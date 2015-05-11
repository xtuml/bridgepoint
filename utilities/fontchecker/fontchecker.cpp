// fontchecker.cpp : Defines the class behaviors for the application.
//

#include "stdafx.h"
#include "fontchecker.h"
#include "fontcheckerDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CFontcheckerApp

BEGIN_MESSAGE_MAP(CFontcheckerApp, CWinApp)
	//{{AFX_MSG_MAP(CFontcheckerApp)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG
	ON_COMMAND(ID_HELP, CWinApp::OnHelp)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CFontcheckerApp construction

CFontcheckerApp::CFontcheckerApp()
{
	
}

/////////////////////////////////////////////////////////////////////////////
// The one and only CFontcheckerApp object

CFontcheckerApp theApp;

/////////////////////////////////////////////////////////////////////////////
// CFontcheckerApp initialization

BOOL CFontcheckerApp::InitInstance()
{
	// Standard initialization
	// If you are not using these features and wish to reduce the size
	//  of your final executable, you should remove from the following
	//  the specific initialization routines you do not need.

#ifdef _AFXDLL
	Enable3dControls();			// Call this when using MFC in a shared DLL
#else
	Enable3dControlsStatic();	// Call this when linking to MFC statically
#endif

	CCustomCommandLineInfo clInfo;
	ParseCommandLine(clInfo);

	CFontcheckerDlg dlg;
	m_pMainWnd = &dlg;
	dlg.setFileToUse(clInfo.getFontFile());
	int nResponse = dlg.DoModal();
	if (nResponse == IDOK)
	{
		// All done
	}

	// Since the dialog has been closed, return FALSE so that we exit the
	//  application, rather than start the application's message pump.
	return FALSE;
}

