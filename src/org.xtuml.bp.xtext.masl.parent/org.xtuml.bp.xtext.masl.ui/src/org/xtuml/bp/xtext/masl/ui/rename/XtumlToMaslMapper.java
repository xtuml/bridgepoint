package org.xtuml.bp.xtext.masl.ui.rename;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.ClassAsSimpleFormalizer_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.LinkedAssociation_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.NewStateTransition_c;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.SimpleAssociation_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage;
import org.xtuml.bp.xtext.masl.masl.types.TypesPackage;

@SuppressWarnings("all")
public class XtumlToMaslMapper {
  @Inject
  @Extension
  private StructurePackage _structurePackage;
  
  @Inject
  @Extension
  private TypesPackage _typesPackage;
  
  public List<EClass> getMaslEClasses(final NonRootModelElement xtumlElement) {
    List<EClass> _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (xtumlElement instanceof Attribute_c) {
        _matched=true;
        EClass _attributeDefinition = this._structurePackage.getAttributeDefinition();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_attributeDefinition));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Component_c) {
        _matched=true;
        EClass _domainDefinition = this._structurePackage.getDomainDefinition();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_domainDefinition));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Function_c) {
        _matched=true;
        EClass _domainServiceDeclaration = this._structurePackage.getDomainServiceDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_domainServiceDeclaration));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof FunctionParameter_c) {
        _matched=true;
        EClass _parameter = this._structurePackage.getParameter();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_parameter));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof RequiredOperation_c) {
        _matched=true;
        EClass _terminatorServiceDeclaration = this._structurePackage.getTerminatorServiceDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_terminatorServiceDeclaration));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ProvidedOperation_c) {
        _matched=true;
        EClass _terminatorServiceDeclaration = this._structurePackage.getTerminatorServiceDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_terminatorServiceDeclaration));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof InterfaceOperation_c) {
        _matched=true;
        EClass _terminatorServiceDeclaration = this._structurePackage.getTerminatorServiceDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_terminatorServiceDeclaration));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof PropertyParameter_c) {
        _matched=true;
        EClass _parameter = this._structurePackage.getParameter();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_parameter));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ModelClass_c) {
        _matched=true;
        EClass _objectDeclaration = this._structurePackage.getObjectDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_objectDeclaration));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Operation_c) {
        _matched=true;
        EClass _objectServiceDeclaration = this._structurePackage.getObjectServiceDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_objectServiceDeclaration));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof OperationParameter_c) {
        _matched=true;
        EClass _parameter = this._structurePackage.getParameter();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_parameter));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Port_c) {
        _matched=true;
        EClass _terminatorDefinition = this._structurePackage.getTerminatorDefinition();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_terminatorDefinition));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof StateMachineState_c) {
        _matched=true;
        EClass _stateDeclaration = this._structurePackage.getStateDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_stateDeclaration));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof StateMachineEvent_c) {
        _matched=true;
        EClass _eventDefinition = this._structurePackage.getEventDefinition();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_eventDefinition));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof StateMachineEventDataItem_c) {
        _matched=true;
        EClass _parameter = this._structurePackage.getParameter();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_parameter));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof DataType_c) {
        _matched=true;
        EClass _typeDeclaration = this._typesPackage.getTypeDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_typeDeclaration));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof UserDataType_c) {
        _matched=true;
        EClass _typeDeclaration = this._typesPackage.getTypeDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_typeDeclaration));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ClassAsSimpleParticipant_c) {
        _matched=true;
        EClass _relationshipEnd = this._structurePackage.getRelationshipEnd();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_relationshipEnd));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ClassAsSimpleFormalizer_c) {
        _matched=true;
        EClass _relationshipEnd = this._structurePackage.getRelationshipEnd();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_relationshipEnd));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ClassAsAssociatedOneSide_c) {
        _matched=true;
        EClass _relationshipEnd = this._structurePackage.getRelationshipEnd();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_relationshipEnd));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ClassAsAssociatedOtherSide_c) {
        _matched=true;
        EClass _relationshipEnd = this._structurePackage.getRelationshipEnd();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_relationshipEnd));
      }
    }
    if (!_matched) {
      _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList());
    }
    return _switchResult;
  }
  
  public List<QualifiedName> getMaslQualifiedNames(final NonRootModelElement xtumlElement, final String name) {
    List<QualifiedName> _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (xtumlElement instanceof InterfaceOperation_c) {
        _matched=true;
        List<QualifiedName> _xblockexpression = null;
        {
          final ArrayList<QualifiedName> ops = CollectionLiterals.<QualifiedName>newArrayList();
          ExecutableProperty_c _oneC_EPOnR4004 = ExecutableProperty_c.getOneC_EPOnR4004(((InterfaceOperation_c)xtumlElement));
          RequiredExecutableProperty_c[] _manySPR_REPsOnR4500 = RequiredExecutableProperty_c.getManySPR_REPsOnR4500(_oneC_EPOnR4004);
          RequiredOperation_c[] _manySPR_ROsOnR4502 = RequiredOperation_c.getManySPR_ROsOnR4502(_manySPR_REPsOnR4500);
          final Function1<RequiredOperation_c, QualifiedName> _function = (RequiredOperation_c spr_ro) -> {
            return this.getMaslQualifiedName(spr_ro, name);
          };
          List<QualifiedName> _map = ListExtensions.<RequiredOperation_c, QualifiedName>map(((List<RequiredOperation_c>)Conversions.doWrapArray(_manySPR_ROsOnR4502)), _function);
          Iterables.<QualifiedName>addAll(ops, _map);
          ExecutableProperty_c _oneC_EPOnR4004_1 = ExecutableProperty_c.getOneC_EPOnR4004(((InterfaceOperation_c)xtumlElement));
          ProvidedExecutableProperty_c[] _manySPR_PEPsOnR4501 = ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(_oneC_EPOnR4004_1);
          ProvidedOperation_c[] _manySPR_POsOnR4503 = ProvidedOperation_c.getManySPR_POsOnR4503(_manySPR_PEPsOnR4501);
          final Function1<ProvidedOperation_c, QualifiedName> _function_1 = (ProvidedOperation_c spr_po) -> {
            return this.getMaslQualifiedName(spr_po, name);
          };
          List<QualifiedName> _map_1 = ListExtensions.<ProvidedOperation_c, QualifiedName>map(((List<ProvidedOperation_c>)Conversions.doWrapArray(_manySPR_POsOnR4503)), _function_1);
          Iterables.<QualifiedName>addAll(ops, _map_1);
          final Function1<QualifiedName, Boolean> _function_2 = (QualifiedName it) -> {
            return Boolean.valueOf(this.nonNull(it));
          };
          Iterable<QualifiedName> _filter = IterableExtensions.<QualifiedName>filter(ops, _function_2);
          _xblockexpression = IterableExtensions.<QualifiedName>toList(_filter);
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof PropertyParameter_c) {
        _matched=true;
        List<QualifiedName> _xblockexpression = null;
        {
          final ArrayList<QualifiedName> params = CollectionLiterals.<QualifiedName>newArrayList();
          ExecutableProperty_c _oneC_EPOnR4006 = ExecutableProperty_c.getOneC_EPOnR4006(((PropertyParameter_c)xtumlElement));
          RequiredExecutableProperty_c[] _manySPR_REPsOnR4500 = RequiredExecutableProperty_c.getManySPR_REPsOnR4500(_oneC_EPOnR4006);
          RequiredOperation_c[] _manySPR_ROsOnR4502 = RequiredOperation_c.getManySPR_ROsOnR4502(_manySPR_REPsOnR4500);
          final Function1<RequiredOperation_c, QualifiedName> _function = (RequiredOperation_c spr_ro) -> {
            QualifiedName _maslQualifiedName = this.getMaslQualifiedName(spr_ro);
            QualifiedName _append = null;
            if (_maslQualifiedName!=null) {
              _append=_maslQualifiedName.append(name);
            }
            return _append;
          };
          List<QualifiedName> _map = ListExtensions.<RequiredOperation_c, QualifiedName>map(((List<RequiredOperation_c>)Conversions.doWrapArray(_manySPR_ROsOnR4502)), _function);
          Iterables.<QualifiedName>addAll(params, _map);
          ExecutableProperty_c _oneC_EPOnR4006_1 = ExecutableProperty_c.getOneC_EPOnR4006(((PropertyParameter_c)xtumlElement));
          ProvidedExecutableProperty_c[] _manySPR_PEPsOnR4501 = ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(_oneC_EPOnR4006_1);
          ProvidedOperation_c[] _manySPR_POsOnR4503 = ProvidedOperation_c.getManySPR_POsOnR4503(_manySPR_PEPsOnR4501);
          final Function1<ProvidedOperation_c, QualifiedName> _function_1 = (ProvidedOperation_c spr_po) -> {
            QualifiedName _maslQualifiedName = this.getMaslQualifiedName(spr_po);
            QualifiedName _append = null;
            if (_maslQualifiedName!=null) {
              _append=_maslQualifiedName.append(name);
            }
            return _append;
          };
          List<QualifiedName> _map_1 = ListExtensions.<ProvidedOperation_c, QualifiedName>map(((List<ProvidedOperation_c>)Conversions.doWrapArray(_manySPR_POsOnR4503)), _function_1);
          Iterables.<QualifiedName>addAll(params, _map_1);
          final Function1<QualifiedName, Boolean> _function_2 = (QualifiedName it) -> {
            return Boolean.valueOf(this.nonNull(it));
          };
          Iterable<QualifiedName> _filter = IterableExtensions.<QualifiedName>filter(params, _function_2);
          _xblockexpression = IterableExtensions.<QualifiedName>toList(_filter);
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof StateMachineEventDataItem_c) {
        _matched=true;
        List<QualifiedName> _xblockexpression = null;
        {
          final ArrayList<QualifiedName> params = CollectionLiterals.<QualifiedName>newArrayList();
          StateMachineEvent_c _oneSM_EVTOnR532 = StateMachineEvent_c.getOneSM_EVTOnR532(((StateMachineEventDataItem_c)xtumlElement));
          QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_oneSM_EVTOnR532);
          QualifiedName _append = null;
          if (_maslQualifiedName!=null) {
            _append=_maslQualifiedName.append(name);
          }
          params.add(_append);
          StateMachineEvent_c _oneSM_EVTOnR532_1 = StateMachineEvent_c.getOneSM_EVTOnR532(((StateMachineEventDataItem_c)xtumlElement));
          SemEvent_c _oneSM_SEVTOnR525 = SemEvent_c.getOneSM_SEVTOnR525(_oneSM_EVTOnR532_1);
          StateEventMatrixEntry_c[] _manySM_SEMEsOnR503 = StateEventMatrixEntry_c.getManySM_SEMEsOnR503(_oneSM_SEVTOnR525);
          NewStateTransition_c[] _manySM_NSTXNsOnR504 = NewStateTransition_c.getManySM_NSTXNsOnR504(_manySM_SEMEsOnR503);
          Transition_c[] _manySM_TXNsOnR507 = Transition_c.getManySM_TXNsOnR507(_manySM_NSTXNsOnR504);
          StateMachineState_c[] _manySM_STATEsOnR506 = StateMachineState_c.getManySM_STATEsOnR506(_manySM_TXNsOnR507);
          final Function1<StateMachineState_c, QualifiedName> _function = (StateMachineState_c sm_state) -> {
            QualifiedName _maslQualifiedName_1 = this.getMaslQualifiedName(sm_state);
            QualifiedName _append_1 = null;
            if (_maslQualifiedName_1!=null) {
              _append_1=_maslQualifiedName_1.append(name);
            }
            return _append_1;
          };
          List<QualifiedName> _map = ListExtensions.<StateMachineState_c, QualifiedName>map(((List<StateMachineState_c>)Conversions.doWrapArray(_manySM_STATEsOnR506)), _function);
          Iterables.<QualifiedName>addAll(params, _map);
          final Function1<QualifiedName, Boolean> _function_1 = (QualifiedName it) -> {
            return Boolean.valueOf(this.nonNull(it));
          };
          Iterable<QualifiedName> _filter = IterableExtensions.<QualifiedName>filter(params, _function_1);
          _xblockexpression = IterableExtensions.<QualifiedName>toList(_filter);
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      QualifiedName _maslQualifiedName = this.getMaslQualifiedName(xtumlElement, name);
      final Function1<QualifiedName, Boolean> _function = (QualifiedName it) -> {
        return Boolean.valueOf(this.nonNull(it));
      };
      Iterable<QualifiedName> _filter = IterableExtensions.<QualifiedName>filter(Collections.<QualifiedName>unmodifiableList(CollectionLiterals.<QualifiedName>newArrayList(_maslQualifiedName)), _function);
      _switchResult = IterableExtensions.<QualifiedName>toList(_filter);
    }
    return _switchResult;
  }
  
  public QualifiedName getMaslQualifiedName(final NonRootModelElement xtumlElement) {
    QualifiedName _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (xtumlElement instanceof Component_c) {
        _matched=true;
        String _name = ((Component_c)xtumlElement).getName();
        _switchResult = QualifiedName.create(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof StateMachineEvent_c) {
        _matched=true;
        QualifiedName _maslQualifiedNamePrefix = this.getMaslQualifiedNamePrefix(xtumlElement);
        QualifiedName _append = null;
        if (_maslQualifiedNamePrefix!=null) {
          String _mning = ((StateMachineEvent_c)xtumlElement).getMning();
          _append=_maslQualifiedNamePrefix.append(_mning);
        }
        _switchResult = _append;
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Association_c) {
        _matched=true;
        QualifiedName _maslQualifiedNamePrefix = this.getMaslQualifiedNamePrefix(xtumlElement);
        QualifiedName _append = null;
        if (_maslQualifiedNamePrefix!=null) {
          int _numb = ((Association_c)xtumlElement).getNumb();
          String _plus = ("R" + Integer.valueOf(_numb));
          _append=_maslQualifiedNamePrefix.append(_plus);
        }
        _switchResult = _append;
      }
    }
    if (!_matched) {
      QualifiedName _maslQualifiedNamePrefix = this.getMaslQualifiedNamePrefix(xtumlElement);
      QualifiedName _append = null;
      if (_maslQualifiedNamePrefix!=null) {
        String _name = xtumlElement.getName();
        _append=_maslQualifiedNamePrefix.append(_name);
      }
      _switchResult = _append;
    }
    return _switchResult;
  }
  
  public QualifiedName getMaslQualifiedName(final NonRootModelElement xtumlElement, final String name) {
    QualifiedName _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (xtumlElement instanceof Component_c) {
        _matched=true;
        _switchResult = QualifiedName.create(name);
      }
    }
    if (!_matched) {
      QualifiedName _maslQualifiedNamePrefix = this.getMaslQualifiedNamePrefix(xtumlElement);
      QualifiedName _append = null;
      if (_maslQualifiedNamePrefix!=null) {
        _append=_maslQualifiedNamePrefix.append(name);
      }
      _switchResult = _append;
    }
    return _switchResult;
  }
  
  private QualifiedName getMaslQualifiedNamePrefix(final NonRootModelElement xtumlElement) {
    QualifiedName _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (xtumlElement instanceof Attribute_c) {
        _matched=true;
        ModelClass_c _oneO_OBJOnR102 = ModelClass_c.getOneO_OBJOnR102(((Attribute_c)xtumlElement));
        _switchResult = this.getMaslQualifiedName(_oneO_OBJOnR102);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Function_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        _switchResult = this.getMaslQualifiedName(_component);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof FunctionParameter_c) {
        _matched=true;
        Function_c _oneS_SYNCOnR24 = Function_c.getOneS_SYNCOnR24(((FunctionParameter_c)xtumlElement));
        _switchResult = this.getMaslQualifiedName(_oneS_SYNCOnR24);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof RequiredOperation_c) {
        _matched=true;
        RequiredExecutableProperty_c _oneSPR_REPOnR4502 = RequiredExecutableProperty_c.getOneSPR_REPOnR4502(((RequiredOperation_c)xtumlElement));
        Requirement_c _oneC_ROnR4500 = Requirement_c.getOneC_ROnR4500(_oneSPR_REPOnR4502);
        InterfaceReference_c _oneC_IROnR4009 = InterfaceReference_c.getOneC_IROnR4009(_oneC_ROnR4500);
        Port_c _oneC_POOnR4016 = Port_c.getOneC_POOnR4016(_oneC_IROnR4009);
        _switchResult = this.getMaslQualifiedName(_oneC_POOnR4016);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ProvidedOperation_c) {
        _matched=true;
        ProvidedExecutableProperty_c _oneSPR_PEPOnR4503 = ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(((ProvidedOperation_c)xtumlElement));
        Provision_c _oneC_POnR4501 = Provision_c.getOneC_POnR4501(_oneSPR_PEPOnR4503);
        InterfaceReference_c _oneC_IROnR4009 = InterfaceReference_c.getOneC_IROnR4009(_oneC_POnR4501);
        Port_c _oneC_POOnR4016 = Port_c.getOneC_POOnR4016(_oneC_IROnR4009);
        _switchResult = this.getMaslQualifiedName(_oneC_POOnR4016);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ModelClass_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        _switchResult = this.getMaslQualifiedName(_component);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Operation_c) {
        _matched=true;
        ModelClass_c _oneO_OBJOnR115 = ModelClass_c.getOneO_OBJOnR115(((Operation_c)xtumlElement));
        _switchResult = this.getMaslQualifiedName(_oneO_OBJOnR115);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof OperationParameter_c) {
        _matched=true;
        Operation_c _oneO_TFROnR117 = Operation_c.getOneO_TFROnR117(((OperationParameter_c)xtumlElement));
        _switchResult = this.getMaslQualifiedName(_oneO_TFROnR117);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Port_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        _switchResult = this.getMaslQualifiedName(_component);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof StateMachineState_c) {
        _matched=true;
        StateMachine_c _oneSM_SMOnR501 = StateMachine_c.getOneSM_SMOnR501(((StateMachineState_c)xtumlElement));
        InstanceStateMachine_c _oneSM_ISMOnR517 = InstanceStateMachine_c.getOneSM_ISMOnR517(_oneSM_SMOnR501);
        ModelClass_c _oneO_OBJOnR518 = ModelClass_c.getOneO_OBJOnR518(_oneSM_ISMOnR517);
        _switchResult = this.getMaslQualifiedName(_oneO_OBJOnR518);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof StateMachineEvent_c) {
        _matched=true;
        StateMachine_c _oneSM_SMOnR502 = StateMachine_c.getOneSM_SMOnR502(((StateMachineEvent_c)xtumlElement));
        InstanceStateMachine_c _oneSM_ISMOnR517 = InstanceStateMachine_c.getOneSM_ISMOnR517(_oneSM_SMOnR502);
        ModelClass_c _oneO_OBJOnR518 = ModelClass_c.getOneO_OBJOnR518(_oneSM_ISMOnR517);
        _switchResult = this.getMaslQualifiedName(_oneO_OBJOnR518);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof DataType_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        _switchResult = this.getMaslQualifiedName(_component);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof UserDataType_c) {
        _matched=true;
        DataType_c _oneS_DTOnR17 = DataType_c.getOneS_DTOnR17(((UserDataType_c)xtumlElement));
        _switchResult = this.getMaslQualifiedNamePrefix(_oneS_DTOnR17);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Association_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        _switchResult = this.getMaslQualifiedName(_component);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ClassAsSimpleParticipant_c) {
        _matched=true;
        SimpleAssociation_c _oneR_SIMPOnR207 = SimpleAssociation_c.getOneR_SIMPOnR207(((ClassAsSimpleParticipant_c)xtumlElement));
        Association_c _oneR_RELOnR206 = Association_c.getOneR_RELOnR206(_oneR_SIMPOnR207);
        _switchResult = this.getMaslQualifiedName(_oneR_RELOnR206);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ClassAsSimpleFormalizer_c) {
        _matched=true;
        SimpleAssociation_c _oneR_SIMPOnR208 = SimpleAssociation_c.getOneR_SIMPOnR208(((ClassAsSimpleFormalizer_c)xtumlElement));
        Association_c _oneR_RELOnR206 = Association_c.getOneR_RELOnR206(_oneR_SIMPOnR208);
        _switchResult = this.getMaslQualifiedName(_oneR_RELOnR206);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ClassAsAssociatedOneSide_c) {
        _matched=true;
        LinkedAssociation_c _oneR_ASSOCOnR209 = LinkedAssociation_c.getOneR_ASSOCOnR209(((ClassAsAssociatedOneSide_c)xtumlElement));
        Association_c _oneR_RELOnR206 = Association_c.getOneR_RELOnR206(_oneR_ASSOCOnR209);
        _switchResult = this.getMaslQualifiedName(_oneR_RELOnR206);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ClassAsAssociatedOtherSide_c) {
        _matched=true;
        LinkedAssociation_c _oneR_ASSOCOnR210 = LinkedAssociation_c.getOneR_ASSOCOnR210(((ClassAsAssociatedOtherSide_c)xtumlElement));
        Association_c _oneR_RELOnR206 = Association_c.getOneR_RELOnR206(_oneR_ASSOCOnR210);
        _switchResult = this.getMaslQualifiedName(_oneR_RELOnR206);
      }
    }
    if (!_matched) {
      _switchResult = null;
    }
    return _switchResult;
  }
  
  private NonRootModelElement getComponent(final NonRootModelElement xtumlElement) {
    boolean _equals = Objects.equal(null, xtumlElement);
    if (_equals) {
      return null;
    }
    PersistableModelComponent parent = xtumlElement.getPersistableComponent();
    while ((!Objects.equal(parent, null))) {
      NonRootModelElement _rootModelElement = parent.getRootModelElement();
      if ((_rootModelElement instanceof Component_c)) {
        return parent.getRootModelElement();
      } else {
        PersistableModelComponent _parent = parent.getParent();
        parent = _parent;
      }
    }
    if ((xtumlElement instanceof DataType_c)) {
      PackageableElement_c _onePE_PEOnR8001 = PackageableElement_c.getOnePE_PEOnR8001(((DataType_c)xtumlElement));
      Package_c _oneEP_PKGOnR8000 = Package_c.getOneEP_PKGOnR8000(_onePE_PEOnR8001);
      PackageableElement_c _onePE_PEOnR8001_1 = PackageableElement_c.getOnePE_PEOnR8001(_oneEP_PKGOnR8000);
      Package_c _oneEP_PKGOnR8000_1 = Package_c.getOneEP_PKGOnR8000(_onePE_PEOnR8001_1);
      PackageableElement_c[] _manyPE_PEsOnR8000 = PackageableElement_c.getManyPE_PEsOnR8000(_oneEP_PKGOnR8000_1);
      final Component_c[] components = Component_c.getManyC_CsOnR8001(_manyPE_PEsOnR8000);
      int _length = components.length;
      boolean _equals_1 = (_length == 1);
      if (_equals_1) {
        return components[0];
      }
    }
    return null;
  }
  
  private boolean nonNull(final Object obj) {
    boolean _notEquals = (!Objects.equal(obj, null));
    if (_notEquals) {
      return true;
    }
    return false;
  }
}
