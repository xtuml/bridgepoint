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

All of the tool disablement settings are of the form `-Ddisable.bp.<tool name>=true` where the 
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
-Ddisable.bp.Actor=true
-Ddisable.bp.ManageProjectMarkings=true
```

If you wish to change what tools are displayed, simply exit BridgePoint, add or change the tool
disablement setting in the INI file from true to false and then start BridgePoint again.   

Disabling all of the tools in a Palette drawer will automatically hide the drawer itself.  

### Context Menu 
This section lists all of the context menu entries that may be disabled:
```
disable.bp.AddtoIdentifier
disable.bp.AssignEvent
disable.bp.AssignSignal
disable.bp.CantHappenEvent
disable.bp.CantHappeninState
disable.bp.CombineWith
disable.bp.Createdocumentation
disable.bp.ExportMASLDomain
disable.bp.ExportMASLDomains
disable.bp.ExportMASLProject
disable.bp.IgnoreEvent
disable.bp.IgnoreinState
disable.bp.MakePublic
disable.bp.MakeProtected
disable.bp.MakePrivate
disable.bp.MarkInstanceDestroyed
disable.bp.MarkInstancenotDestroyed
disable.bp.ManageProjectMarkings
disable.bp.MoveDown
disable.bp.MoveUp
disable.bp.PublishReferences
disable.bp.RemoveEvent
disable.bp.RemovefromIdentifier
disable.bp.RemoveSignal
disable.bp.SetasBaseAttribute
disable.bp.SetasDerivedAttribute
disable.bp.SetToProvider
disable.bp.SetFromProvider
disable.bp.Split
disable.bp.ToggleEndVisibility
disable.bp.ToggleStartVisibility
```

### Palette and "New >" Context Menu
This section lists all of the Palette and context menu "New >" tools that may be disabled:
```
disable.bp.AcceptEventAction
disable.bp.AcceptTimeEventAction
disable.bp.Action
disable.bp.ActivityEdge
disable.bp.ActivityFinalNode
disable.bp.ActivityPartition
disable.bp.Actor
disable.bp.Argument
disable.bp.Association
disable.bp.AssociativeLink
disable.bp.AsynchronousMessage
disable.bp.Attribute
disable.bp.BridgeOperation
disable.bp.Class
disable.bp.ClassStateMachine
disable.bp.Component
disable.bp.ComponentReference
disable.bp.CommunicationLine
disable.bp.Constant
disable.bp.ConstantSpecification
disable.bp.DecisionMergeNode
disable.bp.EnumerationDataType
disable.bp.Enumerator
disable.bp.Event
disable.bp.Exception
disable.bp.ExternalEntity
disable.bp.FlowFinalNode
disable.bp.ForkJoinNode
disable.bp.Function
disable.bp.ImportedClass
disable.bp.InitialNode
disable.bp.Instance
disable.bp.InstanceStateMachine
disable.bp.InteractionClass
disable.bp.InteractionComponent
disable.bp.InteractionExternalEntity
disable.bp.Interface
disable.bp.Link
disable.bp.Member
disable.bp.ObjectNode
disable.bp.Operation
disable.bp.Package
disable.bp.PackageParticipant
disable.bp.Parameter
disable.bp.ProvidedInterface
disable.bp.RequiredInterface
disable.bp.ReturnMessage
disable.bp.SendSignalAction
disable.bp.Signal
disable.bp.State
disable.bp.StructuredDataType
disable.bp.Subtype
disable.bp.SuperType
disable.bp.SynchronousMessage
disable.bp.TimingMark
disable.bp.TimeSpan
disable.bp.UserDataType
disable.bp.UseCase
```
