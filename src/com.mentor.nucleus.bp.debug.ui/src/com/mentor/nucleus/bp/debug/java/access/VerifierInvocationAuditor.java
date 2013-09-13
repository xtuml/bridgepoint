package com.mentor.nucleus.bp.debug.java.access;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import lib.BPBoolean;
import lib.BPFloat;
import lib.BPInteger;
import lib.BPString;
import lib.BPUniqueId;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.Dimensions_c;
import com.mentor.nucleus.bp.core.Elementtypeconstants_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.Enumerator_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.Ifdirectiontype_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.PropertyParameter_c;
import com.mentor.nucleus.bp.core.StructureMember_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.Vm_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.BPClassLoader;
import com.mentor.nucleus.bp.debug.ui.model.BPDebugTarget;

public class VerifierInvocationAuditor {

    final static String CR = System.getProperty("line.separator");

    public static String performRealizedCodeAudit(Package_c pkg) {
        String result = "Beginning binding check on package " + pkg.getName()
                + CR + CR;
        try {
            result += internalPerformRealizedCodeAudit(pkg);
        } catch (Throwable t) {
            result += "An error occurred while auditing this package: " + t.getMessage() + CR;
        }
        result += "Binding check on " + pkg.getName() + " complete." + CR + CR;
        return result;
    }

    public static String performRealizedCodeAudit(Component_c comp) {
        String result = "Beginning binding check on component "
                + comp.getName() + CR + CR;
        try {
            result += internalPerformRealizedCodeAudit(comp);
        } catch (Throwable t) {
            result += "An error occurred while auditing this component: " + t.getMessage() + CR;
        }
        result += "Binding check on " + comp.getName() + " complete." + CR + CR;
        return result;
    }

    private static String internalPerformRealizedCodeAudit(
            NonRootModelElement elem) {
        String result = "";
        result += "Checking component realizations:" + CR;
        Set<NonRootModelElement> components = null;
        try {
            components = collectElements(elem,
                    Elementtypeconstants_c.COMPONENT);
            if (elem instanceof Component_c) {
                components.add(elem);
            }
            clearBindings(components.toArray(new Component_c[0]));              
            for (NonRootModelElement component : components) {
                result += performAudit((Component_c) component);
            }
            result += "Component check complete." + CR + CR + CR;
            result += "Checking interface realizations:" + CR;

            Interface_c[] interfaces = collectElements(elem,
                    Elementtypeconstants_c.INTERFACE).toArray(
                    new Interface_c[0]);
            for (Interface_c iface : interfaces) {
                result += performAudit(iface);
            }
            result += "Interface check complete." + CR + CR + CR;
            result += "Checking data type realizations:" + CR;
            DataType_c[] dts = collectElements(elem,
                    Elementtypeconstants_c.DATATYPE).toArray(
                    new DataType_c[0]);
            for (DataType_c dataType : dts) {
                result += performAudit(dataType);
            }
            result += "Data type check complete." + CR;
            return result;
        } catch (ElementCollectionException cp) {
            return cp.getMessage();
        }
    }

    private static void clearBindings(Component_c[] components) {
        // Clear previously loaded classes
        for (Component_c component : components) {
            SystemModel_c system = (SystemModel_c)Ooaofooa.
            getDefaultInstance().getInstanceList(SystemModel_c.class).
                                               getGlobal(component.Getsystemid());
            Vm_c.resetClassLoader(system);
            Vm_c.Setuserclasspath(system.getSys_id());
            String[] paths = component.getRealized_class_path().split(";");
            for(String path: paths) {
              if (!path.equals("")) {
                Vm_c.Adduserclasspath(system, path);
              }
            }
        }
        BPClassLoader.resetTheDefinitionsCache();
    }

