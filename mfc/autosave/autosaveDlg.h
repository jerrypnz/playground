// autosaveDlg.h : header file
//

#if !defined(AFX_AUTOSAVEDLG_H__1F06D31D_E122_4901_8460_96266F5C6307__INCLUDED_)
#define AFX_AUTOSAVEDLG_H__1F06D31D_E122_4901_8460_96266F5C6307__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CAutosaveDlg dialog

class CAutosaveDlg : public CDialog
{
// Construction
public:
	CAutosaveDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CAutosaveDlg)
	enum { IDD = IDD_AUTOSAVE_DIALOG };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAutosaveDlg)
	public:
	virtual BOOL PreTranslateMessage(MSG* pMsg);
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CAutosaveDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnExit();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnOption();
	afx_msg void OnSave();
	afx_msg void OnClear();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_AUTOSAVEDLG_H__1F06D31D_E122_4901_8460_96266F5C6307__INCLUDED_)
