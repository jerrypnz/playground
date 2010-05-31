// Shoot.h : main header file for the SHOOT application
//

#if !defined(AFX_SHOOT_H__7C8B13B9_7996_4745_849E_A4E5B50CAF06__INCLUDED_)
#define AFX_SHOOT_H__7C8B13B9_7996_4745_849E_A4E5B50CAF06__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CShootApp:
// See Shoot.cpp for the implementation of this class
//

class CShootApp : public CWinApp
{
public:
	CShootApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CShootApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CShootApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SHOOT_H__7C8B13B9_7996_4745_849E_A4E5B50CAF06__INCLUDED_)