    private static Set<NonRootModelElement> collectElements(
            NonRootModelElement elem, int elemType)
            throws ElementCollectionException {
        SortedSet<NonRootModelElement> result = new TreeSet<NonRootModelElement>(
                new Comparator<NonRootModelElement>(){

                    @Override
                    public int compare(NonRootModelElement o1,
                            NonRootModelElement o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
        Component_c[] components = new Component_c[0];
        Package_c[] packages = new Package_c[0];
        if (elem instanceof Package_c) {
            components = Component_c.getManyC_CsOnR8001(PackageableElement_c
                    .getManyPE_PEsOnR8000((Package_c) elem));
            packages = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c
                    .getManyPE_PEsOnR8000((Package_c) elem));
            ComponentReference_c[] refs = ComponentReference_c
                    .getManyCL_ICsOnR8001(PackageableElement_c
                            .getManyPE_PEsOnR8000((Package_c) elem));
            for (ComponentReference_c ref : refs) {
                result.addAll(collectElements(ref, elemType));
            }
        } else if (elem instanceof Component_c) {
            components = Component_c.getManyC_CsOnR8001(PackageableElement_c
                    .getManyPE_PEsOnR8003((Component_c) elem));
            packages = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c
                    .getManyPE_PEsOnR8003((Component_c) elem));
            ComponentReference_c[] refs = ComponentReference_c
                    .getManyCL_ICsOnR8001(PackageableElement_c
                            .getManyPE_PEsOnR8003((Component_c) elem));
            for (ComponentReference_c ref : refs) {
                result.addAll(collectElements(ref, elemType));
            }
        } else if (elem instanceof ComponentReference_c) {
            components = new Component_c[] { Component_c
                    .getOneC_COnR4201((ComponentReference_c) elem) };
            packages = new Package_c[0];
        } else if (elem instanceof DataType_c) {
            DataType_c[] dts = DataType_c.getManyS_DTsOnR45(StructureMember_c
                    .getManyS_MBRsOnR44(StructuredDataType_c
                            .getOneS_SDTOnR17((DataType_c) elem)));
            result.addAll(Arrays.asList(dts));
            for (DataType_c dt : dts) {
                result.addAll(collectElements(dt, elemType));
            }
        }
        for (Component_c component : components) {
            if (component.getIsrealized() == true) {
                if (elemType == Elementtypeconstants_c.COMPONENT) {
                    result.add(component);
                } else if (elemType == Elementtypeconstants_c.INTERFACE) {
                    Interface_c[] interfaces = Interface_c
                            .getManyC_IsOnR4012(InterfaceReference_c
                                    .getManyC_IRsOnR4016(Port_c
                                            .getManyC_POsOnR4010(component)));
                    result.addAll(Arrays.asList(interfaces));
                } else if (elemType == Elementtypeconstants_c.DATATYPE) {
                    Interface_c[] interfaces = Interface_c
                            .getManyC_IsOnR4012(InterfaceReference_c
                                    .getManyC_IRsOnR4016(Port_c
                                            .getManyC_POsOnR4010(component)));
                    DataType_c[] dts = DataType_c
                            .getManyS_DTsOnR4007(PropertyParameter_c
                                    .getManyC_PPsOnR4006(ExecutableProperty_c
                                            .getManyC_EPsOnR4003(interfaces)));
                    result.addAll(Arrays.asList(dts));
                    for (DataType_c dt : dts) {
                        result.addAll(collectElements(dt, elemType));
                    }
                }
            }
            result.addAll(collectElements(component, elemType));
        }
        for (Package_c pack : packages) {
            result.addAll(collectElements(pack, elemType));
        }
        return result;
    }

    private static String performAudit(Component_c comp) {
        String result = "Checking component " + comp.getName() + CR;
        Class<?> realizedTarget = null;
        String className = "";
        BPClassLoader cl = null;
        try {
            cl = Vm_c.getVmCl(comp);
            className = BPDebugTarget.getClassNameForComponent(comp);
            realizedTarget = cl.loadClass(className);
        } catch (ClassNotFoundException cnf) {
            result += "No realized class found for " + comp.getName() + ". "
                    + "Expected to find class: " + className + "." + CR;
        } catch (IllegalStateException ise) {
            result += ise.getMessage() + CR;
        }
        if ((realizedTarget != null) && (cl != null)) {
            // TODO check class implements the expected interfaces
            Port_c[] ports = Port_c.getManyC_POsOnR4010(comp);
            Class<?>[] ctorArgs = new Class[ports.length];
            for (int i = 0; i < ports.length; i++) {
                className = BPDebugTarget.getClassNameForPort(ports[i]);
                try {
                    ctorArgs[i] = cl.loadClass(className);
                } catch (ClassNotFoundException cnf) {
                    result += "No realized interface found for " + comp + ". "
                            + "Expected to find class: " + className + "." + CR;
                }
            }
            try {
                @SuppressWarnings("unused")
                Constructor<?> ctor = realizedTarget.getConstructor(ctorArgs);
            } catch (SecurityException e) {
                result += "Security exception getting constructor for realized component: "
                        + comp + "." + CR;
                result += e.getLocalizedMessage() + CR;
            } catch (NoSuchMethodException e) {
                result += "No matching constructor found for realized component: "
                        + comp + "." + CR;
                result += "Realized component constructor must accept the following arguments:"
                        + CR;
                for (int i = 0; i < ports.length; i++) {
                    result += "\t"
                            + BPDebugTarget.getClassNameForPort(ports[i]) + CR;
                }
            }
        }
        result += "Component check on " + comp.getName() + " complete." + CR
                + CR;
        return result;
    }

    private static String performAudit(Interface_c iface) {
        String result = "Checking interface " + iface.getName()
                + " Requirer to Provider" + CR;
        String ifaceName = "I" + iface.getName().replaceAll(" ", "");
        String ifacePath = BPDebugTarget.getClassPathForInterface(iface);
        String className = ifacePath + "." + ifaceName + "ToProvider";
        Class<?> realizedInterface = null;
        BPClassLoader cl = null;
        try {
            cl = Vm_c.getVmCl(iface);
            realizedInterface = cl.loadClass(className);
        } catch (ClassNotFoundException cnf) {
            result += "No realized interface found for the provider direction of "
                    + iface.getName()
                    + ". "
                    + "Expected to find class: "
                    + className + "." + CR;
        } catch (IllegalStateException ise) {
            result += ise.getMessage() + CR;
        }
        if ((realizedInterface != null) && (cl != null)) {
            ExecutableProperty_c[] props = ExecutableProperty_c
                    .getManyC_EPsOnR4003(iface);
            for (ExecutableProperty_c prop : props) {
                int dir = Ifdirectiontype_c.OOA_UNINITIALIZED_ENUM;
                InterfaceOperation_c iOp = InterfaceOperation_c
                        .getOneC_IOOnR4004(prop);
                if (iOp != null) {
                    dir = iOp.getDirection();
                } else {
                    InterfaceSignal_c sig = InterfaceSignal_c
                            .getOneC_ASOnR4004(prop);
                    if (sig != null) {
                        dir = sig.getDirection();
                    }
                }
                if (dir == Ifdirectiontype_c.ClientServer) {
                    // ClientServer == To Provider
                    result += peformAudit(realizedInterface, prop, cl);
                }
            }
        }
        result += "Interface check on " + iface.getName()
                + " Requirer to Provider complete." + CR + CR;
        result += "Checking interface " + iface.getName()
                + " Provider to Requirer" + CR;
        className = ifacePath + "." + ifaceName + "FromProvider";
        realizedInterface = null;
        try {
            cl = Vm_c.getVmCl(iface);
            realizedInterface = cl.loadClass(className);
        } catch (ClassNotFoundException cnf) {
            result += "No realized interface found for the requirer direction of "
                    + iface.getName()
                    + ". "
                    + "Expected to find class: "
                    + className + "." + CR;
        } catch (IllegalStateException ise) {
            result += ise.getMessage() + CR;
        }
        if ((realizedInterface != null) && (cl != null)) {
            ExecutableProperty_c[] props = ExecutableProperty_c
                    .getManyC_EPsOnR4003(iface);
            for (ExecutableProperty_c prop : props) {
                int dir = Ifdirectiontype_c.OOA_UNINITIALIZED_ENUM;
                InterfaceOperation_c iOp = InterfaceOperation_c
                        .getOneC_IOOnR4004(prop);
                if (iOp != null) {
                    dir = iOp.getDirection();
                } else {
                    InterfaceSignal_c sig = InterfaceSignal_c
                            .getOneC_ASOnR4004(prop);
                    if (sig != null) {
                        dir = sig.getDirection();
                    }
                }
                if (dir == Ifdirectiontype_c.ServerClient) {
                    // ServerClient == From Provider
                    result += peformAudit(realizedInterface, prop, cl);
                }
            }
        }
        result += "Interface check on " + iface.getName()
                + " Provider to Requirer complete." + CR + CR;
        return result;
    }

    private static String peformAudit(Class<?> realizedInterface,
            ExecutableProperty_c prop, BPClassLoader cl) {
        String result = "";
        String methName = prop.getName();
        PropertyParameter_c[] pps = PropertyParameter_c
                .getManyC_PPsOnR4006(prop);
        PropertyParameter_c cursor = null;
        for (PropertyParameter_c parm : pps) {
            PropertyParameter_c succeeds = PropertyParameter_c
                    .getOneC_PPOnR4021Succeeds(parm);
            if (succeeds == null) {
                cursor = parm;
                break;
            }
        }
        ArrayList<Class<?>> argList = new ArrayList<Class<?>>();
        ArrayList<String> dtList = new ArrayList<String>();
        argList.add(ComponentInstance_c.class);
        int count = 0;
        while (count < pps.length) {
            DataType_c dt = DataType_c.getOneS_DTOnR4007(cursor);
            String className = getClassNameForDt(dt);
            Dimensions_c[] dims = Dimensions_c.getManyS_DIMsOnR4017(cursor);
            boolean byRef = cursor.getBy_ref() == 1;
            if (dims.length > 0) {
                className = "java.util.ArrayList";
            }
            if (className != null) {
                dtList.add(className);
            } else {
                dtList.add(getNameForCoreTpeOf(dt, byRef));
            }
            Class<?> class4DT = null;
            try {
                if (className != null) {
                    class4DT = cl.loadClass(className);
                } else {
                    class4DT = getClassForCoreTypeOf(dt, byRef);
                }
            } catch (ClassNotFoundException cnf) {
                // Some data types will not have a realized class so do nothing
            }
            argList.add(class4DT);
            cursor = PropertyParameter_c.getOneC_PPOnR4021Precedes(cursor);
            ++count;
        }
        Method messageHandler = null;
        try {
            messageHandler = realizedInterface.getMethod(methName, argList
                    .toArray(new Class<?>[0]));
        } catch (SecurityException e) {
            result += "Security exception getting realized message: "
                    + methName + "." + CR;
            result += e.getLocalizedMessage() + CR;
        } catch (NoSuchMethodException e) {
            result += "No matching realized message: " + methName + "." + CR;
            if (!dtList.isEmpty()) {
                result += "Realized message must accept the following arguments:"
                        + CR;
                result += "ComponentInstance_c";
                for (String dtName : dtList) {
                    result += ", " + dtName;
                }
                result += CR;
            }
        }
        if (messageHandler != null) {
            if ((messageHandler.getModifiers() & Modifier.PUBLIC) == 0) {
                result += "Realized message " + methName + " must be public."
                        + CR;
            }
            if ((messageHandler.getModifiers() & Modifier.STATIC) == 1) {
                result += "Realized message " + methName
                        + " must be non-static." + CR;
            }
            Class<?> actualReturnType = messageHandler.getReturnType();
            DataType_c modeledReturnType = DataType_c
                    .getOneS_DTOnR4008(InterfaceOperation_c
                            .getOneC_IOOnR4004(prop));
            if (modeledReturnType != null
                    && !modeledReturnType.getName().equals("void")) {
                String className = getClassNameForDt(modeledReturnType);
                Dimensions_c[] dims = Dimensions_c
                        .getManyS_DIMsOnR4018(InterfaceOperation_c
                                .getOneC_IOOnR4004(prop));
                if (dims.length > 0) {
                    className = "java.util.ArrayList";
                }
                Class<?> expectedReturnType = null;
                try {
                    if (className != null) {
                        expectedReturnType = cl.loadClass(className);
                    } else {
                        expectedReturnType = getClassForCoreTypeOf(
                                modeledReturnType, false);
                    }
                } catch (ClassNotFoundException cnf) {
                    // Some data types will not have a realized class so do
                    // nothing
                }
                if (actualReturnType != null) {
                    if (!(actualReturnType.isAssignableFrom(expectedReturnType))) {
                        result += "Realized message return type of "
                                + methName
                                + " ("
                                + actualReturnType.getName()
                                + ") is not compatible with the expected return type ("
                                + expectedReturnType.getName() + ")." + CR;
                    }
                } else {
                    result += "Realized message return type of " + methName
                            + " could not be resolved to a realized class."
                            + CR;
                }
            }

        }
        return result;
    }

    private static String performAudit(DataType_c dataType) {
        String result = "";
        StructuredDataType_c sdt = StructuredDataType_c
                .getOneS_SDTOnR17(dataType);
        UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(dataType);
        EnumerationDataType_c edt = EnumerationDataType_c
                .getOneS_EDTOnR17(dataType);
        if (sdt != null) {
            result = "Checking data type " + dataType.getName() + CR;
            Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
                    .getOnePE_PEOnR8001(dataType));
            if (pkg != null) {
                String typeName = VerifierInvocationHandler.pathToClassName(pkg
                        .Getpath("")
                        + "::" + dataType.getName());
                Class<?> realizedSDT = null;
                BPClassLoader bpcl = Vm_c.getVmCl(pkg.Getsystemid());
                try {
                    realizedSDT = bpcl.loadClass(typeName);
                } catch (ClassNotFoundException cnf) {
                    result += "Realized class for Structured Data type "
                            + typeName + " not found." + CR;
                }
                if (realizedSDT != null) {
                    StructureMember_c[] members = StructureMember_c
                            .getManyS_MBRsOnR44(sdt);
                    for (StructureMember_c member : members) {
                        String memberName = dataType.getName() + "::"
                                + member.getName();
                        Method getAccessor = null;
                        try {
                            getAccessor = realizedSDT.getDeclaredMethod("get"
                                    + member.getName(), new Class<?>[0]);
                        } catch (SecurityException e) {
                            result += "Security exception getting "
                                    + "realized member read accessor: "
                                    + memberName + "." + CR;
                        } catch (NoSuchMethodException e) {
                            // Expected outcome, do nothing
                        }
                        Method setAccessor = null;
                        try {
                            DataType_c memberDt = DataType_c
                                    .getOneS_DTOnR45(member);
                            Class<?> coreType = getClassForCoreTypeOf(memberDt,
                                    false);
                            setAccessor = realizedSDT.getDeclaredMethod("set"
                                    + member.getName(),
                                    new Class<?>[] { coreType });
                        } catch (SecurityException e) {
                            result += "Security exception getting "
                                    + "realized member write accessor: "
                                    + memberName + "." + CR;
                        } catch (NoSuchMethodException e) {
                            // Expected outcome, do nothing
                        }
                        if (getAccessor != null) {
                            if ((getAccessor.getModifiers() & Modifier.PUBLIC) == 0) {
                                result += "Read accessor for " + memberName
                                        + " must be public." + CR;
                            }
                            if ((getAccessor.getModifiers() & Modifier.STATIC) == 1) {
                                result += "Read accessor for " + memberName
                                        + " must be non-static." + CR;
                            }
                        }
                        if (setAccessor != null) {
                            if ((setAccessor.getModifiers() & Modifier.PUBLIC) == 0) {
                                result += "Write accessor for " + memberName
                                        + " must be public." + CR;
                            }
                            if ((setAccessor.getModifiers() & Modifier.STATIC) == 1) {
                                result += "Write accessor for " + memberName
                                        + " must be non-static." + CR;
                            }
                        }
                        if (getAccessor == null || setAccessor == null) {
                            Field field = null;
                            try {
                                field = realizedSDT.getDeclaredField(member
                                        .getName());
                            } catch (SecurityException e) {
                                result += "Security exception getting realized member: "
                                        + memberName + "." + CR;
                                result += e.getLocalizedMessage() + CR;
                            } catch (NoSuchFieldException e) {
                                result += "No matching member found for: "
                                        + memberName + "." + CR;
                            }
                            if (field != null) {
                                if ((field.getModifiers() & Modifier.PUBLIC) == 0) {
                                    if (getAccessor == null
                                            && setAccessor == null) {
                                        result += "Member "
                                                + memberName
                                                + " must be public or preferably, "
                                                + "get and set accessors provided."
                                                + CR;
                                    } else if (getAccessor != null) {
                                        result += "Member " + memberName
                                                + " is missing method " + "set"
                                                + member.getName() + CR;
                                    } else if (setAccessor != null) {
                                        result += "Member " + memberName
                                                + " is missing method " + "get"
                                                + member.getName() + CR;
                                    }
                                } else {
                                    result += "Member "
                                            + memberName
                                            + " is public. Recommend "
                                            + "providing read and write accessors."
                                            + CR;
                                }
                                if ((field.getModifiers() & Modifier.STATIC) == 1) {
                                    result += "Member " + memberName
                                            + " must be non-static." + CR;
                                }
                            }
                        }
                    } // end for each member
                } // end if realized SDT found
            } else {
                result += "Structured Data Type " + dataType.getName()
                        + " must be declared under a generic package." + CR;
            }
            result += "Data type check on " + dataType.getName() + " complete."
                    + CR + CR;
        } else if (udt != null) {
            result += auditUserDefinedType(dataType);
        } else if (edt != null) {
            result += auditEnumeratedDataType(dataType);
        }
        return result;
    }

