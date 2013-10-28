package com.mentor.nucleus.bp.debug.java.access;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lib.BPBoolean;
import lib.BPFloat;
import lib.BPInteger;
import lib.BPString;
import lib.BPUniqueId;
import lib.LOG;

import com.mentor.nucleus.bp.core.ActualParameter_c;
import com.mentor.nucleus.bp.core.ArrayValue_c;
import com.mentor.nucleus.bp.core.BlockInStackFrame_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.Dimensions_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.LocalValue_c;
import com.mentor.nucleus.bp.core.Local_c;
import com.mentor.nucleus.bp.core.MessageValue_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.PropertyParameter_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperationBody_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignalBody_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperationBody_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignalBody_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.Runstatetype_c;
import com.mentor.nucleus.bp.core.RuntimeChannel_c;
import com.mentor.nucleus.bp.core.RuntimeValue_c;
import com.mentor.nucleus.bp.core.StackFrame_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.StructureMember_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.StructuredValue_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.TransientValueReference_c;
import com.mentor.nucleus.bp.core.TransientVar_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.User_c;
import com.mentor.nucleus.bp.core.ValueInArray_c;
import com.mentor.nucleus.bp.core.ValueInStackFrame_c;
import com.mentor.nucleus.bp.core.ValueInStructure_c;
import com.mentor.nucleus.bp.core.Value_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.Vm_c;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.ui.cells.providers.ComponentInstanceCellProvider;
import com.mentor.nucleus.bp.core.util.BPClassLoader;
import com.mentor.nucleus.bp.debug.ui.model.BPThread;

public class VerifierInvocationHandler implements InvocationHandler {

	private ComponentInstance_c component = null;
	private Port_c port = null;

