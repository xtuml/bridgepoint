package org.xtuml.bp.core;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.xtuml.bp.core.common.ILogger;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.util.OoaofooaUtil;

import lib.BPBoolean;
import lib.BPFloat;
import lib.BPInteger;
import lib.BPLong;
import lib.BPString;
import lib.BPUniqueId;

//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

//NOTE:
// This class only supports a class loader with a path of a single project. This
// could be a problem when simulating multiple projects that have references to
// user classes in different paths. We may need a way to append paths to a 
// BPClassLoader when that support is needed.

// Virtual Machine
public class Vm_c {

	private static class targetInfo {

		public Class<?> target = null;

		public ArrayList<Object> argVals = new ArrayList<Object>();

		public ArrayList<Class<?>> argTypes = new ArrayList<Class<?>>();

	}

	// private static Stack<targetInfo> s = new Stack<targetInfo>();
	private static HashMap<Thread, Stack<targetInfo>> stackMap = new HashMap<Thread, Stack<targetInfo>>();

	private static Map<SystemModel_c, ClassLoader> vmclMap = Collections
			.synchronizedMap(new HashMap<SystemModel_c, ClassLoader>());

	private static Object result = null;

	public static void resetClassLoader(SystemModel_c key) {
		vmclMap.remove(key);
	}

	public static void resetAllClassLoader() {
		vmclMap.clear();
	}

	public static void printWarningMessageForUnloadedClassesIfNeeded(SystemModel_c key) {
		if (vmclMap.containsKey(key)) {
			CorePlugin.out.println("\nWARNING:  The terminated project " + key.Get_name()
					+ "contains a realized class/classes that are not unloaded "
					+ "because there are one or more projects running in verifier\n");
		}
	}

	private static Stack<targetInfo> getStack() {
		synchronized (stackMap) {
			Stack<targetInfo> result = stackMap.get(Thread.currentThread());
			if (result == null) {
				result = new Stack<targetInfo>();
				stackMap.put(Thread.currentThread(), result);
			}
			return result;
		}
	}

	public static boolean Loadclass(final String name, UUID systemID) {
		Ooaofooa.log.println(ILogger.BRIDGE, "loadClass", " Bridge entered: VirtualMachine::Loadclass");
		SystemModel_c system = (SystemModel_c) Ooaofooa.getDefaultInstance().getInstanceList(SystemModel_c.class)
				.getGlobal(systemID);
		return LoadQualifiedclass(system, "lib." + name);
	} // End loadClass

	public static boolean LoadQualifiedclass(SystemModel_c system, final String name) {
		Ooaofooa.log.println(ILogger.BRIDGE, "loadClass", " Bridge entered: VirtualMachine::Loadclass");

		targetInfo tgtInfo = getStack().peek();
		tgtInfo.target = null;

		if (!vmclMap.containsKey(system) || vmclMap.get(system) == null) {
			getVmCl(system.getSys_id());
		}
		try {
			tgtInfo.target = vmclMap.get(system).loadClass(name); // $NON-NLS-1$
		} catch (ClassNotFoundException e) {
			// Do nothing. This is not an error, the class
			// may not exist or may be located elsewhere.
		}
		if (tgtInfo.target != null) {
			return true;
		}
		getStack().pop();
		return false;

	} // End loadClass

	private static ClassLoader getClassLoaderForSystem(SystemModel_c system, ClassLoader parent) {
		final IProject project = system.getFile().getProject();
		try {
			if (project.hasNature(JavaCore.NATURE_ID)) {
				final IJavaProject javaProject = JavaCore.create(project);
				final URL[] urls = Stream.concat(Stream.of(javaProject.getOutputLocation()),
						Stream.of(javaProject.getResolvedClasspath(true)).flatMap(cpe -> {
							switch (cpe.getEntryKind()) {
							case IClasspathEntry.CPE_LIBRARY:
								return Stream.of(cpe.getPath());
							case IClasspathEntry.CPE_PROJECT:
								final Optional<IProject> refProj = Stream
										.of(ResourcesPlugin.getWorkspace().getRoot().getProjects())
										.filter(p2 -> p2.getFullPath().equals(cpe.getPath())).findAny();
								if (refProj.isPresent()) {
									try {
										return Stream.of(JavaCore.create(refProj.orElseThrow()).getOutputLocation());
									} catch (JavaModelException e) {
										CorePlugin.getDefault().getLog()
												.warn("Failed to find referenced project: " + cpe.getPath(), e);
									}
								}
							default:
								return Stream.empty();
							}
						})).flatMap(p -> {
							IPath p2 = ResourcesPlugin.getWorkspace().getRoot().getFile(p).getLocation();
							if (p2 == null) {
								p2 = p; // non-workspace resource
							}
							if (!"jar".equals(p2.getFileExtension())) {
								p2 = p2.addTrailingSeparator(); // add a trailing slash to indicate this is a directory
																// containing .class files
							}
							try {
								return Stream.of(new File(p2.toOSString()).toURI().toURL());
							} catch (MalformedURLException e) {
								CorePlugin.getDefault().getLog().warn("Failed to create classpath URL", e);
								return Stream.empty();
							}
						}).toArray(URL[]::new);
				return new URLClassLoader(urls, parent);
			} else {
				return new URLClassLoader(new URL[0], parent);
			}
		} catch (CoreException e) {
			CorePlugin.logError("Error occurred while creating class loader", e);
			return new URLClassLoader(new URL[0], parent);
		}
	}