    private static String auditUserDefinedType(DataType_c dataType) {
        String result = "Checking data type " + dataType.getName() + CR;
        Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
                .getOnePE_PEOnR8001(dataType));
        if (pkg != null) {
            String typeName = VerifierInvocationHandler.pathToClassName(pkg
                    .Getpath("")
                    + "::" + dataType.getName());
            Class<?> realizedUDT = null;
            BPClassLoader bpcl = Vm_c.getVmCl(pkg.Getsystemid());
            try {
                realizedUDT = bpcl.loadClass(typeName);
            } catch (ClassNotFoundException cnf) {
                result += "User Data type "
                        + typeName
                        + " not found, mapping will be performed to default core type."
                        + CR;
            }
            if (realizedUDT != null) {
                Method getAccessor = null;
                Method setAccessor = null;
                try {
                    getAccessor = realizedUDT.getDeclaredMethod("getValue",
                            new Class<?>[0]);
                } catch (SecurityException e) {
                    result += "Security exception getting "
                            + "realized user data type read accessor for: "
                            + dataType.getName() + "." + CR;
                } catch (NoSuchMethodException e) {
                    // Expected outcome, do nothing
                }
                try {
                    Class<?> coreType = getClassForCoreTypeOf(dataType, false);
                    setAccessor = realizedUDT.getDeclaredMethod("setValue",
                            new Class<?>[] { coreType });
                } catch (SecurityException e) {
                    result += "Security exception getting "
                            + "realized user data type write accessor for: "
                            + dataType.getName() + "." + CR;
                } catch (NoSuchMethodException e) {
                    // Expected outcome, do nothing
                }
                if (getAccessor != null) {
                    if ((getAccessor.getModifiers() & Modifier.PUBLIC) == 0) {
                        result += "Read accessor for " + dataType.getName()
                                + " must be public." + CR;
                    }
                    if ((getAccessor.getModifiers() & Modifier.STATIC) == 1) {
                        result += "Read accessor for " + dataType.getName()
                                + " must be non-static." + CR;
                    }
                }
                if (setAccessor != null) {
                    if ((setAccessor.getModifiers() & Modifier.PUBLIC) == 0) {
                        result += "Write accessor for " + dataType.getName()
                                + " must be public." + CR;
                    }
                    if ((setAccessor.getModifiers() & Modifier.STATIC) == 1) {
                        result += "Write accessor for " + dataType.getName()
                                + " must be non-static." + CR;
                    }
                }
                if (getAccessor == null || setAccessor == null) {
                    Field field = null;
                    try {
                        field = realizedUDT.getDeclaredField("value");
                    } catch (SecurityException e) {
                        result += "Security exception getting realized field: "
                                + "value." + CR;
                        result += e.getLocalizedMessage() + CR;
                    } catch (NoSuchFieldException e) {
                        result += "No accessors or value field found for: "
                                + dataType.getName() + "." + CR;
                    }
                    if (field != null) {
                        if ((field.getModifiers() & Modifier.PUBLIC) == 0) {
                            if (getAccessor == null && setAccessor == null) {
                                result += "Field 'value' "
                                        + " must be public or preferably, "
                                        + "get and set accessors provided."
                                        + CR;
                            } else if (getAccessor != null) {
                                result += "Missing write accessor for: "
                                        + dataType.getName() + "." + CR;
                            } else if (setAccessor != null) {
                                result += "Missing write accessor for: "
                                        + dataType.getName() + "." + CR;
                            }
                        } else {
                            result += "Value field for " + dataType.getName()
                                    + " is public. Recommend "
                                    + "providing read and write accessors."
                                    + CR;
                        }
                        if ((field.getModifiers() & Modifier.STATIC) == 1) {
                            result += "Value field for " + dataType.getName()
                                    + " must be non-static." + CR;
                        }
                    }
                }
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static String auditEnumeratedDataType(DataType_c dataType) {
        String result = "Checking data type " + dataType.getName() + CR;
        Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
                .getOnePE_PEOnR8001(dataType));
        if (pkg != null) {
            String className = VerifierInvocationHandler.pathToClassName(pkg
                    .Getpath("")
                    + "::" + dataType.getName());
            SystemModel_c sys = SystemModel_c.getOneS_SYSOnR1405(pkg);
            if (sys != null) {
                BPClassLoader cl = Vm_c.getVmCl(sys.getSys_id());
                if (cl != null) {
                    try {
                        Class<?> realizedEDT = cl.loadClass(className);
                        if (realizedEDT.isEnum()) {
                            EnumerationDataType_c edt = EnumerationDataType_c
                                    .getOneS_EDTOnR17(dataType);
                            Enumerator_c[] enmrs = Enumerator_c
                                    .getManyS_ENUMsOnR27(edt);
                            for (Enumerator_c enmr : enmrs) {
                                try {
                                    Enum.valueOf((Class<Enum>) realizedEDT,
                                            enmr.getName());
                                } catch (IllegalArgumentException iae) {
                                    result += "Enumerated type " + className
                                            + " does not define "
                                            + enmr.getName() + "." + CR;
                                }
                            }
                            for (Enum realizedEnmr: ((Class<Enum>)realizedEDT).getEnumConstants()) {
                                String name = realizedEnmr.name();
                                boolean matchFound = false;
                                for (Enumerator_c enmr : enmrs) {
                                    if (name.equals(enmr.getName())) {
                                        matchFound = true;
                                        break;
                                    }
                                }
                                if (matchFound == false) {
                                    result += "Realized enumeration " + name +
                                        " does not have a modeled mapping." + CR;
                                }
                            }
                        } else {
                            result += "Enumerated type " + className
                                    + " is not a java enumeraion." + CR;
                        }
                    } catch (ClassNotFoundException e) {
                        result += "Enumerated type " + className + " not found."
                                + CR;
                    }
                } else {
                    result += "Internal error: Verifier class loader not found."
                    + CR;
                }
            } else {
                result += "Internal error: Model system not found."
                    + CR;
            }
        } else {
            result += "Data type is not in a generic package."
                + CR;
        }
        return result;
    }

    private static Class<?> getClassForCoreTypeOf(DataType_c dt, boolean byRef) {
        DataType_c coreDt = VerifierInvocationHandler.getCoreTypeForDt(dt);
        CoreDataType_c cdt = CoreDataType_c.getOneS_CDTOnR17(coreDt);
        StructuredDataType_c sdt = StructuredDataType_c
                .getOneS_SDTOnR17(coreDt);
        EnumerationDataType_c edt = EnumerationDataType_c
                .getOneS_EDTOnR17(coreDt);
        UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(dt);
        if (cdt != null) {
            DataType_c superType = DataType_c.getOneS_DTOnR17(cdt);
            if (superType.getName().equals("integer")) {
                return byRef ? BPInteger.class : int.class;
            } else if (superType.getName().equals("real")) {
                return byRef ? BPFloat.class : float.class;
            } else if (superType.getName().equals("string")) {
                return byRef ? BPString.class : java.lang.String.class;
            } else if (superType.getName().equals("boolean")) {
                return byRef ? BPBoolean.class : boolean.class;
            } else if (superType.getName().equals("unique_id")) {
                return byRef ? BPUniqueId.class : java.util.UUID.class;
            } else if (superType.getName().equals("component_ref")) {
                return java.lang.Object.class;
            } else if (superType.getName().equals("state<State_Model>")) {
                return java.lang.Object.class;
            } else if (superType.getName().equals("inst_ref<Object>")) {
                return java.lang.Object.class;
            } else if (superType.getName().equals("inst_ref_set<Object>")) {
                return java.lang.Object.class;
            } else if (superType.getName().equals("inst<Event>")) {
                return java.lang.Object.class;
            } else if (superType.getName().equals("inst<Event>")) {
                return java.lang.Object.class;
            } else if (superType.getName().equals("date")) {
                return java.lang.Object.class;
            } else if (superType.getName().equals("inst_ref<Timer>")) {
                return java.lang.Object.class;
            } else if (superType.getName().equals("timestamp")) {
                return java.lang.Object.class;
            } else if (superType.getName().equals("void")) {
                return void.class;
            }
        } else if (sdt != null || edt != null) {
            Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
                    .getOnePE_PEOnR8001(coreDt));
            if (pkg != null) {
                String typeName = VerifierInvocationHandler.pathToClassName(pkg
                        .Getpath("")
                        + "::" + coreDt.getName());
                Class<?> realizedDT = null;
                BPClassLoader bpcl = Vm_c.getVmCl(pkg.Getsystemid());
                try {
                    realizedDT = bpcl.loadClass(typeName);
                } catch (ClassNotFoundException cnf) {
                    // Do nothing, this will be reported elsewhere
                }
                if (realizedDT != null) {
                    return realizedDT;
                }
            }
        } else if (udt != null) {
            DataType_c definition = DataType_c.getOneS_DTOnR18(udt);    
            if (definition != null) {
                return getClassForCoreTypeOf(definition, byRef);
            }
            
        }
        return null;
    }

