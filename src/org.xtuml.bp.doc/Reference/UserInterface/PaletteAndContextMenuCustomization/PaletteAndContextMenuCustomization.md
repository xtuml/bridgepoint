Palette and Context Menu Customization 
===================

Introduction
------------
The primary BridgePoint tools are accessed through the xtUML Modeling Perspective. The
tools are accessed both through context menu entries (CMEs) and the Palette of the
graphical canvases.  

In some cases, a user may wish to modify what tools are displayed in the context menus
and the Palette.  BridgePoint supports the ability to hide tools so they do not show
up for the user to use.  This feature is used to simplify and clean up the user 
interface so the modeler is presented with less options.  


Tool Configuration
------------
In the BridgePoint installation directory you will find a file called `bridgepoint.ini`. This
file contains settings for eclipse and the underlying Java virtual machine. The user 
interface settings to disable tools are added at the end of this file.  

All of the tool disablement settings are of the form `-Dbridgepoint.<tool name>=disabled` where the 
tool name has spaces removed.  

For example, here is a `bridgepoint.ini` that disables the `Actor` tool and the
`Manage Project Markings` CME.   

```xml
-startup
../Eclipse/plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar
--launcher.library
../Eclipse/plugins/org.eclipse.equinox.launcher.cocoa.macosx.x86_64_1.1.300.v20150602-1417
-vmargs
-Dorg.eclipse.epp.logging.aeri.ui.skipReports=true
-XstartOnFirstThread
-Dorg.eclipse.swt.internal.carbon.smallFonts
-Dbridgepoint.Actor=disabled
-Dbridgepoint.ManageProjectMarkings=disabled
```

If you wish to change what tools are displayed, simply exit BridgePoint, add or change the tool
disablement setting in the INI file from *disabled* to *enabled* and then start BridgePoint again.   

Disabling all of the tools in a Palette drawer will automatically hide the drawer itself.  

### Context Menu 
This section lists all of the context menu entries that may be disabled:
```
bridgepoint.AddBuiltinExternalEntities
bridgepoint.AddtoIdentifier
bridgepoint.AssignEvent
bridgepoint.AssignSignal
bridgepoint.CantHappenEvent
bridgepoint.CantHappeninState
bridgepoint.CardinalityOnAssociativeLink
bridgepoint.CheckModelIntegrity
bridgepoint.CheckReferentialIntegrity
bridgepoint.CombineWith
bridgepoint.CreateChangeSet
bridgepoint.CreateDocumentation
bridgepoint.Disconnect
bridgepoint.ExportMASLDomain
bridgepoint.ExportMASLDomains
bridgepoint.ExportMASLProject
bridgepoint.Formalize
bridgepoint.GenerateFunctionsFromList
bridgepoint.IgnoreEvent
bridgepoint.IgnoreinState
bridgepoint.LoadandPersist
bridgepoint.LocateOrphanedElements
bridgepoint.MakePublic
bridgepoint.MakeProtected
bridgepoint.MakePrivate
bridgepoint.MarkInstanceDestroyed
bridgepoint.MarkInstancenotDestroyed
bridgepoint.ManageProjectMarkings
bridgepoint.MoveDown
bridgepoint.MoveUp
bridgepoint.PublishReferences
bridgepoint.ReconcileGraphics
bridgepoint.RemoveEvent
bridgepoint.RemovefromIdentifier
bridgepoint.RemoveSignal
bridgepoint.SetasBaseAttribute
bridgepoint.SetasDerivedAttribute
bridgepoint.SetModelCompiler
bridgepoint.SetToProvider
bridgepoint.SetFromProvider
bridgepoint.Split
bridgepoint.ToggleEndVisibility
bridgepoint.ToggleStartVisibility
bridgepoint.Unassign
bridgepoint.Unformalize
```

