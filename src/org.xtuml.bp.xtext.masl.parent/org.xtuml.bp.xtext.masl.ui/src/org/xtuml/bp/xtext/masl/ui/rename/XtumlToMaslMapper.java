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
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.ClassAsSimpleFormalizer_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.LinkedAssociation_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.SimpleAssociation_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
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
  
  public List<EClass> getMaslDefinitionEClasses(final NonRootModelElement xtumlElement) {
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
        EClass _domainServiceDefinition = this._structurePackage.getDomainServiceDefinition();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_domainServiceDefinition));
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
        EClass _terminatorServiceDefinition = this._structurePackage.getTerminatorServiceDefinition();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_terminatorServiceDefinition));
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ProvidedOperation_c) {
        _matched=true;
        EClass _terminatorServiceDefinition = this._structurePackage.getTerminatorServiceDefinition();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_terminatorServiceDefinition));
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
        EClass _objectServiceDefinition = this._structurePackage.getObjectServiceDefinition();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_objectServiceDefinition));
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
      if (xtumlElement instanceof UserDataType_c) {
        _matched=true;
        EClass _typeDeclaration = this._typesPackage.getTypeDeclaration();
        _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList(_typeDeclaration));
      }
    }
    if (!_matched) {
      _switchResult = Collections.<EClass>unmodifiableList(CollectionLiterals.<EClass>newArrayList());
    }
    return _switchResult;
  }
  
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
        String _mning = ((StateMachineEvent_c)xtumlElement).getMning();
        _switchResult = _maslQualifiedNamePrefix.append(_mning);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Association_c) {
        _matched=true;
        QualifiedName _maslQualifiedNamePrefix = this.getMaslQualifiedNamePrefix(xtumlElement);
        int _numb = ((Association_c)xtumlElement).getNumb();
        String _plus = ("R" + Integer.valueOf(_numb));
        _switchResult = _maslQualifiedNamePrefix.append(_plus);
      }
    }
    if (!_matched) {
      QualifiedName _maslQualifiedNamePrefix = this.getMaslQualifiedNamePrefix(xtumlElement);
      String _name = xtumlElement.getName();
      _switchResult = _maslQualifiedNamePrefix.append(_name);
    }
    return _switchResult;
  }
  
  public List<QualifiedName> getMaslQualifiedNames(final NonRootModelElement xtumlElement, final String name) {
    List<QualifiedName> _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (xtumlElement instanceof InterfaceOperation_c) {
        _matched=true;
        ArrayList<QualifiedName> _xblockexpression = null;
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
          _xblockexpression = ops;
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof PropertyParameter_c) {
        _matched=true;
        ArrayList<QualifiedName> _xblockexpression = null;
        {
          final ArrayList<QualifiedName> params = CollectionLiterals.<QualifiedName>newArrayList();
          ExecutableProperty_c _oneC_EPOnR4006 = ExecutableProperty_c.getOneC_EPOnR4006(((PropertyParameter_c)xtumlElement));
          RequiredExecutableProperty_c[] _manySPR_REPsOnR4500 = RequiredExecutableProperty_c.getManySPR_REPsOnR4500(_oneC_EPOnR4006);
          RequiredOperation_c[] _manySPR_ROsOnR4502 = RequiredOperation_c.getManySPR_ROsOnR4502(_manySPR_REPsOnR4500);
          final Function1<RequiredOperation_c, QualifiedName> _function = (RequiredOperation_c spr_ro) -> {
            QualifiedName _maslQualifiedName = this.getMaslQualifiedName(spr_ro);
            return _maslQualifiedName.append(name);
          };
          List<QualifiedName> _map = ListExtensions.<RequiredOperation_c, QualifiedName>map(((List<RequiredOperation_c>)Conversions.doWrapArray(_manySPR_ROsOnR4502)), _function);
          Iterables.<QualifiedName>addAll(params, _map);
          ExecutableProperty_c _oneC_EPOnR4006_1 = ExecutableProperty_c.getOneC_EPOnR4006(((PropertyParameter_c)xtumlElement));
          ProvidedExecutableProperty_c[] _manySPR_PEPsOnR4501 = ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(_oneC_EPOnR4006_1);
          ProvidedOperation_c[] _manySPR_POsOnR4503 = ProvidedOperation_c.getManySPR_POsOnR4503(_manySPR_PEPsOnR4501);
          final Function1<ProvidedOperation_c, QualifiedName> _function_1 = (ProvidedOperation_c spr_po) -> {
            QualifiedName _maslQualifiedName = this.getMaslQualifiedName(spr_po);
            return _maslQualifiedName.append(name);
          };
          List<QualifiedName> _map_1 = ListExtensions.<ProvidedOperation_c, QualifiedName>map(((List<ProvidedOperation_c>)Conversions.doWrapArray(_manySPR_POsOnR4503)), _function_1);
          Iterables.<QualifiedName>addAll(params, _map_1);
          _xblockexpression = params;
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      QualifiedName _maslQualifiedName = this.getMaslQualifiedName(xtumlElement, name);
      _switchResult = Collections.<QualifiedName>unmodifiableList(CollectionLiterals.<QualifiedName>newArrayList(_maslQualifiedName));
    }
    return _switchResult;
  }
  
  public QualifiedName getMaslQualifiedName(final NonRootModelElement xtumlElement, final String name) {
    QualifiedName _xblockexpression = null;
    {
      final QualifiedName prefix = this.getMaslQualifiedNamePrefix(xtumlElement);
      QualifiedName _xifexpression = null;
      boolean _notEquals = (!Objects.equal(null, prefix));
      if (_notEquals) {
        _xifexpression = prefix.append(name);
      } else {
        _xifexpression = QualifiedName.create(name);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
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
      if (xtumlElement instanceof UserDataType_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        _switchResult = this.getMaslQualifiedName(_component);
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
    return null;
  }
}
