// fontchecker.h : main header file for the FONTCHECKER application
//

#if !defined(AFX_FONTCHECKER_H__0DB56992_50B9_4B63_A852_6E259A3F7782__INCLUDED_)
#define AFX_FONTCHECKER_H__0DB56992_50B9_4B63_A852_6E259A3F7782__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

class CCustomCommandLineInfo : public CCommandLineInfo
{
  CString m_fontFile;
 
  //public methods for checking these.
public:
  CString getFontFile() { return m_fontFile; };
   
  virtual void ParseParam(const char* pszParam, BOOL bFlag, BOOL bLast)
  {
	  m_fontFile = pszParam;
  }
};

/////////////////////////////////////////////////////////////////////////////
// CFontcheckerApp:
// See fontchecker.cpp for the implementation of this class
//

class CFontcheckerApp : public CWinApp
{
public:
	CFontcheckerApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CFontcheckerApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL


protected:
	//{{AFX_MSG(CFontcheckerApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()

};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_FONTCHECKER_H__0DB56992_50B9_4B63_A852_6E259A3F7782__INCLUDED_)
