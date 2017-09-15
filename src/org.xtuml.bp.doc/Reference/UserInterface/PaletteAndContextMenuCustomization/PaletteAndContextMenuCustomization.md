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

All of the tool disablement settings are of the form `-Dconfigure.bp.<tool name>=disabled` where the 
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
-Dconfigure.bp.Actor=disabled
-Dconfigure.bp.ManageProjectMarkings=disabled
```

If you wish to change what tools are displayed, simply exit BridgePoint, add or change the tool
disablement setting in the INI file from *disabled* to *enabled* and then start BridgePoint again.   

Disabling all of the tools in a Palette drawer will automatically hide the drawer itself.  

### Context Menu 
This section lists all of the context menu entries that may be disabled:
```
configure.bp.AddBuiltinExternalEntities
configure.bp.AddtoIdentifier
configure.bp.AssignEvent
configure.bp.AssignSignal
configure.bp.CantHappenEvent
configure.bp.CantHappeninState
configure.bp.CheckModelIntegrity
configure.bp.CheckReferentialIntegrity
configure.bp.CleanseforModelCompiler
configure.bp.CombineWith
configure.bp.CreateChangeSet
configure.bp.Createdocumentation
configure.bp.Disconnect
configure.bp.ExportMASLDomain
configure.bp.ExportMASLDomains
configure.bp.ExportMASLProject
configure.bp.Formalize
configure.bp.GenerateFunctionsFromList
configure.bp.IgnoreEvent
configure.bp.IgnoreinState
configure.bp.LoadandPersist
configure.bp.LocateOrphanedElements
configure.bp.MakePublic
configure.bp.MakeProtected
configure.bp.MakePrivate
configure.bp.MarkInstanceDestroyed
configure.bp.MarkInstancenotDestroyed
configure.bp.ManageProjectMarkings
configure.bp.MoveDown
configure.bp.MoveUp
configure.bp.PublishReferences
configure.bp.ReconcileGraphics
configure.bp.RemoveEvent
configure.bp.RemovefromIdentifier
configure.bp.RemoveSignal
configure.bp.SetasBaseAttribute
configure.bp.SetasDerivedAttribute
configure.bp.SetModelCompiler
configure.bp.SetToProvider
configure.bp.SetFromProvider
configure.bp.Split
configure.bp.ToggleEndVisibility
configure.bp.ToggleStartVisibility
configure.bp.Unassign
configure.bp.Unformalize
```

### Palette and "New >" Context Menu
This section lists all of the Palette and context menu "New >" tools that may be disabled:
```
configure.bp.AcceptEventAction
configure.bp.AcceptTimeEventAction
configure.bp.Action
configure.bp.ActivityEdge
configure.bp.ActivityFinalNode
configure.bp.ActivityPartition
configure.bp.Actor
configure.bp.Argument
configure.bp.Association
configure.bp.AssociativeLink
configure.bp.AsynchronousMessage
configure.bp.Attribute
configure.bp.BridgeOperation
configure.bp.Class
configure.bp.ClassStateMachine
configure.bp.Component
configure.bp.ComponentReference
configure.bp.CommunicationLine
configure.bp.Constant
configure.bp.ConstantSpecification
configure.bp.DecisionMergeNode
configure.bp.EnumerationDataType
configure.bp.Enumerator
configure.bp.Event
configure.bp.Exception
configure.bp.ExternalEntity
configure.bp.FlowFinalNode
configure.bp.ForkJoinNode
configure.bp.Function
configure.bp.ImportedClass
configure.bp.InitialNode
configure.bp.Instance
configure.bp.InstanceStateMachine
configure.bp.InteractionClass
configure.bp.InteractionComponent
configure.bp.InteractionExternalEntity
configure.bp.Interface
configure.bp.Link
configure.bp.Member
configure.bp.ObjectNode
configure.bp.Operation
configure.bp.Package
configure.bp.PackageParticipant
configure.bp.Parameter
configure.bp.ProvidedInterface
configure.bp.RequiredInterface
configure.bp.ReturnMessage
configure.bp.SendSignalAction
configure.bp.Signal
configure.bp.State
configure.bp.StructuredDataType
configure.bp.Subtype
configure.bp.SuperType
configure.bp.SynchronousMessage
configure.bp.TimingMark
configure.bp.TimeSpan
configure.bp.UserDataType
configure.bp.UseCase
```
