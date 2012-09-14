// ShootDlg.h : header file
//

#if !defined(AFX_SHOOTDLG_H__B70B5A95_19C4_44B9_93AC_13225559F518__INCLUDED_)
#define AFX_SHOOTDLG_H__B70B5A95_19C4_44B9_93AC_13225559F518__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ShootBoard.h"
/////////////////////////////////////////////////////////////////////////////
// CShootDlg dialog

class CShootDlg : public CDialog
{
// Construction
public:
	void UpdateScore();
	CShootDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CShootDlg)
	enum { IDD = IDD_SHOOT_DIALOG };
	CStatic	m_target;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CShootDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;
	ShootBoard* board;
	int TARGET_WIDTH,TARGET_HEIGHT;
	// Generated message map functions
	//{{AFX_MSG(CShootDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnStart();
	afx_msg void OnCancel();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnTimer(UINT nIDEvent);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	bool canShoot;
	double totalScore;
	int shootTimes;
	double avgScore;
	int currentScore;
	bool showTarget;
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SHOOTDLG_H__B70B5A95_19C4_44B9_93AC_13225559F518__INCLUDED_)
