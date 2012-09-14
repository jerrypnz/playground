// ListGenDlg.h : header file
//

#if !defined(AFX_LISTGENDLG_H__C500EB35_4B8D_4CD7_A71C_52C7C35FD8A6__INCLUDED_)
#define AFX_LISTGENDLG_H__C500EB35_4B8D_4CD7_A71C_52C7C35FD8A6__INCLUDED_

	// Added by ClassView
#include "PlayList.h"	// Added by ClassView
#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CListGenDlg dialog

class CListGenDlg : public CDialog
{
// Construction
public:
	CListGenDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CListGenDlg)
	enum { IDD = IDD_LISTGEN_DIALOG };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CListGenDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CListGenDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnStart();
	afx_msg void OnExit();
	afx_msg void OnBrowse();
	afx_msg void OnSave();
	afx_msg void OnUp();
	afx_msg void OnDown();
	afx_msg void OnDelete();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	bool m_opend;
	CPlayList m_listInterface;
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_LISTGENDLG_H__C500EB35_4B8D_4CD7_A71C_52C7C35FD8A6__INCLUDED_)