	private static String[] coreTypesThatMapToObject = { "inst_ref<Object>", "inst_ref_set<Object>", "inst<Event>",
			"inst<Mapping>", "inst_ref<Mapping>", "date", "inst_ref<Timer>" };

	public static void Addargumentvalue(Object value) {
		targetInfo tgtInfo = getStack().peek();
		Class<?> type = value.getClass();
		if (value instanceof Integer) {
			type = int.class;
		}
		tgtInfo.argTypes.add(type);
		tgtInfo.argVals.add(value);
	}

	public static Object Addargumentvalue(boolean byRef, boolean isArray, String type, Object value) {
		Ooaofooa.log.println(ILogger.BRIDGE, "addArgumentValue", " Bridge entered: VirtualMachine::Addargumentvalue");
		targetInfo tgtInfo = getStack().peek();
		Object result = null;
		if (isArray || value instanceof List<?>) {
			tgtInfo.argTypes.add(List.class);
			tgtInfo.argVals.add(value != null ? value : new ArrayList<>());
			result = value;
		} else if (type.equals("boolean")) { //$NON-NLS-1$
			if (byRef == false) {
				tgtInfo.argTypes.add(boolean.class);
				tgtInfo.argVals.add((Object) Boolean.parseBoolean((String) value));
			} else {
				tgtInfo.argTypes.add(BPBoolean.class);
				if (value instanceof BPBoolean) {
					result = (BPBoolean) value;
				} else {
					result = new BPBoolean(Boolean.parseBoolean((String) value));
				}
				tgtInfo.argVals.add(result);
			}
		} else if (type.equals("integer")) { //$NON-NLS-1$
			if (byRef == false) {
				tgtInfo.argTypes.add(int.class);
				tgtInfo.argVals.add((Object) Integer.parseInt((String) value));
			} else {
				tgtInfo.argTypes.add(BPInteger.class);
				if (value instanceof BPInteger) {
					result = (BPInteger) value;
				} else {
					result = new BPInteger(Integer.parseInt((String) value));
				}
				tgtInfo.argVals.add(result);
			}
		} else if (type.equals("timestamp")) { //$NON-NLS-1$
			if (byRef == false) {
				tgtInfo.argTypes.add(long.class);
				tgtInfo.argVals.add((Object) Long.parseLong((String) value));
			} else {
				tgtInfo.argTypes.add(BPLong.class);
				if (value instanceof BPLong) {
					result = (BPLong) value;
				} else {
					result = new BPLong(Long.parseLong((String) value));
				}
				tgtInfo.argVals.add(result);
			}
		} else if (type.equals("real")) { //$NON-NLS-1$
			if (byRef == false) {
				tgtInfo.argTypes.add(float.class);
				tgtInfo.argVals.add((Object) Float.parseFloat((String) value));
			} else {
				tgtInfo.argTypes.add(BPFloat.class);
				if (value instanceof BPFloat) {
					result = (BPFloat) value;
				} else {
					result = new BPFloat(Float.parseFloat((String) value));
				}
				tgtInfo.argVals.add(result);
			}
		} else if (type.equals("string")) { //$NON-NLS-1$
			if (byRef == false) {
				tgtInfo.argTypes.add(String.class);
				tgtInfo.argVals.add(value);
			} else {
				tgtInfo.argTypes.add(BPString.class);
				result = value;
				tgtInfo.argVals.add(result);
			}
		} else if (type.equals("component_ref")) { //$NON-NLS-1$
			tgtInfo.argTypes.add(ComponentInstance_c.class);
			result = value;
			tgtInfo.argVals.add(value);
		} else if (type.equals("unique_id")) { //$NON-NLS-1$
			if (byRef == false) {
				tgtInfo.argTypes.add(UUID.class);
				// Make sure we have a valid UUID string
				UUID id = IdAssigner.createRuntimeUUIDFromString((String) value, null);
				tgtInfo.argVals.add(id);
			} else {
				tgtInfo.argTypes.add(BPUniqueId.class);
				result = value;
				tgtInfo.argVals.add(result);
			}
		} else {
			boolean mapsToObject = false;
			for (String objectType : coreTypesThatMapToObject) {
				if (objectType.equals(type)) {
					mapsToObject = true;
					break;
				}
			}
			if (mapsToObject) {
				if (byRef == false) {
					tgtInfo.argTypes.add(Object.class);
					tgtInfo.argVals.add(value);
				} else {
					CorePlugin.logError("Unsupported data type, " + type
							+ ", for argument being passed by reference into realized code", null);
				}
			} else {
				tgtInfo.argTypes.add(value.getClass());
				result = value;
				tgtInfo.argVals.add(value);
			}
		}
		return result;
	} // End addArgumentValue