	public VerifierInvocationHandler(ComponentInstance_c comp, Port_c port) {
		component = comp;
		this.port = port;
	}

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		Object javaResult = null;
		String methName = arg1.getName();
		String className = arg1.getDeclaringClass().getName();
		String[] segments = className.split("\\.");
		className = segments[segments.length - 1];
		int lPortTypePosn = className.lastIndexOf("ToProvider");
		boolean targetInterfaceIsRequired = true;
		if (lPortTypePosn == -1) {
			lPortTypePosn = className.lastIndexOf("FromProvider");
			targetInterfaceIsRequired = false;
		}
		String ifaceName = className.substring(0, lPortTypePosn).replaceFirst(
				"I", "");
		Component_c comp = Component_c.getOneC_COnR2955(component);
		if (comp == null) {
			comp = Component_c.getOneC_COnR4201(ComponentReference_c
					.getOneCL_ICOnR2963(component));
		}
		InterfaceReference_c[] ifRefs = InterfaceReference_c
				.getManyC_IRsOnR4016(port);
		InterfaceReference_c ifRef = null;
		for (int i = 0; i < ifRefs.length; i++) {
			Requirement_c req = Requirement_c.getOneC_ROnR4009(ifRefs[i]);
			if (req != null) {
				if (targetInterfaceIsRequired) {
					Interface_c iface = Interface_c.getOneC_IOnR4012(ifRefs[i]);
					if (iface.getName().replace(" ", "").equals(ifaceName)) {
						ifRef = ifRefs[i];
						break;
					}
				}
			} else {
				if (!targetInterfaceIsRequired) {
					Interface_c iface = Interface_c.getOneC_IOnR4012(ifRefs[i]);
					if (iface.getName().replace(" ", "").equals(ifaceName)) {
						ifRef = ifRefs[i];
						break;
					}
				}
			}
		}
		Interface_c iface = Interface_c.getOneC_IOnR4012(ifRef);
		// Interface and reference identified
		ExecutableProperty_c[] exProps = ExecutableProperty_c
				.getManyC_EPsOnR4003(iface);
		ExecutableProperty_c exProp = null;
		for (int i = 0; i < exProps.length; i++) {
			if (exProps[i].getName().replace(" ", "").equals(methName)) {
				exProp = exProps[i];
				break;
			}
		}
		boolean shouldBlock = InterfaceOperation_c.getOneC_IOOnR4004(exProp) != null;
		java.util.UUID specifiedComponentInstance = Gd_c.Null_unique_id();
		if (arg2[0] instanceof ComponentInstance_c) {
			specifiedComponentInstance = ((ComponentInstance_c) arg2[0])
					.getExecution_engine_id();
		}
		if (comp != null) {
			java.util.UUID channelID = ComponentInstance_c
					.Resolvetargetchannel(comp.getModelRoot(), ifRef.getId(),
							targetInterfaceIsRequired, component
									.getExecution_engine_id(),
							specifiedComponentInstance);
			if (channelID != Gd_c.Null_unique_id()) {
				RuntimeChannel_c channel = (RuntimeChannel_c) component
						.getModelRoot().getInstanceList(RuntimeChannel_c.class)
						.getGlobal(channelID);
				ComponentInstance_c targetEngine = ComponentInstance_c
						.getOneI_EXEOnR2968IsInterfaceProviderTo(channel);
				if (targetInterfaceIsRequired) {
					targetEngine = ComponentInstance_c
							.getOneI_EXEOnR2968IsInterfaceRequirerOf(channel);
				}
				Stack_c targetStack = Stack_c
						.getOneI_STACKOnR2930(targetEngine);
				java.util.UUID bodyID = ComponentInstance_c.Resolvetargetbody(
						comp.getModelRoot(), channelID,
						"User realized call to " + methName,
						targetInterfaceIsRequired, methName);
				if (bodyID != Gd_c.Null_unique_id()) {
					Body_c body = (Body_c) component.getModelRoot()
							.getInstanceList(Body_c.class).getGlobal(bodyID);
					if (body != null) {
						// Now marshal the argument values
						PropertyParameter_c param = PropertyParameter_c
								.getOneC_PPOnR4006(exProp);
						PropertyParameter_c tempParam = null;
						while (param != null){
							tempParam = param;
							param = PropertyParameter_c.getOneC_PPOnR4021Succeeds(param);
						}
						param  = tempParam;
						StackFrame_c localStackFrame = StackFrame_c
								.getOneI_STFOnR2929(Stack_c
										.getOneI_STACKOnR2930(component));
						boolean stackFrameCreated = false;
						if (localStackFrame == null) {
							localStackFrame = new StackFrame_c(component.getModelRoot());
							Stack_c.getOneI_STACKOnR2930(component).Push(localStackFrame.getStack_frame_id());
							stackFrameCreated = true;
						}
							java.util.UUID sf = body.Createstackframe(true,
									localStackFrame.getStack_frame_id(),
									targetStack.getStack_id());
							StackFrame_c newStackFrame = (StackFrame_c) component
									.getModelRoot().getInstanceList(
											StackFrame_c.class).getGlobal(sf);
							Map<RuntimeValue_c, ParameterValue> argMap = new HashMap<RuntimeValue_c, ParameterValue>();
							for (int i = 0; param != null ; i++) {
								RuntimeValue_c rtVal = marshallValueIn(param,
										arg2[i + 1], exProp.getId(), bodyID,
										sf, localStackFrame.getStack_frame_id(),
										ComponentInstance_c.getOneI_EXEOnR2930(targetStack));
								if (param.getBy_ref() == 1) {
									Dimensions_c [] dims = Dimensions_c.getManyS_DIMsOnR4017(param);
									argMap.put(rtVal, new ParameterValue(arg2[i + 1], dims.length));
								}
								param = PropertyParameter_c.getOneC_PPOnR4021Precedes(param);
							}
							if (targetEngine != null) {
								if (newStackFrame != null) {
									if (shouldBlock) {
										localStackFrame
												.relateAcrossR2965ToBlockedBy(newStackFrame);
										body.Startstackframeformessage(sf);
										Stack_c localStack = Stack_c
												.getOneI_STACKOnR2943(localStackFrame);
										while (StackFrame_c
												.getOneI_STFOnR2965BlockedBy(localStackFrame) != null
												&& localStack != null
												&& localStack.getRunstate() != Runstatetype_c.Terminated) {
											synchronized (component) {
												if (!BPThread
														.qdFramesReady(localStack)
														&& !BPThread
																.qdResultsReady(localStack)) {
													component.wait();
												}
												if (BPThread
														.qdResultsReady(localStack)) {
													StackFrame_c[] resultSFs = StackFrame_c
															.getManyI_STFsOnR2967(localStack);
													for (StackFrame_c resultSF : resultSFs) {
														if (resultSF == newStackFrame
																&& resultSF
																		.getReadyforinterrupt() == true) {
															// Transfer returned
															// data
															// back
															// into java
															// Handle by
															// Reference
															// values
															Set<RuntimeValue_c> rtVals = argMap
																	.keySet();
															for (RuntimeValue_c rtVal : rtVals) {
																DataType_c dt = DataType_c
																		.getOneS_DTOnR3307(rtVal);
																marshallContentOut(
																		rtVal,
																		dt,
																		argMap
																				.get(rtVal).getValue(), argMap.get(rtVal).getArrayDepth(), true, targetEngine);
																disposeTransientValueVariables(rtVal);
															}
															// Now need to
															// handle
															// return
															// value . . .
															ValueInStackFrame_c returnedValue = ValueInStackFrame_c
																	.getOneI_VSFOnR2951(newStackFrame);
															ValueInStackFrame_c localVsf = null;
															if (returnedValue != null) {
																localVsf = new ValueInStackFrame_c(
																		newStackFrame
																				.getModelRoot());
																localVsf
																		.relateAcrossR2951To(localStackFrame);
															}
															localStack
																	.Unblock();
															if (localVsf != null) {
																RuntimeValue_c resultRtv = RuntimeValue_c
																		.getOneRV_RVLOnR3305(localVsf);
																DataType_c dt = DataType_c
																		.getOneS_DTOnR3307(resultRtv);
																InterfaceOperation_c ifOp = InterfaceOperation_c.getOneC_IOOnR4004(exProp);
																if (ifOp != null) {
																	dt = DataType_c.getOneS_DTOnR4008(ifOp);
																}
																Dimensions_c [] dims = Dimensions_c.getManyS_DIMsOnR4018(InterfaceOperation_c.getOneC_IOOnR4004(exProp));
																javaResult = marshallContentOut(
																		resultRtv,
																		dt,
																		null, dims.length, false, targetEngine);
															}
														}
													}
												} else {
													// Handle reentrant calls
													// from
													// Verifier
													executeRealizedCode(localStack);
												}
											}
										}
									} else {
										body.Startstackframeformessage(sf);
									}
								}
							}
					//	}
							if (stackFrameCreated) {
								Stack_c.getOneI_STACKOnR2930(component).Pop(true);
							}
					}
				}
				else {
					User_c.Logerror("Body not found for message, ->" + methName + "<-. Synchronization possibly needed", "");
				}
			}
		}
		return javaResult;
	}

	private void disposeTransientValueVariables(RuntimeValue_c rtVal) {
		// These variables were created as temporary holders so that a pass
		// by reference value could be obtained. They are no longer needed
		// at this point and can be disposed.
		Local_c lcl = Local_c.getOneL_LCLOnR3306(rtVal);
		LocalValue_c lvl = LocalValue_c.getOneL_LVLOnR3001(lcl);
		TransientVar_c tvar = TransientVar_c.getOneV_TRNOnR3005(lvl);
		if (lvl != null) {
			lvl.unrelateAcrossR3005From(tvar);
		}
		Variable_c var = Variable_c.getOneV_VAROnR814(tvar);
		Value_c val = Value_c.getOneV_VALOnR801(TransientValueReference_c
				.getOneV_TVLOnR805(var));
		ActualParameter_c arg = ActualParameter_c.getOneV_PAROnR800(val);
		if (arg != null) {
			arg.Dispose();
		}
		if (var != null) {
			var.Dispose();
		}
		if (val != null) {
			val.Dispose();
		}
		if (lcl != null) {
			lcl.Dispose();
		}
	}

	private static Object convertToRawJavaType(Object toConvert, DataType_c dt, boolean isByRef, ComponentInstance_c ci) {
		String name = dt.getName();
		if (name.equals("integer")) {
			try {
				Integer intVal = Integer.parseInt((String) toConvert);
				if (isByRef) {
				  toConvert = new BPInteger(intVal);	
				}
				else {
				  toConvert = intVal;
				}
			} catch (NumberFormatException nfe) {
				LOG
						.LogInfo("Unexpected integer format found converting Verifier data for realized invocation. "
								+ nfe.getLocalizedMessage());
			}
		} else if (name.equals("string")) {
			if (isByRef) {
				toConvert = new BPString((String)toConvert);
			}
			else {
				// do nothing already in correct form
			}
		} else if (name.equals("real")) {
			Float floatVal = Float.parseFloat((String) toConvert); 
			if (isByRef) {
				toConvert = new BPFloat(floatVal);
			}
			else {
			  toConvert = floatVal;
			}
		} else if (name.equals("unique_id")) {
			UUID uuidVal = null;
			if (IdAssigner.isUUID((String) toConvert)) {
			  uuidVal = UUID.fromString((String) toConvert);
			}
			else {
			  uuidVal = IdAssigner.createRuntimeUUIDFromString(toConvert, ci);
			}
			if (isByRef) {
				toConvert = new BPUniqueId(uuidVal);
			}
			else {
			  toConvert = uuidVal;	
			}
		} else if (name.equals("boolean")) {
			Boolean boolVal = Boolean.parseBoolean((String) toConvert);
			if (isByRef) {
				toConvert = new BPBoolean(boolVal);
			}
			else {
				toConvert = boolVal;
			}
		} else if (name.equals("component_ref")) {
			// do nothing
		}
		return toConvert;
	}

	private static RuntimeValue_c marshallValueIn(
			PropertyParameter_c propertyParameterC, Object value, UUID msgid,
			UUID bodyID, UUID sf, UUID stackFrameId, ComponentInstance_c ci) {
		ModelRoot root = propertyParameterC.getModelRoot();
		DataType_c dt = DataType_c.getOneS_DTOnR4007(propertyParameterC);
		Dimensions_c[] dims = Dimensions_c
				.getManyS_DIMsOnR4017(propertyParameterC);
		RuntimeValue_c rootValue = marshallContentIn(root, null, value, dt,
				dims.length, ci);
		StackFrame_c lclStackFrame = (StackFrame_c) root.getInstanceList(
				StackFrame_c.class).getGlobal(stackFrameId);
		BlockInStackFrame_c bsf = BlockInStackFrame_c
				.getOneI_BSFOnR2923(lclStackFrame);
		if (rootValue != null) {
			Value_c val = new Value_c(root);
			TransientValueReference_c tvr = new TransientValueReference_c(root);
			tvr.relateAcrossR801To(val);
			Local_c lcl = new Local_c(root);
			LocalValue_c lvl = new LocalValue_c(root);
			lcl.relateAcrossR3001To(lvl);
			lcl.relateAcrossR3000To(bsf);
			lcl.relateAcrossR3306To(rootValue);
			Variable_c var = new Variable_c(root);
			TransientVar_c tvar = new TransientVar_c(root);
			tvar.relateAcrossR814To(var);
			tvr.relateAcrossR805To(var);
			tvar.relateAcrossR3005To(lvl);
			tvar.relateAcrossR821To(dt);
			var.setName("User realized value for "
					+ propertyParameterC.getName());
			ActualParameter_c arg = new ActualParameter_c(root);
			arg.setName(propertyParameterC.getName());
			val.relateAcrossR800To(arg);
			ValueInStackFrame_c visf = new ValueInStackFrame_c(root);
			if (lclStackFrame != null) {
				lclStackFrame.relateAcrossR2951To(visf);
				visf.relateAcrossR2978To(val);
				visf.relateAcrossR3305To(rootValue);
			}
			ComponentInstance_c.Transfervaluetotarget(root, arg.getValue_id(),
					bodyID, stackFrameId, msgid, sf);
			if (propertyParameterC.getBy_ref() == 0) {
				// Being passed by value
				lcl.Dispose();
				val.Dispose();
				var.Dispose();
				arg.Dispose();
			}
		}
		return rootValue;
	}

	private static RuntimeValue_c marshallContentIn(ModelRoot root,
			RuntimeValue_c rootValue, Object value, DataType_c dt,
			int arrayDepth, ComponentInstance_c ci) {
		RuntimeValue_c result = null;
		if (rootValue != null) {
			result = rootValue;
		}
		if (arrayDepth > 0) {
			result = marshallArrayValueIn(root, rootValue, value, dt,
					arrayDepth, ci);
		} else {
			DataType_c rootType = getCoreTypeForDt(dt);
			CoreDataType_c cdt = CoreDataType_c.getOneS_CDTOnR17(rootType);
			StructuredDataType_c sdt = StructuredDataType_c
					.getOneS_SDTOnR17(rootType);
			EnumerationDataType_c edt = EnumerationDataType_c
					.getOneS_EDTOnR17(rootType);
			if (cdt != null) {
				result = marshallCoreValueIn(root, rootValue, value, dt, ci);
			} else if (sdt != null) {
				result = marshallStructuredValueIn(root, rootValue, value, dt,
						result, ci);
			} else if (edt != null) {
				result = marshallEnumeratedValueIn(value, dt, result, rootType);
			}
		}
		return result;
	}

	private static RuntimeValue_c marshallEnumeratedValueIn(Object value, DataType_c dt,
			RuntimeValue_c result, DataType_c rootType) {
		Package_c pkg = Package_c
				.getOneEP_PKGOnR8000(PackageableElement_c
						.getOnePE_PEOnR8001(dt));
		if (pkg != null) {
			String className = pkg.Getpath("");
			String enumName = rootType.getName();
			className = pathToClassName(className + "::" + enumName);
			Class<?> enumClass = value.getClass();
			if (enumClass.getName().equals(className)
					&& enumClass.isEnum()) {
				Enum<?> enumType = (Enum<?>) value;
				String enumVal = enumName + "::" + enumType.toString();
				if (result == null) {
					result = new RuntimeValue_c(pkg.getModelRoot());
					result.relateAcrossR3307To(dt);
				}
				result.Setvalue(enumVal);
			} else {
				result.Setvalue(value);
			}
		} else {
			// TODO When dts0100910433 is promoted convert these
			// to Runtime exceptions
			LOG.LogInfo("Non generic package realized elements not supported.");
		}
		return result;
	}

	private static RuntimeValue_c marshallStructuredValueIn(ModelRoot root,
			RuntimeValue_c rootValue, Object value, DataType_c adt,
			RuntimeValue_c result, ComponentInstance_c ci) {
		DataType_c rootDt = getCoreTypeForDt(adt);
		StructuredDataType_c sdt = StructuredDataType_c.getOneS_SDTOnR17(rootDt);
		StructuredValue_c strVal = null;
		if (rootValue == null) {
			result = new RuntimeValue_c(root);
			result.relateAcrossR3307To(rootDt);
			strVal = new StructuredValue_c(root);
			result.relateAcrossR3300To(strVal);
		} else {
			strVal = StructuredValue_c.getOneRV_SVLOnR3300(rootValue);
		}
		StructureMember_c[] members = StructureMember_c.getManyS_MBRsOnR44(sdt);
		for (StructureMember_c strMember : members) {
			Object javaMember = null;
			String memberName = strMember.getName();
			DataType_c type = DataType_c.getOneS_DTOnR45(strMember);
			Method meth = null;
			try {
				meth = value.getClass().getDeclaredMethod("get" + memberName,
						new Class<?>[0]);
			} catch (SecurityException e) {
				String ex = e.getLocalizedMessage();
				LOG.LogInfo("Security exception while accessing realized structured data type: " + ex);
			} catch (NoSuchMethodException e) {
				// Normal operation, do nothing ...
			}
			if (meth != null) {
				Class<?> memberClass = getClassForType(type);
				if (memberClass != null && 
						   memberClass.isAssignableFrom(meth.getReturnType())) {
					try {
						javaMember = meth.invoke(value, new Object[0]);
					} catch (IllegalArgumentException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Illegal argument exception while accessing realized structured data type: " + ex);
					} catch (IllegalAccessException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Illegal access exception while accessing realized structured data type: " + ex);
					} catch (InvocationTargetException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Invocation target exception while accessing realized structured data type: " + ex);
					}
				} else {
					LOG.LogInfo("Incompatible return value.");
				}
			} else {
				// Fall back to direct field access
				try {
					Field field = value.getClass().getDeclaredField(memberName);
					try {
						javaMember = field.get(value);
					} catch (IllegalArgumentException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Illegal argument exception while accessing field of realized structured data type: " + ex);
					} catch (IllegalAccessException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Illegal access exception while accessing field of realized structured data type: " + ex);
					}
				} catch (SecurityException e) {
					String ex = e.getLocalizedMessage();
					LOG.LogInfo("Security exception while accessing field of realized structured data type: " + ex);
				} catch (NoSuchFieldException e) {
					String ex = e.getLocalizedMessage();
					LOG.LogInfo("No field found while accessing realized structured data type: " + ex);
				}
			}
			if (javaMember != null) {
				ValueInStructure_c[] viss = ValueInStructure_c
						.getManyRV_VISsOnR3301(strVal);
				ValueInStructure_c vis = null;
				for (ValueInStructure_c visCandidate : viss) {
					if (visCandidate.getName().equals(strMember.getName())) {
						vis = visCandidate;
						break;
					}
				}
				RuntimeValue_c member = null;
				if (vis == null) {
					vis = new ValueInStructure_c(root);
					vis.setName(strMember.getName());
					vis.relateAcrossR3301To(strVal);
				} else {
					member = RuntimeValue_c.getOneRV_RVLOnR3301(vis);
				}
				Dimensions_c[] dims = Dimensions_c
						.getManyS_DIMsOnR53(strMember);
				member = marshallContentIn(root, member, javaMember, type,
						dims.length, ci);
				vis.relateAcrossR3301To(member);
			}
		}
		return result;
	}

	private static RuntimeValue_c marshallCoreValueIn(ModelRoot root,
			RuntimeValue_c rootValue, Object value, DataType_c dt, ComponentInstance_c ci) {
		RuntimeValue_c result = null;
		if (rootValue != null) {
			result = rootValue;
		}
		UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(dt);
		boolean realizedTypeFound = false;
		if (udt != null) {
			DataType_c rootType = getCoreTypeForDt(dt);
			Class<?> realizedType = getClassForType(dt);
			if (realizedType != null) {
				Method[] methods = realizedType.getDeclaredMethods();
				for (Method meth : methods) {
					if (meth.getName().equals("getValue")
							&& meth.getParameterTypes().length == 0) {
						try {
							result = new RuntimeValue_c(root);
							result.relateAcrossR3307To(rootType);
							result.Setvalue(meth.invoke(value));
							realizedTypeFound = true;
						} catch (IllegalArgumentException iae) {
							// TODO When dts0100910433 is promoted convert these
							// to Runtime exceptions
							LOG
									.LogInfo("Illegal argument exception converting realized data for Verifier invocation. "
											+ iae.getLocalizedMessage());
						} catch (IllegalAccessException iae) {
							LOG
									.LogInfo("Illegal access exception converting realized data for Verifier invocation. "
											+ iae.getLocalizedMessage());
						} catch (InvocationTargetException ite) {
							LOG
									.LogInfo("Invocation target exception converting realized data for Verifier invocation. "
											+ ite.getLocalizedMessage());
						}
					}
				}
				if (!realizedTypeFound) {
					Object javaMember = null;
					Field[] fields = value.getClass().getDeclaredFields();
					for (Field field : fields) {
						if (field.getName().equals("value")) {
							try {
								javaMember = field.get(value);
							} catch (IllegalArgumentException e) {
								String ex = e.getLocalizedMessage();
								LOG.LogInfo("Illegal argument exception while accessing realized structured data type: " + ex);
							} catch (IllegalAccessException e) {
								String ex = e.getLocalizedMessage();
								LOG.LogInfo("Illegal access exception while accessing realized structured data type: " + ex);
							}
							break;
						}
					}
					if (javaMember != null) {
						result = convertFromRawJavaType(javaMember, rootType, ci);
						realizedTypeFound = true;
					}
				}
			}
		}
		if (!realizedTypeFound) {
			DataType_c rootType = getCoreTypeForDt(dt);
			if (rootType != null && rootType.getName().equals("integer")) {
				if (value instanceof Integer) {
					if (rootValue == null) {
						result = new RuntimeValue_c(root);
						result.relateAcrossR3307To(dt);
					}
					result.Setvalue(Gd_c.Int_to_string(((Integer) value)));
				} else if (value instanceof BPInteger) {
					if (rootValue == null) {
						result = new RuntimeValue_c(root);
						result.relateAcrossR3307To(dt);
					}
					result.Setvalue(Gd_c.Int_to_string(((BPInteger) value)
							.getValue()));
				}
			} else if (rootType != null && rootType.getName().equals("string")) {

				if (value instanceof String) {
					if (rootValue == null) {
						result = new RuntimeValue_c(root);
						result.relateAcrossR3307To(dt);
					}
					result.Setvalue(value);
				} else if (value instanceof BPString) {
					if (rootValue == null) {
						result = new RuntimeValue_c(root);
						result.relateAcrossR3307To(dt);
					}
					result.Setvalue(((BPString) value).getValue());
				}
			} else if (rootType != null && rootType.getName().equals("boolean")) {

				if (value instanceof Boolean) {
					if (rootValue == null) {
						result = new RuntimeValue_c(root);
						result.relateAcrossR3307To(dt);
					}
					result.Setvalue(Gd_c.Boolean_to_string((Boolean)value));
				} else if (value instanceof BPBoolean) {
					if (rootValue == null) {
						result = new RuntimeValue_c(root);
						result.relateAcrossR3307To(dt);
					}
					result.Setvalue(Gd_c.Boolean_to_string(((BPBoolean)value).getValue()));
				}
			} else if (rootType != null && rootType.getName().equals("real")) {

					if (value instanceof Float) {
						if (rootValue == null) {
							result = new RuntimeValue_c(root);
							result.relateAcrossR3307To(dt);
						}
						result.Setvalue(Gd_c.Real_to_string((Float)value));
					} else if (value instanceof BPFloat) {
						if (rootValue == null) {
							result = new RuntimeValue_c(root);
							result.relateAcrossR3307To(dt);
						}
						result.Setvalue(Gd_c.Real_to_string(((BPFloat)value).getValue()));
					}
			} else if (rootType != null && rootType.getName().equals("unique_id")) {

				if (value instanceof UUID) {
					if (rootValue == null) {
						result = new RuntimeValue_c(root);
						result.relateAcrossR3307To(dt);
					}
					result.Setvalue(IdAssigner.convertFromRuntimeUUID((UUID)value, ci));
				} else if (value instanceof BPUniqueId) {
					if (rootValue == null) {
						result = new RuntimeValue_c(root);
						result.relateAcrossR3307To(dt);
					}
					result.Setvalue(Gd_c.Unique_id_to_string(((BPUniqueId)value).getValue()));
				}
			}
			else {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
                result.Setvalue(value);				
			}
		}
		return result;
	}

	private static RuntimeValue_c convertFromRawJavaType(Object value, DataType_c dt, ComponentInstance_c ci) {
		RuntimeValue_c result = null;
		ModelRoot root = dt.getModelRoot();
		String dtName = dt.getName();
		if (dtName.equals("integer")) {
			if (value instanceof Integer) {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
				result.Setvalue(Gd_c.Int_to_string(((Integer) value)));
			} else if (value instanceof BPInteger) {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
				result.Setvalue(Gd_c.Int_to_string(((BPInteger) value)
						.getValue()));
			}
		} else if (dtName.equals("string")) {
			if (value instanceof String) {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
				result.Setvalue(value);
			} else if (value instanceof BPString) {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
				result.Setvalue(((BPString) value).getValue());
			}
		} else if (dtName.equals("real")) {
			if (value instanceof Float) {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
				result.Setvalue(Gd_c.Real_to_string(((Float) value)));
			} else if (value instanceof BPFloat) {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
				result.Setvalue(Gd_c.Real_to_string(((BPFloat) value)
						.getValue()));
			}
		} else if (dtName.equals("unique_id")) {
			if (value instanceof UUID) {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
				result.Setvalue(IdAssigner.convertFromRuntimeUUID(value, ci));
			} else if (value instanceof BPUniqueId) {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
				result.Setvalue(IdAssigner.convertFromRuntimeUUID(((BPUniqueId)value).getValue(), ci));
			}
		}
		return result;
	}

	public static RuntimeValue_c marshallArrayValueIn(ModelRoot root,
			RuntimeValue_c rootValue, Object value, DataType_c dt,
			int arrayDepth, ComponentInstance_c ci) {
		RuntimeValue_c result = null;
		if (rootValue != null) {
			result = rootValue;
		}
		if (value instanceof ArrayList<?>) {
			ArrayValue_c arrayVal = null;
			if (rootValue == null) {
				result = new RuntimeValue_c(root);
				result.relateAcrossR3307To(dt);
				arrayVal = new ArrayValue_c(root);
				result.relateAcrossR3300To(arrayVal);
			} else {
				arrayVal = ArrayValue_c.getOneRV_AVLOnR3300(rootValue);
			}
			int index = 0;
			for (Object content : ((ArrayList<?>) value).toArray()) {
				ValueInArray_c[] vias = ValueInArray_c
						.getManyRV_VIAsOnR3302(arrayVal);
				ValueInArray_c via = null;
				for (ValueInArray_c viaCandidate : vias) {
					if (viaCandidate.getIndex() == index) {
						via = viaCandidate;
						break;
					}
				}
				RuntimeValue_c member = null;
				if (via == null) {
					via = new ValueInArray_c(root);
					via.setIndex(index);
					via.relateAcrossR3302To(arrayVal);
				} else {
					member = RuntimeValue_c.getOneRV_RVLOnR3302(via);
				}
				member = marshallContentIn(root, member, content, dt,
						arrayDepth - 1, ci);
				via.relateAcrossR3302To(member);
				index++;
			}
		}
		return result;
	}

	public static void executeRealizedCode(Stack_c stack) {
		ComponentInstance_c fee = ComponentInstance_c.getOneI_EXEOnR2930(stack);
		Component_c comp = Component_c.getOneC_COnR2955(fee);
		if (comp == null) {
			comp = Component_c.getOneC_COnR4201(ComponentReference_c
					.getOneCL_ICOnR2963(fee));
		}
		if (comp != null) {
			String className = pathToClassName(comp.Getpath(true, ""));
			Vm_c.Resetvalues();
			SystemModel_c system = (SystemModel_c) Ooaofooa
					.getDefaultInstance().getInstanceList(SystemModel_c.class)
					.getGlobal(comp.Getsystemid());
			boolean success = Vm_c.LoadQualifiedclass(system, className);
			if (success) {
				ComponentInstance_c waitingComp = null;
				synchronized (fee) {
					if (BPThread.qdFramesReady(stack)) {
						stack.Dequeue(false);
						StackFrame_c localStackFrame = StackFrame_c
								.getOneI_STFOnR2929(stack);
						Body_c body = Body_c.getOneACT_ACTOnR601(Block_c
								.getManyACT_BLKsOnR2923(BlockInStackFrame_c
										.getOneI_BSFOnR2923(localStackFrame)));
						StackFrame_c blockedFrame = StackFrame_c
								.getOneI_STFOnR2965Blocks(localStackFrame);
						ComponentInstance_c sender = ComponentInstance_c
								.getOneI_EXEOnR2930(Stack_c
										.getOneI_STACKOnR2943(blockedFrame));
						Vm_c.Addargumentvalue(false, "component_ref", sender);
						RequiredOperation_c reqOp = null;
						ProvidedOperation_c provOp = null;
						String messageName = null;
						boolean isOperation = false;
						int returnArrayDepth = 0;
						ExecutableProperty_c ep = null;
						// First, handle an invocation that expects a return
						// value
						ValueInStackFrame_c resultValue = null;
						resultValue = getWaitingReturnValue(blockedFrame);
						Value_c val = Value_c.getOneV_VALOnR2978(resultValue);
						if (val != null) {
							isOperation = true;
							MessageValue_c mv = MessageValue_c
									.getOneV_MSVOnR801(val);
							ProvidedExecutableProperty_c pep = ProvidedExecutableProperty_c
									.getOneSPR_PEPOnR841(mv);
							RequiredExecutableProperty_c rep = RequiredExecutableProperty_c
									.getOneSPR_REPOnR845(mv);
							if (pep != null) { // Provided Operation
								provOp = ProvidedOperation_c
										.getOneSPR_POOnR4503(pep);
								messageName = provOp.getName();
								ep = ExecutableProperty_c
										.getOneC_EPOnR4501(pep);
								Dimensions_c[] dims = Dimensions_c
										.getManyS_DIMsOnR4018(InterfaceOperation_c
												.getOneC_IOOnR4004(ep));
								returnArrayDepth = dims.length;
							} else { // Required Operation
								reqOp = RequiredOperation_c
										.getOneSPR_ROOnR4502(rep);
								messageName = reqOp.getName();
								ep = ExecutableProperty_c
										.getOneC_EPOnR4500(rep);
								Dimensions_c[] dims = Dimensions_c
										.getManyS_DIMsOnR4018(InterfaceOperation_c
												.getOneC_IOOnR4004(ep));
								returnArrayDepth = dims.length;
							}
						} else { // No return value expected, void call or
							// signal
							reqOp = RequiredOperation_c
									.getOneSPR_ROOnR685(RequiredOperationBody_c
											.getManyACT_ROBsOnR698(body));
							provOp = ProvidedOperation_c
									.getOneSPR_POOnR687(ProvidedOperationBody_c
											.getOneACT_POBOnR698(body));
							RequiredSignal_c reqSig = RequiredSignal_c
									.getOneSPR_RSOnR684(RequiredSignalBody_c
											.getOneACT_RSBOnR698(body));
							ProvidedSignal_c provSig = ProvidedSignal_c
									.getOneSPR_PSOnR686(ProvidedSignalBody_c
											.getOneACT_PSBOnR698(body));
							if (reqOp != null) {
								isOperation = true;
								messageName = reqOp.getName();
								ep = ExecutableProperty_c
										.getOneC_EPOnR4500(RequiredExecutableProperty_c
												.getOneSPR_REPOnR4502(reqOp));
							} else if (provOp != null) {
								isOperation = true;
								messageName = provOp.getName();
								ep = ExecutableProperty_c
										.getOneC_EPOnR4501(ProvidedExecutableProperty_c
												.getOneSPR_PEPOnR4503(provOp));
							} else if (reqSig != null) {
								messageName = reqSig.getName();
								ep = ExecutableProperty_c
										.getOneC_EPOnR4500(RequiredExecutableProperty_c
												.getOneSPR_REPOnR4502(reqSig));
							} else if (provSig != null) {
								messageName = provSig.getName();
								ep = ExecutableProperty_c
										.getOneC_EPOnR4501(ProvidedExecutableProperty_c
												.getOneSPR_PEPOnR4503(provSig));
							}
						}
						ComponentInstance_c ci = ComponentInstance_c
								.getOneI_EXEOnR2930(stack);
						if (ep != null) {
							PropertyParameter_c[] pParms = PropertyParameter_c
									.getManyC_PPsOnR4006(ep);
							PropertyParameter_c cursor = null;
							for (PropertyParameter_c parm : pParms) {
								PropertyParameter_c succeeds = PropertyParameter_c
										.getOneC_PPOnR4021Succeeds(parm);
								if (succeeds == null) {
									cursor = parm;
									break;
								}
							}
							Map<RuntimeValue_c, ParameterValue> argMap = new HashMap<RuntimeValue_c, ParameterValue>();
							while (cursor != null) {
								marshallValueOut(localStackFrame, cursor, null,
										argMap, ci);
								cursor = PropertyParameter_c
										.getOneC_PPOnR4021Precedes(cursor);
							}
							Vm_c.Execute(messageName, fee.getRealizedby(),
									localStackFrame.getStack_frame_id());
							for (RuntimeValue_c rtVal : argMap.keySet()) {
								marshallContentIn(rtVal.getModelRoot(), rtVal,
										argMap.get(rtVal).getValue(),
										DataType_c.getOneS_DTOnR3307(rtVal),
										argMap.get(rtVal).getArrayDepth(), ci);
							}
						} else {
							LOG.LogInfo("Message not delivered.");
						}
						if (ci != null) { // If component instance is null we're
							// terminating
							stack.Pop(!isOperation);
							if (isOperation) {
								localStackFrame.unrelateAcrossR2943From(stack);
								StackFrame_c topStFr = StackFrame_c
										.getOneI_STFOnR2929(stack);
								if (topStFr == localStackFrame) {
									topStFr.unrelateAcrossR2929From(stack);
								}
								if (resultValue != null) {
									// Marshall the java result back into
									// Verifier
ValueInStackFrame_c localVsf = ValueInStackFrame_c.getOneI_VSFOnR2951(localStackFrame);
									if (localVsf == null) {
										localVsf = new ValueInStackFrame_c( localStackFrame.getModelRoot());
										localVsf .relateAcrossR2951To(localStackFrame);
									}
									DataType_c dt = DataType_c
											.getOneS_DTOnR820(Value_c
													.getOneV_VALOnR2978(resultValue));
									RuntimeValue_c rtV = marshallContentIn(
											blockedFrame.getModelRoot(), null,
											Vm_c.getRawResult(), dt,
											returnArrayDepth, ci);
									rtV.relateAcrossR3305To(localVsf);
									rtV.relateAcrossR3310To(localStackFrame);
								}
								if (blockedFrame != null) {
									Stack_c invokingStack = Stack_c
											.getOneI_STACKOnR2943(blockedFrame);
									localStackFrame.setReadyforinterrupt(false);
									invokingStack
											.Enqueue(
													false,
													blockedFrame
															.getStack_frame_id(),
													localStackFrame
															.getStack_frame_id());
									localStackFrame.setReadyforinterrupt(true);
									waitingComp = ComponentInstance_c
											.getOneI_EXEOnR2930(invokingStack);
								}
							}
							if (waitingComp != null) {
								waitingComp.Notify();
							}
						}
					}
				}
			} else {
				LOG.LogInfo("Realized class not found: " + className);
			}
		}
	}

	private static ValueInStackFrame_c getWaitingReturnValue(
			StackFrame_c blockedFrame) {
		ValueInStackFrame_c[] vsfs = ValueInStackFrame_c
				.getManyI_VSFsOnR2951(blockedFrame);
		// work backwards through the list looking for one which is not computed
		for (int i = vsfs.length - 1; i >= 0; i--) {
			RuntimeValue_c rtV = RuntimeValue_c.getOneRV_RVLOnR3305(vsfs[i]);
			if (rtV == null) {
				// this is the one
				return vsfs[i];
			}
		}
		return null;
	}

	public static String pathToClassName(String name) {
		String[] segments = name.split("::");
		String className = "";
		if (segments.length > 1) {
			for (int i = 1; i < segments.length; i++) {
				if (i != 1) {
					className += ".";
				}
				if (i < segments.length - 1) { // class name must retain
					// capitalization
					segments[i] = segments[i].toLowerCase();
				}
				className += segments[i].replaceAll(" ", "");
			}
			return className;
		} else {
			return name;
		}
	}

	private static Object getInstanceForType(DataType_c dt) {
		Object result = null;
		Class<?> realizedType = getClassForType(dt);
		if (realizedType != null) {
			try {
				Constructor<?> ctor = realizedType.getConstructor(new Class<?>[0]);
				try {
					result = ctor.newInstance(new Object[0]);
				} catch (IllegalArgumentException e) {
					String ex = e.getLocalizedMessage();
					LOG.LogInfo("Illegal argument exception while instantiating data value: " + ex);
				} catch (InstantiationException e) {
					String ex = e.getLocalizedMessage();
					LOG.LogInfo("Instantiation exception exception while instantiating data value: " + ex);
				} catch (IllegalAccessException e) {
					String ex = e.getLocalizedMessage();
					LOG.LogInfo("Illegal access exception exception while instantiating data value: " + ex);
				} catch (InvocationTargetException e) {
					String ex = e.getLocalizedMessage();
					LOG.LogInfo("Invocation target exception while instantiating data value: " + ex);
				}
			} catch (SecurityException e) {
				String ex = e.getLocalizedMessage();
				LOG.LogInfo("Security exception while instantiating data value: " + ex);
			} catch (NoSuchMethodException e) {
				String ex = e.getLocalizedMessage();
				LOG.LogInfo("Constructor not found while instantiating data value: " + ex);
			} 
		}
		return result;
	}
	private static Class<?> getClassForType(DataType_c dt) {
		Class<?> result = null;
		Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
				.getOnePE_PEOnR8001(dt));
		if (pkg != null && dt != null) {
			String className = pathToClassName(pkg.Getpath("") + "::"
					+ dt.getName());
			SystemModel_c sys = SystemModel_c.getOneS_SYSOnR1405(pkg);
			if (sys != null) {
				BPClassLoader cl = Vm_c.getVmCl(sys.getSys_id());
				if (cl != null) {
					try {
						result = cl.loadClass(className);
					} catch (ClassNotFoundException cnfe) {
						// This is expected, do nothing
					}
					if (result == null) {
						DataType_c definition = DataType_c.getOneS_DTOnR18(
								           UserDataType_c.getOneS_UDTOnR17(dt));
						if (definition != null) {
							return getClassForType(definition);
						}
					}
				}
			}
		}
		return result;
	}

	private static void marshallValueOut(StackFrame_c localStackFrame,
			PropertyParameter_c prop_param, Object toMarshall,
			Map<RuntimeValue_c, ParameterValue> argMap, ComponentInstance_c ci) {
		DataType_c dt = DataType_c.getOneS_DTOnR4007(prop_param);
		LocalValue_c[] lvls = LocalValue_c.getManyL_LVLsOnR3001(Local_c
				.getManyL_LCLsOnR3000(BlockInStackFrame_c
						.getManyI_BSFsOnR2923(localStackFrame)));
		RuntimeValue_c parVal = null;
		for (int i = 0; i < lvls.length; i++) {
			PropertyParameter_c pp = PropertyParameter_c
					.getOneC_PPOnR3017(lvls[i]);
			if (pp == prop_param) {
				parVal = RuntimeValue_c.getOneRV_RVLOnR3306(Local_c
						.getOneL_LCLOnR3001(lvls[i]));
				if (parVal == null) {
					java.util.UUID rvID = lvls[i]
							.Getruntimevalue(localStackFrame
									.getStack_frame_id());
					parVal = (RuntimeValue_c) lvls[i].getModelRoot()
							.getInstanceList(RuntimeValue_c.class).getGlobal(
									rvID);
				}
				break;
			}
		}
		if (parVal != null) {
			Dimensions_c [] dims = Dimensions_c.getManyS_DIMsOnR4017(prop_param);
			boolean handleByRef = prop_param.getBy_ref() == 1;
			Object value = marshallContentOut(parVal, dt, toMarshall, dims.length, handleByRef, ci);
			Vm_c.Addargumentvalue(value);
			if (handleByRef) {
				argMap.put(parVal, new ParameterValue(value,
						dims.length));
			}
		}
	}

	private static Object marshallContentOut(RuntimeValue_c parVal,
			DataType_c dt, Object toMarshall, int arrayDepth, boolean handleByRef, ComponentInstance_c ci) {
		Object result = null;
		if (arrayDepth > 0) {
			result = marshallArrayValueOut(toMarshall, dt, parVal, arrayDepth, ci);
		}
		else {
			DataType_c rootType = getCoreTypeForDt(dt);
			CoreDataType_c cdt = CoreDataType_c.getOneS_CDTOnR17(rootType);
			StructuredDataType_c sdt = StructuredDataType_c
					.getOneS_SDTOnR17(rootType);
			EnumerationDataType_c edt = EnumerationDataType_c
					.getOneS_EDTOnR17(rootType);
			if (cdt != null) {
				result = marshallCoreValueOut(dt, toMarshall, parVal, handleByRef, ci);
			} else if (sdt != null) {
				result = marshallStructuredValueOut(dt, toMarshall, parVal, ci);
			} else if (edt != null) {
				result = marshallEnumeratedValueOut(dt, toMarshall, parVal);
			}
			
		}
		return result;
	}

	private static Object marshallStructuredValueOut(DataType_c dt,
			Object toMarshall, RuntimeValue_c parVal, ComponentInstance_c ci) {
		StructuredValue_c strV = StructuredValue_c.getOneRV_SVLOnR3300(parVal);
		Object result = null;
		DataType_c coreType = getCoreTypeForDt(dt);
		Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
				.getOnePE_PEOnR8001(dt));
		String typeName = pathToClassName(pkg.Getpath("") + "::"
				+ dt.getName());
		BPClassLoader bpcl = Vm_c.getVmCl(pkg.Getsystemid());
			Class<?> clazz = null;
			try {
				clazz = bpcl.loadClass(typeName);
			} catch (ClassNotFoundException e) {
				// Expected code path, do nothing
			}
			if (clazz == null) {
				typeName = pathToClassName(pkg.Getpath("") + "::" + coreType.getName());
				try {
					clazz = bpcl.loadClass(typeName);
				} catch (ClassNotFoundException e) {
					String ex = e.getLocalizedMessage();
					LOG.LogInfo("Class not found exception while loading structured data type: " + ex);
				}
			}
			if (clazz != null) {
				if (toMarshall == null) {
					Constructor<?> ctor = null;
					try {
						ctor = clazz
								.getConstructor(new Class[0]);
					} catch (SecurityException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Security exception while getting constructor for realized data type: " + ex);
					} catch (NoSuchMethodException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("No such nethod exception while getting constructor for realized data type: " + ex);
					}
					try {
						if (ctor != null) {
						  result = ctor.newInstance(new Object[0]);
						}
						else {
							LOG.LogInfo("No constructor found for realized data type.");
						}
					} catch (IllegalArgumentException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Illegal argument exception while calling constructor for realized data type: " + ex);
					} catch (InstantiationException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Instantiation exception while calling constructor for realized data type: " + ex);
					} catch (IllegalAccessException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Illegal access exception while calling constructor for realized data type: " + ex);
					} catch (InvocationTargetException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Invocation target exception while calling constructor for realized data type: " + ex);
					}
				} else {
					result = toMarshall;
				}
				ValueInStructure_c[] viss = ValueInStructure_c
				.getManyRV_VISsOnR3301(strV);
				StructureMember_c[] members = StructureMember_c.getManyS_MBRsOnR44(StructuredDataType_c.getOneS_SDTOnR17(coreType));
				for (StructureMember_c member: members) {
					for (ValueInStructure_c vis : viss) {
						if (vis.getName().equals(member.getName())) {
							Field field = null;
							try {
								field = clazz.getDeclaredField(vis.getName());
							} catch (SecurityException e) {
								String ex = e.getLocalizedMessage();
								LOG.LogInfo("Security exception while getting field for realized data type: " + ex);
							} catch (NoSuchFieldException e) {
								String ex = e.getLocalizedMessage();
								LOG.LogInfo("No field found for realized data type: " + ex);
							}
							RuntimeValue_c rVal = RuntimeValue_c
									.getOneRV_RVLOnR3301(vis);
							DataType_c memberDt = DataType_c
									.getOneS_DTOnR45(member);
							Object value = null;
							if (toMarshall != null) {
								try {
									value = field.get(toMarshall);
								} catch (IllegalArgumentException e) {
									String ex = e.getLocalizedMessage();
									LOG.LogInfo("Illegal argument exception while getting field value for realized data type: " + ex);
								} catch (IllegalAccessException e) {
									String ex = e.getLocalizedMessage();
									LOG.LogInfo("Illegal access exception while getting field value for realized data type: " + ex);
								}
							}
							setField(result, member, field, rVal, memberDt,
									value, ci);
							// continue to next member
							break;
						}
					}
				}
			}
		return result;
	}

	private static void setField(Object result, StructureMember_c member,
			Field field, RuntimeValue_c rVal, DataType_c memberDt, Object value, ComponentInstance_c ci) {
		Dimensions_c [] dims = Dimensions_c.getManyS_DIMsOnR53(member);
		value = marshallContentOut(rVal, memberDt, value, dims.length, false, ci);
		try {
			field.set(result, value);
		} catch (IllegalArgumentException e) {
			String ex = e.getLocalizedMessage();
			LOG.LogInfo("Illegal argument exception while setting field value for realized data type: " + ex);
		} catch (IllegalAccessException e) {
			String ex = e.getLocalizedMessage();
			LOG.LogInfo("Illegal access exception while setting field value for realized data type: " + ex);
		}
	}

	private static Object marshallCoreValueOut(DataType_c dt, Object toMarshall, RuntimeValue_c parVal, boolean isByRef, ComponentInstance_c ci) {
		UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(dt);
		DataType_c coreType = getCoreTypeForDt(dt);
		boolean realizedTypeFound = false;
		if (udt != null) {
			Object inst = toMarshall;
			if (inst == null) {
				inst = getInstanceForType(dt);
			}
			if (inst != null) {
			  Method[] methods = inst.getClass().getDeclaredMethods();
			  for (Method meth : methods) {
				if (meth.getName().equals("setValue") &&
                        meth.getParameterTypes().length == 1) {
					try {
						meth.invoke(inst, convertToRawJavaType(parVal.Getvalue(),coreType, false, ci));
						realizedTypeFound = true;
						toMarshall = inst;
					} catch (IllegalArgumentException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Illegal argument exception while invoking accessor value for realized data type: " + ex);
					} catch (IllegalAccessException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Illegal access exception while invoking accessor value for realized data type: " + ex);
					} catch (InvocationTargetException e) {
						String ex = e.getLocalizedMessage();
						LOG.LogInfo("Invocation target exception while invoking accessor value for realized data type: " + ex);
					}
				}
			}
			  if (!realizedTypeFound) {
					Field[] fields = inst.getClass().getDeclaredFields();
					for (Field field: fields) {
						if (field.getName().equals("value")) {
							try {
								field.set(inst, convertToRawJavaType(parVal.Getvalue(), coreType, false, ci));
								realizedTypeFound = true;
								toMarshall = inst;
							} catch (IllegalArgumentException e) {
								String ex = e.getLocalizedMessage();
								LOG.LogInfo("Illegal argument exception while accessing field of realized data type: " + ex);
							} catch (IllegalAccessException e) {
								String ex = e.getLocalizedMessage();
								LOG.LogInfo("Illegal access exception while accessing field of realized data type: " + ex);
							}
							break;
						}
					}
					}
		  }
		}
		if (!realizedTypeFound) {
		  if (toMarshall == null) {
		    toMarshall = convertToRawJavaType(parVal.Getvalue(),
                                                             coreType, isByRef, ci);
		  }
		  else {
			Object result = convertToRawJavaType(parVal.Getvalue(),
                                                               coreType, false, ci);
			if (isByRef == true) {
				// Need set the value of the existing toMarshall instance
				if (toMarshall instanceof BPInteger) {
					((BPInteger)toMarshall).setValue((Integer)result);
				}
				else if (toMarshall instanceof BPString) {
					((BPString)toMarshall).setValue((String)result);
				}
				else if (toMarshall instanceof BPFloat) {
					((BPFloat)toMarshall).setValue((Float)result);
				}
				else if (toMarshall instanceof BPUniqueId) {
					((BPUniqueId)toMarshall).setValue((UUID)result);
				}
				else if (toMarshall instanceof BPBoolean) {
					((BPBoolean)toMarshall).setValue((Boolean)result);
				}
			}
			else {
				toMarshall = result;
			}
		  }
		}
		return toMarshall;
	}

	@SuppressWarnings("unchecked")
	private static Object marshallArrayValueOut(Object toMarshall,
			DataType_c dt, RuntimeValue_c parVal, int arrayDepth, ComponentInstance_c ci) {
		ArrayValue_c av = ArrayValue_c.getOneRV_AVLOnR3300(parVal);
		ArrayList<Object> result = null;
		if (toMarshall == null) {
			result = new ArrayList<Object>();
		} else {
			if (toMarshall instanceof ArrayList<?>) {
			  result = (ArrayList<Object>) toMarshall;
			}
			else {
				LOG.LogInfo("Expected instance of an arraylist type for loading into a modeled array.");
			}
		}
		ValueInArray_c[] virs = ValueInArray_c.getManyRV_VIAsOnR3302(av);
		for (ValueInArray_c vir : virs) {
			RuntimeValue_c content = RuntimeValue_c
					.getOneRV_RVLOnR3302(vir);
			Object value = null;
			if (toMarshall != null && (result.size() > vir.getIndex())) {
				value = result.get(vir.getIndex());
			}
			value = marshallContentOut(content, dt, value, arrayDepth -1, false, ci);
			while (result.size() <= vir.getIndex()) {
				result.add(null);
			}
			result.set(vir.getIndex(), value);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static Object marshallEnumeratedValueOut(DataType_c dt,
			Object toMarshall, RuntimeValue_c parVal) {
		Class<?> realizedEDT = getClassForType(dt);
		if (realizedEDT != null && realizedEDT.isEnum()) {
			Object parValue = parVal.Getvalue();
			if (parValue instanceof String) {
				String[] parts = ((String) parValue).split("::");
				if (parts.length == 2) {
				  try {
					return Enum.valueOf((Class<Enum>) realizedEDT, parts[1]);
				  }
				  catch (IllegalArgumentException iae) {
						String ex = iae.getLocalizedMessage();
						LOG.LogInfo("Illegal argument exception while taking value of realized enumeration: " + ex);
				  }
				} else {
					LOG.LogInfo("Unexpected format for outgoing enumeration value.");
				}
			} else {
				LOG.LogInfo("Outgoing enumeration value was not in expected string format.");
			}
		} else {
			// Handle as simple string here
			return parVal.Getvalue();
		}
		return null;
	}

	static DataType_c getCoreTypeForDt(DataType_c dt) {
		CoreDataType_c cdt = CoreDataType_c.getOneS_CDTOnR17(dt);
		UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(dt);
		if (cdt != null) {
			return dt;
		} else if (udt != null) {
			return getCoreTypeForDt(DataType_c.getOneS_DTOnR18(udt));
		}
		// Must be an enumerated or structured type, there is no core type
		return dt;
	}

	public static void handleReturnValue(Stack_c stack) {
		ComponentInstance_c ci = ComponentInstance_c.getOneI_EXEOnR2930(stack);
		if (ci != null) {
			ci.Notify();
		}
	}

}
