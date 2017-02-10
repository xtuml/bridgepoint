package org.xtuml.bp.xtext.masl.ui.rename;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.Requirement_c;
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
  
  public QualifiedName getMaslQualifiedName(final NonRootModelElement xtumlElement) {
    QualifiedName _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (xtumlElement instanceof Attribute_c) {
        _matched=true;
        ModelClass_c _oneO_OBJOnR102 = ModelClass_c.getOneO_OBJOnR102(((Attribute_c)xtumlElement));
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_oneO_OBJOnR102);
        String _name = ((Attribute_c)xtumlElement).getName();
        _switchResult = _maslQualifiedName.append(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Component_c) {
        _matched=true;
        String _name = ((Component_c)xtumlElement).getName();
        _switchResult = QualifiedName.create(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Function_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_component);
        String _name = ((Function_c)xtumlElement).getName();
        _switchResult = _maslQualifiedName.append(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof RequiredOperation_c) {
        _matched=true;
        RequiredExecutableProperty_c _oneSPR_REPOnR4502 = RequiredExecutableProperty_c.getOneSPR_REPOnR4502(((RequiredOperation_c)xtumlElement));
        Requirement_c _oneC_ROnR4500 = Requirement_c.getOneC_ROnR4500(_oneSPR_REPOnR4502);
        InterfaceReference_c _oneC_IROnR4009 = InterfaceReference_c.getOneC_IROnR4009(_oneC_ROnR4500);
        Port_c _oneC_POOnR4016 = Port_c.getOneC_POOnR4016(_oneC_IROnR4009);
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_oneC_POOnR4016);
        String _name = ((RequiredOperation_c)xtumlElement).getName();
        _switchResult = _maslQualifiedName.append(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ProvidedOperation_c) {
        _matched=true;
        ProvidedExecutableProperty_c _oneSPR_PEPOnR4503 = ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(((ProvidedOperation_c)xtumlElement));
        Provision_c _oneC_POnR4501 = Provision_c.getOneC_POnR4501(_oneSPR_PEPOnR4503);
        InterfaceReference_c _oneC_IROnR4009 = InterfaceReference_c.getOneC_IROnR4009(_oneC_POnR4501);
        Port_c _oneC_POOnR4016 = Port_c.getOneC_POOnR4016(_oneC_IROnR4009);
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_oneC_POOnR4016);
        String _name = ((ProvidedOperation_c)xtumlElement).getName();
        _switchResult = _maslQualifiedName.append(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof ModelClass_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_component);
        String _name = ((ModelClass_c)xtumlElement).getName();
        _switchResult = _maslQualifiedName.append(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Operation_c) {
        _matched=true;
        ModelClass_c _oneO_OBJOnR115 = ModelClass_c.getOneO_OBJOnR115(((Operation_c)xtumlElement));
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_oneO_OBJOnR115);
        String _name = ((Operation_c)xtumlElement).getName();
        _switchResult = _maslQualifiedName.append(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof Port_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_component);
        String _name = ((Port_c)xtumlElement).getName();
        _switchResult = _maslQualifiedName.append(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof StateMachineState_c) {
        _matched=true;
        StateMachine_c _oneSM_SMOnR501 = StateMachine_c.getOneSM_SMOnR501(((StateMachineState_c)xtumlElement));
        InstanceStateMachine_c _oneSM_ISMOnR517 = InstanceStateMachine_c.getOneSM_ISMOnR517(_oneSM_SMOnR501);
        ModelClass_c _oneO_OBJOnR518 = ModelClass_c.getOneO_OBJOnR518(_oneSM_ISMOnR517);
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_oneO_OBJOnR518);
        String _name = ((StateMachineState_c)xtumlElement).getName();
        _switchResult = _maslQualifiedName.append(_name);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof StateMachineEvent_c) {
        _matched=true;
        StateMachine_c _oneSM_SMOnR502 = StateMachine_c.getOneSM_SMOnR502(((StateMachineEvent_c)xtumlElement));
        InstanceStateMachine_c _oneSM_ISMOnR517 = InstanceStateMachine_c.getOneSM_ISMOnR517(_oneSM_SMOnR502);
        ModelClass_c _oneO_OBJOnR518 = ModelClass_c.getOneO_OBJOnR518(_oneSM_ISMOnR517);
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_oneO_OBJOnR518);
        String _mning = ((StateMachineEvent_c)xtumlElement).getMning();
        _switchResult = _maslQualifiedName.append(_mning);
      }
    }
    if (!_matched) {
      if (xtumlElement instanceof UserDataType_c) {
        _matched=true;
        NonRootModelElement _component = this.getComponent(xtumlElement);
        QualifiedName _maslQualifiedName = this.getMaslQualifiedName(_component);
        String _name = ((UserDataType_c)xtumlElement).getName();
        _switchResult = _maslQualifiedName.append(_name);
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
