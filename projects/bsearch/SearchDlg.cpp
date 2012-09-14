// SearchDlg.cpp : implementation file
//

#include "stdafx.h"
#include "Search.h"
#include "SearchDlg.h"
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

int Compare(const void* a,const void* b)
{
	int left = *((int*)a);
	int right = *((int*)b);
	if(left > right)
		return 1;
	else if(left < right)
		return -1;
	else
		return 0;
}

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
// CSearchDlg dialog

CSearchDlg::CSearchDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CSearchDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CSearchDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CSearchDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CSearchDlg)
	DDX_Control(pDX, IDC_DATA_LIST, m_dataList);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CSearchDlg, CDialog)
	//{{AFX_MSG_MAP(CSearchDlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_OK, OnOk)
	ON_BN_CLICKED(IDC_SEARCH, OnSearch)
	ON_WM_TIMER()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CSearchDlg message handlers

BOOL CSearchDlg::OnInitDialog()
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
	InitData();
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CSearchDlg::OnSysCommand(UINT nID, LPARAM lParam)
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

void CSearchDlg::OnPaint() 
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
HCURSOR CSearchDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}


void CSearchDlg::OnOk() 
{
	this->EndDialog(0);
}

void CSearchDlg::OnOK() 
{
}

void CSearchDlg::InitData()
{
	m_dataList.InsertColumn(0,"操作指针",LVCFMT_LEFT,80);
	m_dataList.InsertColumn(1,"元素位置",LVCFMT_LEFT,90);
	m_dataList.InsertColumn(2,"元素内容",LVCFMT_LEFT,90);
	time_t t;
	srand((unsigned)time(&t));
	for(int i=0;i<MAX_NUM;i++)
	{
		int t = rand() % 3;
		switch(t)
		{
		case 0:
			sortedData[i] = rand() % 500;
			break;
		case 1:
			sortedData[i] = rand() % 1000;
			break;
		case 2:
		default:
			sortedData[i] = rand() % 10000;
		}
	}
	qsort(sortedData,MAX_NUM,sizeof(int),Compare);
	CString temp;
	for(int j=0;j<MAX_NUM;j++)
	{
		temp.Format("%d",j);
		m_dataList.InsertItem(j," ");
		m_dataList.SetItemText(j,1,temp);
		temp.Format("%d",sortedData[j]);
		m_dataList.SetItemText(j,2,temp);
	}
}

void CSearchDlg::StartSearch()
{
	m_dataList.SetItemText(low,0," ");
	m_dataList.SetItemText(high,0," ");
	m_dataList.SetItemText(mid,0," ");
	low = 0;
	high = MAX_NUM-1;
	mid = (high + low)/2;
	CString temp;
	GetDlgItemText(IDC_NUM,temp);
	sscanf(temp,"%d",&dataToFind);
	m_dataList.SetItemText(low,0,"low-->");
	m_dataList.SetItemText(mid,0,"mid-->");
	m_dataList.SetItemText(high,0,"high-->");
	SetTimer(1,2000,NULL);
}

void CSearchDlg::NextStep()
{
	if(dataToFind > sortedData[mid])
	{
		m_dataList.SetItemText(low,0," ");
		low = mid;
		mid = (high + low)/2;
		m_dataList.SetItemText(low,0,"low-->");
		m_dataList.SetItemText(mid,0,"mid-->");
	}
	else if(dataToFind < sortedData[mid])
	{
		m_dataList.SetItemText(high,0," ");
		high = mid;
		mid = (high + low)/2;
		m_dataList.SetItemText(high,0,"high-->");
		m_dataList.SetItemText(mid,0,"mid-->");
	}

	if(dataToFind == sortedData[mid])
	{
		KillTimer(1);
		m_dataList.SetItemText(low,0," ");
		m_dataList.SetItemText(high,0," ");
		CString temp;
		temp.Format("查找结果：指定元素位于位置%d",mid);
		MessageBox(temp,"查找结果",MB_OK|MB_ICONINFORMATION);		
	}
	else if(high == mid || low==mid)
	{
		KillTimer(1);
		MessageBox("查找结果：未找到指定元素","查找结果",MB_OK|MB_ICONINFORMATION);
		return;
	}
}

void CSearchDlg::OnSearch() 
{
	StartSearch();
}

void CSearchDlg::OnTimer(UINT nIDEvent) 
{
	NextStep();
}