### Palette and "New >" Context Menu
This section lists all of the Palette and context menu "New >" tools that may be disabled:
```
bridgepoint.AcceptEventAction
bridgepoint.AcceptTimeEventAction
bridgepoint.Action
bridgepoint.ActivityEdge
bridgepoint.ActivityFinalNode
bridgepoint.ActivityPartition
bridgepoint.Actor
bridgepoint.Argument
bridgepoint.Association
bridgepoint.AssociativeLink
bridgepoint.AsynchronousMessage
bridgepoint.Attribute
bridgepoint.BridgeOperation
bridgepoint.Class
bridgepoint.ClassStateMachine
bridgepoint.Component
bridgepoint.ComponentReference
bridgepoint.CommunicationLine
bridgepoint.Constant
bridgepoint.ConstantSpecification
bridgepoint.DecisionMergeNode
bridgepoint.EnumerationDataType
bridgepoint.Enumerator
bridgepoint.Event
bridgepoint.Exception
bridgepoint.ExternalEntity
bridgepoint.Extend
bridgepoint.FlowFinalNode
bridgepoint.ForkJoinNode
bridgepoint.Function
bridgepoint.Generalization
bridgepoint.ImportedClass
bridgepoint.InitialNode
bridgepoint.Include
bridgepoint.Instance
bridgepoint.InstanceStateMachine
bridgepoint.InteractionClass
bridgepoint.InteractionComponent
bridgepoint.InteractionExternalEntity
bridgepoint.Interface
bridgepoint.Link
bridgepoint.Member
bridgepoint.ObjectNode
bridgepoint.Operation
bridgepoint.Package
bridgepoint.PackageParticipant
bridgepoint.Parameter
bridgepoint.ProvidedInterface
bridgepoint.RequiredInterface
bridgepoint.ReturnMessage
bridgepoint.SendSignalAction
bridgepoint.Signal
bridgepoint.State
bridgepoint.StructuredDataType
bridgepoint.Subtype
bridgepoint.SuperType
bridgepoint.SynchronousMessage
bridgepoint.TimingMark
bridgepoint.TimeSpan
bridgepoint.UserDataType
bridgepoint.UseCase
bridgepoint.UseCaseAssociation
```

### Example 
Here is an example set of additions to the end of `bridgepoint.ini` that disables all 
of the UML analysis diagram tools.

```
-Dbridgepoint.AcceptEventAction=disabled
-Dbridgepoint.AcceptTimeEventAction=disabled
-Dbridgepoint.Action=disabled
-Dbridgepoint.ActivityEdge=disabled
-Dbridgepoint.ActivityFinalNode=disabled
-Dbridgepoint.ActivityPartition=disabled
-Dbridgepoint.Actor=disabled
-Dbridgepoint.AssociativeLink=disabled
-Dbridgepoint.AsynchronousMessage=disabled
-Dbridgepoint.CommunicationLine=disabled
-Dbridgepoint.DecisionMergeNode=disabled
-Dbridgepoint.Extend=disabled
-Dbridgepoint.FlowFinalNode=disabled
-Dbridgepoint.ForkJoinNode=disabled
-Dbridgepoint.Generalization=disabled
-Dbridgepoint.Include=disabled
-Dbridgepoint.InitialNode=disabled
-Dbridgepoint.Instance=disabled
-Dbridgepoint.InteractionClass=disabled
-Dbridgepoint.InteractionComponent=disabled
-Dbridgepoint.InteractionExternalEntity=disabled
-Dbridgepoint.Link=disabled
-Dbridgepoint.ObjectNode=disabled
-Dbridgepoint.PackageParticipant=disabled
-Dbridgepoint.ReturnMessage=disabled
-Dbridgepoint.SendSignalAction=disabled
-Dbridgepoint.SynchronousMessage=disabled
-Dbridgepoint.TimingMark=disabled
-Dbridgepoint.TimeSpan=disabled
-Dbridgepoint.UseCase=disabled
-Dbridgepoint.UseCaseAssociation=disabled
```