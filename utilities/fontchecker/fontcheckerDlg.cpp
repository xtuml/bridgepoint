// fontcheckerDlg.cpp : implementation file
//

#include "stdafx.h"
#include "fontchecker.h"
#include "fontcheckerDlg.h"
#include <fstream>
#include <string>

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

using std::string;

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// Dialog Data
	//{{AFX_DATA(CAboutDlg)
	enum { IDD = IDD_ABOUTBOX };
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAboutDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	//{{AFX_MSG(CAboutDlg)
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
	//{{AFX_DATA_INIT(CAboutDlg)
	//}}AFX_DATA_INIT
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAboutDlg)
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	//{{AFX_MSG_MAP(CAboutDlg)
		// No message handlers
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CFontcheckerDlg dialog

CFontcheckerDlg::CFontcheckerDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CFontcheckerDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CFontcheckerDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
	required_charset = ANSI_CHARSET;
}

void CFontcheckerDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CFontcheckerDlg)
	DDX_Control(pDX, IDC_EDIT1, m_infobox);
	DDX_Control(pDX, IDC_LIST1, m_listbox);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CFontcheckerDlg, CDialog)
	//{{AFX_MSG_MAP(CFontcheckerDlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CFontcheckerDlg message handlers

BOOL CFontcheckerDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		CString strAboutMenu;
		strAboutMenu.LoadString(IDS_ABOUTBOX);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	m_infobox.SetWindowText("BridgePoint found the following fonts in your system.  These may prevent the proper operation of BridgePoint Generator.  Please disable or uninstall these fonts and restart BridgePoint before attempting to use the model compiler.");

	// Open file stream to read from
	// File contains filenames or filepaths of problem fonts
	std::ifstream file;
	if ( m_badfontsfile.IsEmpty() ) {
		m_badfontsfile = "font_list.txt";
	}
	file.open((LPCTSTR) m_badfontsfile);
	if (!file)
	{
		m_listbox.AddString( "Error in opening font_list.txt.  It must be in the same directory" );
		m_listbox.AddString( "as this application." );
	} else {
		string lineread;

		// Iterate through the file, add or remove each font as specified on the command line
		int rVal = 0;
		while(std::getline(file, lineread))
		{
			if ( lineread.find('#') != 0 ) {
				if ( lineread.find( "%" ) == 0 ) {
					// If the line starts with "%", then use this as the info in the warning dialog
					CString newtext = lineread.c_str();
					newtext.Replace( "%", "" );
					m_infobox.SetWindowText( (LPCTSTR) newtext );
				} else {
					m_badfontslist.AddTail(lineread.c_str());
				}
			}
		}
		processFonts();
		if ( !m_fontlist.IsEmpty() ) {
			populateListbox();
		}
	}

	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CFontcheckerDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialog::OnSysCommand(nID, lParam);
	}
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CFontcheckerDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CFontcheckerDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CFontcheckerDlg::setFileToUse(CString filename)
{
	m_badfontsfile = filename;
}

void CFontcheckerDlg::populateListbox(void)
{
	POSITION pos;
	POSITION pos2;
    CString curBadFontName;
	CString curExistingFontName;

	for( pos = m_fontlist.GetHeadPosition(); pos != NULL; )
	{
		curExistingFontName = m_fontlist.GetNext( pos );

		for( pos2 = m_badfontslist.GetHeadPosition(); pos2 != NULL; )
		{
			curBadFontName = m_badfontslist.GetNext( pos2 );
			if ( curBadFontName.IsEmpty() ) {
				continue;
			}
			if ( curBadFontName.Find( "---" ) == 0 ) {
				// If the font name starts with "---", then we do exact matching on the name
				curBadFontName.Replace( "---", "" );
				if ( !curBadFontName.CompareNoCase( curExistingFontName ) ) {
					m_listbox.AddString( curExistingFontName );
				}
			} else {
			    // If the font name doesn't start with "---", then we do substring matching on the name
				if ( curExistingFontName.Find( curBadFontName ) >= 0 ) {
					m_listbox.AddString( curExistingFontName );
				}
			}
		}
	}

	if ( m_listbox.GetCount() == 0 ) {
		this->OnOK();
	}
}

void CFontcheckerDlg::processFonts(void)
{
	LOGFONT lf;

	lf.lfCharSet = required_charset;
	lf.lfFaceName[0]='\0';

	::EnumFontFamiliesEx( this->GetDC()->m_hDC, &lf, (FONTENUMPROC) CFontcheckerDlg::EnumFontFamExProc, (LPARAM) &m_fontlist, 0);
}

int CALLBACK CFontcheckerDlg::EnumFontFamExProc(ENUMLOGFONTEX *lpelfe,NEWTEXTMETRICEX *lpntme,int FontType,LPARAM lParam)
{
	CStringList* m_temp = (CStringList*) lParam;
	m_temp->AddTail((char*)lpelfe->elfFullName);

	return 1; 	// I want to get all fonts
}

