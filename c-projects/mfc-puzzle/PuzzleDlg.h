// PuzzleDlg.h : header file
//

#if !defined(AFX_PUZZLEDLG_H__0912EA80_4EE2_4FA9_A018_1C7E35F88E6E__INCLUDED_)
#define AFX_PUZZLEDLG_H__0912EA80_4EE2_4FA9_A018_1C7E35F88E6E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CPuzzleDlg dialog

class CPuzzleDlg : public CDialog
{
// Construction
public:
	CPuzzleDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CPuzzleDlg)
	enum { IDD = IDD_PUZZLE_DIALOG };
	CStatic	m_destImage;
	CStatic	m_srcImage;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CPuzzleDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CPuzzleDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnSplit();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnRestore();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void DrawGrid();
	static void ClearRect(CDC* pDC,const CRect* rect);
	CRect currentDest;
	int nPartFinished;
	bool gameStarted;
	int destY;
	int destX;
	CRect partRect[6];
	int currentPart;
	bool isDragging;
	HCURSOR movingCursor;
	int which[6];
	void InitGame();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_PUZZLEDLG_H__0912EA80_4EE2_4FA9_A018_1C7E35F88E6E__INCLUDED_)
