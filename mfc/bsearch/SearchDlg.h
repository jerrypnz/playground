// SearchDlg.h : header file
//

#if !defined(AFX_SEARCHDLG_H__E769C775_04DD_424F_BA31_587724B2D1DB__INCLUDED_)
#define AFX_SEARCHDLG_H__E769C775_04DD_424F_BA31_587724B2D1DB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CSearchDlg dialog

const int MAX_NUM = 20;

class CSearchDlg : public CDialog
{
// Construction
public:
	CSearchDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CSearchDlg)
	enum { IDD = IDD_SEARCH_DIALOG };
	CListCtrl	m_dataList;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CSearchDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	virtual void OnOK();
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CSearchDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnOk();
	afx_msg void OnSearch();
	afx_msg void OnTimer(UINT nIDEvent);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:

	void NextStep();
	void StartSearch();
	int dataToFind;
	int low;
	int high;
	int mid;
	void InitData();
	int sortedData[MAX_NUM];
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SEARCHDLG_H__E769C775_04DD_424F_BA31_587724B2D1DB__INCLUDED_)
