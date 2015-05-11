// fontcheckerDlg.h : header file
//

#if !defined(AFX_FONTCHECKERDLG_H__870F5C63_F0B1_45AE_B497_38254573E6AA__INCLUDED_)
#define AFX_FONTCHECKERDLG_H__870F5C63_F0B1_45AE_B497_38254573E6AA__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CFontcheckerDlg dialog

class CFontcheckerDlg : public CDialog
{
// Construction
public:
	CFontcheckerDlg(CWnd* pParent = NULL);	// standard constructor

	void setFileToUse(CString filename);

// Dialog Data
	//{{AFX_DATA(CFontcheckerDlg)
	enum { IDD = IDD_FONTCHECKER_DIALOG };
	CEdit	m_infobox;
	CListBox	m_listbox;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CFontcheckerDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	BYTE required_charset;
	static int CALLBACK EnumFontFamExProc(
		ENUMLOGFONTEX *lpelfe,	
		NEWTEXTMETRICEX *lpntme,	
		int FontType,	
		LPARAM lParam);

	HICON m_hIcon;
	CStringList m_fontlist;
	CStringList m_badfontslist;
	CString m_badfontsfile;

	void processFonts(void);
	void populateListbox(void);

	// Generated message map functions
	//{{AFX_MSG(CFontcheckerDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_FONTCHECKERDLG_H__870F5C63_F0B1_45AE_B497_38254573E6AA__INCLUDED_)
