; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Dyndns"
#define MyAppVersion "1.0"
#define MyAppPublisher "Ivan de la Beldad Fernandez"
#define MyAppExeName "Dyndns.exe"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{9E9AB877-CACC-4E55-8BD1-658756364C52}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
DefaultDirName={pf}\{#MyAppName}
DisableProgramGroupPage=yes
OutputBaseFilename=DyndnsInstaller
Compression=lzma
SolidCompression=yes
SetupIconFile="P:\java\Dyndns\exe\installer_icon.ico"
OutputDir="P:\java\Dyndns\exe"

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}";
Name: "autorun"; Description: "Start program with Windows"; GroupDescription: "Auto Start";

[Files]
Source: "P:\java\Dyndns\exe\Dyndns.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "P:\java\Dyndns\exe\jre1.8.0_91\*"; DestDir: "{app}\jre1.8.0_91"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{commonprograms}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "P:\java\Dyndns\exe\installer_icon.ico"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon;
;IconFilename: "P:\java\Dyndns\target\classes\img\logo.ico"
; AUTO START
Name: "{userstartup}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: autorun;
;IconFilename: "P:\java\Dyndns\target\classes\img\logo.ico"

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: postinstall skipifsilent
