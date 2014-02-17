package com.mentor.nucleus.bp.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

import lib.BPBoolean;
import lib.BPFloat;
import lib.BPInteger;
import lib.BPString;
import lib.BPUniqueId;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.osgi.internal.baseadaptor.DefaultClassLoader;

import com.mentor.nucleus.bp.core.common.ILogger;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.util.BPClassLoader;
import com.mentor.nucleus.bp.core.util.OoaofooaUtil;

//========================================================================
//
//File:      $RCSfile: Vm_c.java,v $
//Version:   $Revision: 1.20 $
//Modified:  $Date: 2013/01/10 22:54:14 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
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

    private static Map<SystemModel_c, BPClassLoader> vmclMap = Collections
            .synchronizedMap(new HashMap<SystemModel_c, BPClassLoader>());

    private static Object result = null;

    public static void resetClassLoader(SystemModel_c key) {
        vmclMap.remove(key);
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
        Ooaofooa.log.println(ILogger.BRIDGE, "loadClass",
                " Bridge entered: VirtualMachine::Loadclass");

        targetInfo tgtInfo = getStack().peek();
        tgtInfo.target = null;
        SystemModel_c system = (SystemModel_c) Ooaofooa.getDefaultInstance()
                .getInstanceList(SystemModel_c.class).getGlobal(systemID);
        if (vmclMap.containsKey(system) && vmclMap.get(system) != null) {
            try {
                tgtInfo.target = vmclMap.get(system).loadClass("lib." + name); //$NON-NLS-1$
            } catch (ClassNotFoundException e) {
                // Do nothing. This is not an error, the class
                // may not exist or may be located elsewhere.
            }
            if (tgtInfo.target != null) {
                return true;
            }
        }
        getStack().pop();
        return false;

    } // End loadClass

    public static boolean LoadQualifiedclass(SystemModel_c system,
            final String name) {
        Ooaofooa.log.println(ILogger.BRIDGE, "loadClass",
                " Bridge entered: VirtualMachine::Loadclass");

        targetInfo tgtInfo = getStack().peek();
        tgtInfo.target = null;

        if (vmclMap.containsKey(system) && vmclMap.get(system) != null) {
            try {
                tgtInfo.target = vmclMap.get(system).loadClass(name); //$NON-NLS-1$
            } catch (ClassNotFoundException e) {
                // Do nothing. This is not an error, the class
                // may not exist or may be located elsewhere.
            }
            if (tgtInfo.target != null) {
                return true;
            }
        }
        getStack().pop();
        return false;

    } // End loadClass

    private static BPClassLoader createClassLoader(IPath path) {
        BPClassLoader result = null;
        ClassLoader cll = Vm_c.class.getClassLoader();
        if (cll instanceof DefaultClassLoader) {
            DefaultClassLoader dcl = (DefaultClassLoader) cll;
            String[] appendedClasspath = new String[1];
            appendedClasspath[0] = "external:" + path.toString(); //$NON-NLS-1$
            result = new BPClassLoader(appendedClasspath, dcl);
            result.initialize();
        }
        return result;

    } // End createClassLoader

    /**
     * Get the absolute path location to the user's project. Set the private
     * member, path above to the lib folder below it.
     */
    public static void Setuserclasspath(UUID System_ID) {
        Ooaofooa.log.println(ILogger.BRIDGE, "setUserClassPath",
                " Bridge entered: VirtualMachine::Setuserclasspath");

        // Get all System Model instances in the workspace
        SystemModel_c[] sys = SystemModel_c.SystemModelInstances(Ooaofooa
                .getDefaultInstance());
        // for each system model . . . .
        for (int i = 0; i < sys.length; i++) {
            if (sys[i].getSys_id().equals(System_ID)) {
                // if the system model's id matches the one passed in . . .
                // . . . adapt the System Model instance to an Eclipse
                // resource
                IResource pr = (IResource) sys[i].getAdapter(IResource.class);
                // set the private path member from the resource, append the
                // lib folder
                IPath path = pr.getLocation();
                if (path != null) {
                    path = path.append("bin"); //$NON-NLS-1$    
                    Adduserclasspath(sys[i], path);
                    break;
                } else {
                    CorePlugin.logError("Unable to find path", null);
                }
            }
        }
    } // End setUserClassPath

    public static void Adduserclasspath(UUID systemId, String path) {
		SystemModel_c system = (SystemModel_c) Ooaofooa.getDefaultInstance()
				.getInstanceList(SystemModel_c.class).get(systemId);
    	Adduserclasspath(system, path);
    }
    
    public static void Adduserclasspath(SystemModel_c system, String path) {
        path = expandEclipseVariables(path);
        if (!path.equals("")) {
            IPath newPath = Path.fromOSString(path);
            if (newPath.toFile().exists()) {
                Adduserclasspath(system, newPath);
            } else {
                CorePlugin.err
                        .println("Error: Specified path to realized code is incorrect: "
                                + path);
            }
        }
    }

    private static String expandEclipseVariables(String path) {
        while (path.contains("${")) {
            int start = path.indexOf("${");
            int end = path.indexOf("}", start);
            if (end == -1) {
                CorePlugin.err
                        .println("Error: Substitution variable not terminated in "
                                + path + ". Need closing brace (}).");
                path = "";
            } else {
                String variable = path.substring(start, end + 1);
                String result = "";
                try {
                    result = VariablesPlugin.getDefault()
                            .getStringVariableManager()
                            .performStringSubstitution(variable);
                } catch (CoreException ce) {
                    CorePlugin.err
                            .println("Error obtaining the value of eclipse variable, '"
                                    + variable + "'. " + ce.toString());
                }
                path = path.subSequence(0, start) + result
                        + path.substring(end + 1);
            }
        }
        return path;
    }

    public static void Adduserclasspath(SystemModel_c system, IPath path) {
        if (vmclMap.containsKey(system) && vmclMap.get(system) != null) {
            vmclMap.get(system).addClassPathEntry(path.toOSString());
        } else {
            vmclMap.put(system, createClassLoader(path));
        }
    }

    private static String[] coreTypesThatMapToObject = { "inst_ref<Object>",
            "inst_ref_set<Object>", "inst<Event>", "inst<Mapping>",
            "inst_ref<Mapping>", "date", "inst_ref<Timer>", "timestamp" };

    public static void Addargumentvalue(Object value) {
        targetInfo tgtInfo = getStack().peek();
        Class<?> type = value.getClass();
        if (value instanceof Integer) {
            type = int.class;
        }
        tgtInfo.argTypes.add(type);
        tgtInfo.argVals.add(value);
    }

    public static Object Addargumentvalue(boolean byRef, String type,
            Object value) {
        Ooaofooa.log.println(ILogger.BRIDGE, "addArgumentValue",
                " Bridge entered: VirtualMachine::Addargumentvalue");
        targetInfo tgtInfo = getStack().peek();
        Object result = null;
        if (value instanceof ArrayList<?>) {
            tgtInfo.argTypes.add(ArrayList.class);
            tgtInfo.argVals.add(value);
            result = value;
        } else if (type.equals("boolean")) { //$NON-NLS-1$
            if (byRef == false) {
                tgtInfo.argTypes.add(boolean.class);
                tgtInfo.argVals.add((Object) new Boolean((String) value));
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
                tgtInfo.argVals.add((Object) new Integer(Gd_c
                        .String_to_int((String) value)));
            } else {
                tgtInfo.argTypes.add(BPInteger.class);
                if (value instanceof BPInteger) {
                    result = (BPInteger) value;
                } else {
                    result = new BPInteger(Integer.parseInt((String) value));
                }
                tgtInfo.argVals.add(result);
            }
        } else if (type.equals("real")) { //$NON-NLS-1$
            if (byRef == false) {
                tgtInfo.argTypes.add(float.class);
                tgtInfo.argVals.add((Object) new Float((String) value));
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
                    CorePlugin
                            .logError(
                                    "Unsupported data type, "
                                            + type
                                            + ", for argument being passed by reference into realized code",
                                    null);
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
        Ooaofooa.log.println(ILogger.BRIDGE, "resetValues",
                " Bridge entered: VirtualMachine::Resetvalues");

        targetInfo tgtInfo = new targetInfo();
        tgtInfo.argVals.clear();
        tgtInfo.argTypes.clear();
        tgtInfo.target = null;
        getStack().push(tgtInfo);

    } // End resetValues

    public static boolean Execute(final String Name, UUID p_stack_frame_id) {
        return Execute(Name, null, p_stack_frame_id);
    }

    public static boolean Execute(final String Name, Object target,
            UUID p_stack_frame_id) {
        Ooaofooa.log.println(ILogger.BRIDGE, "execute",
                " Bridge entered: VirtualMachine::Execute");
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
                        // TODo Make this more graceful by logging in the console
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
                if (target != null
                        || (modifiers & Modifier.STATIC) == Modifier.STATIC) {
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
                                v_stackFrame = (StackFrame_c) instances[i]
                                        .getInstanceList(StackFrame_c.class)
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
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    InvocationTargetException e = new InvocationTargetException(
                            new Throwable(), met.getName() + " must be static");
                    CorePlugin.logError("Verifier EE external method "
                            + met.getName() + " must be static", e);
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
                CorePlugin.logError("Verifier EE external method "
                        + funcNameWithArgs + " not found", e);
            }
            return false; // could not Invoke successfully
        } else {
            return false;
        }
    } // End execute

    public static Object Getresult() {
        Ooaofooa.log.println(ILogger.BRIDGE, "getResult",
                " Bridge entered: VirtualMachine::Getresult");
        if (result instanceof Boolean) {
            return (Object) ((Boolean) result).toString();
        }
        else if (result instanceof Integer) {
            return (Object) ((Integer) result).toString();
        }
        else if (result instanceof Float) {
            return (Object) ((Float) result).toString();
        }
        else if (result instanceof String) {
            return (Object) ((String) result).toString();
        }
        else if (result instanceof Long) {
            return (Object) ((Long) result).toString();
        }
        else if (result instanceof UUID) {
        	return (Object) ((UUID) result).toString();
        }
        else {
            return result;
        }
    } // end Getresult

    public static Object getRawResult() {
        return result;
    }

    public static BPClassLoader getVmCl(UUID systemID) {
        SystemModel_c system = (SystemModel_c) Ooaofooa.getDefaultInstance()
                .getInstanceList(SystemModel_c.class).getGlobal(systemID);
        if (vmclMap.containsKey(system)) {
            return vmclMap.get(system);
        } else {
            Setuserclasspath(systemID);
            return vmclMap.get(system);
        }
    }

    public static BPClassLoader getVmCl(Component_c component) {
        PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(component);
        return getVmCl(pe);
    }

    public static BPClassLoader getVmCl(Interface_c iface) {
        PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(iface);
        return getVmCl(pe);
    }

    public static BPClassLoader getVmCl(DataType_c dt) {
        PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(dt);
        return getVmCl(pe);
    }

    public static BPClassLoader getVmCl(PackageableElement_c pe) {
        BPClassLoader cl;
        
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

} // End Vm_c