    private static String getNameForCoreTpeOf(DataType_c dt, boolean byRef) {
        DataType_c coreDt = VerifierInvocationHandler.getCoreTypeForDt(dt);
        CoreDataType_c cdt = CoreDataType_c.getOneS_CDTOnR17(coreDt);
        if (cdt != null) {
            DataType_c superType = DataType_c.getOneS_DTOnR17(cdt);
            if (superType.getName().equals("integer")) {
                return byRef ? "BPInteger" : "int";
            } else if (superType.getName().equals("real")) {
                return byRef ? "BPFloat" : "float";
            } else if (superType.getName().equals("string")) {
                return byRef ? "BPString" : "String";
            } else if (superType.getName().equals("boolean")) {
                return byRef ? "BPBoolean" : "boolean";
            } else if (superType.getName().equals("unique_id")) {
                return byRef ? "BPUniqueId" : "java.util.UUID";
            } else if (superType.getName().equals("component_ref")) {
                return "Object";
            } else if (superType.getName().equals("void")) {
                return "void";
            }
            else {
                return "Object";
            }
        }
        return null;
    }

    private static String getClassNameForDt(DataType_c dt) {
        StructuredDataType_c sdt = StructuredDataType_c.getOneS_SDTOnR17(dt);
        UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(dt);
        EnumerationDataType_c edt = EnumerationDataType_c.getOneS_EDTOnR17(dt);
        if (sdt != null || edt != null) {
            Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
                    .getOnePE_PEOnR8001(dt));
            if (pkg != null) {
                String typeName = VerifierInvocationHandler.pathToClassName(pkg
                        .Getpath("")
                        + "::" + dt.getName());
                return typeName;
            }
        }
        else if (udt != null) {
            DataType_c definition = DataType_c.getOneS_DTOnR18(udt);    
            if (definition != null) {
                return getClassNameForDt(definition);
            }
        }
        return null;
    }
}
