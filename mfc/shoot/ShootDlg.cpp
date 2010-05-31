// ShootDlg.cpp : implementation file
//

#include "stdafx.h"
#include "Shoot.h"
#include "ShootDlg.h"
#include "ShootBoard.h"
#include <stdlib.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

const int TARGET_X = 8;
const int TARGET_Y = 8;
const char* TARGET_MSG = "该程序是一个打靶游戏，点击开始会出现动态靶子，单击鼠标可以打靶，单发子弹最高环为10环";

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
// CShootDlg dialog

CShootDlg::CShootDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CShootDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CShootDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CShootDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CShootDlg)
	DDX_Control(pDX, IDC_TARGET, m_target);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CShootDlg, CDialog)
	//{{AFX_MSG_MAP(CShootDlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDB_START, OnStart)
	ON_WM_LBUTTONDOWN()
	ON_BN_CLICKED(IDB_CANCEL, OnCancel)
	ON_WM_TIMER()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CShootDlg message handlers

BOOL CShootDlg::OnInitDialog()
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
	
	// TODO: Add extra initialization here
	CRect temp;
	m_target.GetClientRect(&temp);
	TARGET_WIDTH = temp.Width();
	TARGET_HEIGHT = temp.Height();
	board = new ShootBoard(m_target.GetDC(),&temp);
	SetDlgItemText(IDC_TARGET,TARGET_MSG);
	showTarget = true;
	canShoot = false;
	totalScore = 0;
	avgScore = 0;
	shootTimes = 0;
	currentScore = 0;
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CShootDlg::OnSysCommand(UINT nID, LPARAM lParam)
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

void CShootDlg::OnPaint() 
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
HCURSOR CShootDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CShootDlg::OnStart() 
{
	canShoot = false;
	showTarget = true;
	totalScore = 0;
	avgScore = 0;
	shootTimes = 0;
	currentScore = 0;
	SetTimer(1,650,NULL);
}

void CShootDlg::OnCancel() 
{
	this->OnOK();	
}


void CShootDlg::OnLButtonDown(UINT nFlags, CPoint point) 
{
	int x = point.x - TARGET_X;
	int y = point.y - TARGET_Y;
	if( (x<TARGET_WIDTH && x>0) &&
		(y<TARGET_HEIGHT && y>0) )
	{
		if(canShoot)
		{
			currentScore = board->Shoot(x,y);
			canShoot = false;
			UpdateScore();
		}
		
	}
	CDialog::OnLButtonDown(nFlags, point);
}

void CShootDlg::OnTimer(UINT nIDEvent) 
{
	if(showTarget)
	{
		board->GenerateTarget();
		canShoot = true;
		showTarget = false;
	}
	else
	{
		board->ClearBakground();
		canShoot = false;
		showTarget = true;
	}
}

void CShootDlg::UpdateScore()
{
	shootTimes++;
	totalScore = totalScore + currentScore;
	avgScore = totalScore / shootTimes;
	CString temp;
	temp.Format("%d次",shootTimes);
	SetDlgItemText(IDC_SHOT_TIMES,temp);
	temp.Format("%d环",currentScore);
	SetDlgItemText(IDC_CURRENT_SCORE,temp);
	temp.Format("%.0lf环",totalScore);
	SetDlgItemText(IDC_TOTAL_SCORE,temp);
	temp.Format("%.1lf环",avgScore);
	SetDlgItemText(IDC_AVG_SCORE,temp);
}
