set Shell = CreateObject("WScript.Shell")
set objFSO = CreateObject("Scripting.FileSystemObject")

set arguments = WScript.arguments
if arguments.unnamed.count < 9 then
    WScript.Echo "You must specify arguments: <AllUsersDesktop> <Name> <Command> <Params> <WorkingDir> <IconPath> <nIcon> <Shortcut> <Replace>"
    WScript.Quit 1
else
    AllUsersDesktop = arguments.unnamed.Item(0)
    Name = arguments.unnamed.Item(1)
    Command = arguments.unnamed.Item(2)
    Params = arguments.unnamed.Item(3)
    WorkingDir = arguments.unnamed.Item(4)
    IconPath = arguments.unnamed.Item(5)
    nIcon = arguments.unnamed.Item(6)
    Shortcut = arguments.unnamed.Item(7)
    ReplaceFlag = arguments.unnamed.Item(8)
end if

DesktopPath = Shell.SpecialFolders("AllUsersDesktop")
DesktopPath2 = Shell.SpecialFolders("Desktop")

' Get the short path so we can put the shortcut on non-english desktops
set tmpf = objFSO.GetFolder(DesktopPath)
DeskPathShort = tmpf.ShortPath
set tmpf = objFSO.GetFolder(DesktopPath2)
DeskPath2Short = tmpf.ShortPath

' This block checks if the shortcut is to be created on the all users desktop
' or not.  If it is, we write a dummy file to the AUD to test that we have the
' necessary permissions.  If we do, great, delete the dummy file and continue.
' If we do not, fall back to the current user desktop.
if AllUsersDesktop Eqv TRUE then
  FileName = DeskPathShort & "\" & Name & ".lnk"
  tempFileName = DeskPathShort & "\" & Name & "app.lnk"
  
  ' Enable error handling
  On Error Resume Next
  
  set link = Shell.CreateShortcut(tempFileName)
  link.Description = Name
  link.Save
  set link=nothing
  
  ' Reset error handling
  On Error Goto 0
  
  if objFSO.FileExists(tempFileName) then
    objFSO.DeleteFile tempFileName, True
  else
    FileName = DeskPath2Short & "\" & Name & ".lnk"
  end if
else
  FileName = DeskPath2Short & "\" & Name & ".lnk"
end if

WScript.Echo "File name is:" & FileName

if ReplaceFlag Eqv FALSE then
  if objFSO.FileExists(FileName) then
    WScript.Quit 2
  end if
end if

' Create the shortcut with the appropriate settings
set link = Shell.CreateShortcut(FileName)
link.Description = Name
link.TargetPath = Command
link.Arguments = Params
link.HotKey = Shortcut
link.IconLocation = IconPath & "," & nIcon
link.WorkingDirectory = WorkingDir
link.WindowStyle = 7
link.Save
set link=nothing