	public static void Resetvalues() {
		Ooaofooa.log.println(ILogger.BRIDGE, "resetValues", " Bridge entered: VirtualMachine::Resetvalues");

		targetInfo tgtInfo = new targetInfo();
		tgtInfo.argVals.clear();
		tgtInfo.argTypes.clear();
		tgtInfo.target = null;
		getStack().push(tgtInfo);

	} // End resetValues

	public static boolean Execute(final String Name, UUID p_stack_frame_id) {
		return Execute(Name, null, p_stack_frame_id);
	}

	public static boolean Execute(final String Name, Object target, UUID p_stack_frame_id) {
		Ooaofooa.log.println(ILogger.BRIDGE, "execute", " Bridge entered: VirtualMachine::Execute");
		targetInfo tgtInfo = getStack().peek();
		if (tgtInfo.target != null) {
			Method met = null;
			Object[] oTypes = tgtInfo.argTypes.toArray();
			Class<?>[] types = new Class<?>[oTypes.length];
			for (int i = 0; i < types.length; i++) {
				types[i] = (Class<?>) oTypes[i];
			}
			boolean passStackFrame = false;
			try {
				try {
					met = tgtInfo.target.getMethod(Name, types);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// Do nothing, can happen if stack frame is accepted.
				}
				if (met == null) {
					// Try to bind to a Java class prepared
					// to accept EE engine execution context
					Class<?>[] typesPlusSF = new Class<?>[oTypes.length + 1];
					typesPlusSF[0] = StackFrame_c.class;
					for (int i = 0; i < types.length; i++) {
						typesPlusSF[i + 1] = types[i];
					}
					try {
						met = tgtInfo.target.getMethod(Name, typesPlusSF);
						passStackFrame = true;
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			} finally {
				getStack().pop();
				if (getStack().isEmpty()) {
					synchronized (stackMap) {
						stackMap.remove(getStack());
					}
				}
			}
			if (met != null) {
				int modifiers = met.getModifiers();
				if (target != null || (modifiers & Modifier.STATIC) == Modifier.STATIC) {
					try {
						int argListSize = tgtInfo.argVals.size();
						int firstArg = 0;
						if (passStackFrame) {
							argListSize++;
							firstArg++;
						}
						Object[] oArgVals = new Object[argListSize];
						if (passStackFrame) {
							StackFrame_c v_stackFrame = null;
							Ooaofooa[] instances = Ooaofooa.getInstances();
							for (int i = 0; i < instances.length; i++) {
								v_stackFrame = (StackFrame_c) instances[i].getInstanceList(StackFrame_c.class)
										.get(p_stack_frame_id.toString());
								if (v_stackFrame != null) {
									break;
								}
							}
							oArgVals[0] = v_stackFrame;
						}
						for (int i = 0; i < tgtInfo.argVals.size(); i++) {
							oArgVals[i + firstArg] = tgtInfo.argVals.get(i);
						}
						result = met.invoke(target, oArgVals);
						return true; // Invoke successful
					} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
						CorePlugin.logError("Failed to execute realized code", e);
						final Stack_c stack = Stack_c.getOneI_STACKOnR2929((StackFrame_c) Ooaofooa.getDefaultInstance()
								.getInstanceList(StackFrame_c.class).getGlobal(p_stack_frame_id));
						stack.setRunstate(Runstatetype_c.Terminated);
					}
				} else {
					InvocationTargetException e = new InvocationTargetException(new Throwable(),
							met.getName() + " must be static");
					CorePlugin.logError("Verifier EE external method " + met.getName() + " must be static", e);
				}
			} else {
				NoSuchMethodException e = new NoSuchMethodException(Name);
				String args = "";
				for (int i = 0; i < types.length; i++) {
					if (args.length() > 0) {
						args = args + ", ";
					}
					args = args + types[i].getName();
				}
				String funcNameWithArgs = Name + "( " + args + " )";
				CorePlugin.logError("Verifier EE external method " + funcNameWithArgs + " not found", e);
			}
			return false; // could not Invoke successfully
		} else {
			return false;
		}
	} // End execute

	public static Object Getresult() {
		Ooaofooa.log.println(ILogger.BRIDGE, "getResult", " Bridge entered: VirtualMachine::Getresult");
		if (result instanceof Boolean) {
			return (Object) ((Boolean) result).toString();
		} else if (result instanceof Integer) {
			return (Object) ((Integer) result).toString();
		} else if (result instanceof Float) {
			return (Object) ((Float) result).toString();
		} else if (result instanceof String) {
			return (Object) ((String) result).toString();
		} else if (result instanceof Long) {
			return (Object) ((Long) result).toString();
		} else if (result instanceof UUID) {
			return (Object) ((UUID) result).toString();
		} else {
			return result;
		}
	} // end Getresult

	public static Object getRawResult() {
		return result;
	}

	public static ClassLoader getVmCl(UUID systemID) {
		SystemModel_c system = (SystemModel_c) Ooaofooa.getDefaultInstance().getInstanceList(SystemModel_c.class)
				.getGlobal(systemID);
		if (!vmclMap.containsKey(system)) {
			final ClassLoader cl = getClassLoaderForSystem(system, Vm_c.class.getClassLoader());
			vmclMap.put(system, cl);
		}
		return vmclMap.get(system);
	}

	public static ClassLoader getVmCl(Component_c component) {
		PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(component);
		return getVmCl(pe);
	}

	public static ClassLoader getVmCl(Interface_c iface) {
		PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(iface);
		return getVmCl(pe);
	}

	public static ClassLoader getVmCl(DataType_c dt) {
		PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(dt);
		return getVmCl(pe);
	}

	public static ClassLoader getVmCl(PackageableElement_c pe) {
		ClassLoader cl;

		if (pe != null) {
			SystemModel_c sys = OoaofooaUtil.getSystemForElement(pe);
			if (sys != null) {
				cl = Vm_c.getVmCl(sys.Get_ooa_id());
				if (cl != null) {
					return cl;
				} else {
					throw new IllegalStateException("Unable to load Verifier Classloader.");
				}
			} else {
				throw new IllegalStateException("Unable to locate the system for the audited element.");
			}
		} else {
			throw new IllegalStateException("Realized components are supported under generic packages only.");
		}
	}

	public static void removeStack(Thread forThread) {
		synchronized (stackMap) {
			Stack<targetInfo> result = stackMap.get(forThread);
			if (result != null) {
				result.clear();
				stackMap.remove(result);
				// Request garbage collection. This is merely a hint to the JVM
				// that we need unwanted instances flushed. It can take several
				// seconds for it to respond.
				System.runFinalization();
				System.gc();
			}
		}
	}
	
	public static Object Createlistfromarrayvalue(ArrayValue_c arrayValue) {
		return Stream.of(ValueInArray_c.getManyRV_VIAsOnR3302(arrayValue))
				.sorted((a, b) -> Integer.compare(a.getIndex(), b.getIndex())).map(RuntimeValue_c::getOneRV_RVLOnR3302)
				.map(RuntimeValue_c::Getvalue).collect(Collectors.toList());
	}

	public static void Setarrayvaluefromlist(ArrayValue_c arrayValue, Object value) {
		if (value instanceof List<?>) {
			final List<?> listValue = (List<?>) value;
			for (int i = 0; i < listValue.size(); i++) {
				final Object element = listValue.get(i);
				// create a value in the array
				final ValueInArray_c via = new ValueInArray_c(arrayValue.getModelRoot());
				via.setIndex(i);
				// create a runtime value
				final RuntimeValue_c rtv = new RuntimeValue_c(arrayValue.getModelRoot());
				via.relateAcrossR3302To(arrayValue);
				via.relateAcrossR3302To(rtv);
				// set value
				if (element instanceof List<?>) { // nested list
					final ArrayValue_c elementArrayValue = new ArrayValue_c(arrayValue.getModelRoot());
					rtv.relateAcrossR3300To(elementArrayValue);
					Setarrayvaluefromlist(elementArrayValue, element);
				} else {
					rtv.Createsimplevalue();
					rtv.Setvalue(element);
				}
			}
		}
	}

} // End Vm_c
