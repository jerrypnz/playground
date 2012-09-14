// Puzzle.h : main header file for the PUZZLE application
//

#if !defined(AFX_PUZZLE_H__22243F21_C7C9_48AC_B0FA_F6E04B05DB0D__INCLUDED_)
#define AFX_PUZZLE_H__22243F21_C7C9_48AC_B0FA_F6E04B05DB0D__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CPuzzleApp:
// See Puzzle.cpp for the implementation of this class
//

class CPuzzleApp : public CWinApp
{
public:
	CPuzzleApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CPuzzleApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CPuzzleApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_PUZZLE_H__22243F21_C7C9_48AC_B0FA_F6E04B05DB0D__INCLUDED_)
