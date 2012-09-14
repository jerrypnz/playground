// ListGenDlg.cpp : implementation file
//

#include "stdafx.h"
#include "ListGen.h"
#include "ListGenDlg.h"
#include <string>
#include <vector>
#include <windows.h>

using namespace std;

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

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
// CListGenDlg dialog

CListGenDlg::CListGenDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CListGenDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CListGenDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDI_MAINICON);
	m_opend = false;
}

void CListGenDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CListGenDlg)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CListGenDlg, CDialog)
	//{{AFX_MSG_MAP(CListGenDlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_START, OnStart)
	ON_BN_CLICKED(IDC_EXIT, OnExit)
	ON_BN_CLICKED(IDC_BROWSE, OnBrowse)
	ON_BN_CLICKED(IDC_SAVE, OnSave)
	ON_BN_CLICKED(IDC_UP, OnUp)
	ON_BN_CLICKED(IDC_DOWN, OnDown)
	ON_BN_CLICKED(IDC_DELETE, OnDelete)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CListGenDlg message handlers

BOOL CListGenDlg::OnInitDialog()
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
	SetDlgItemText(IDC_FILTER,"*.mp3");
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CListGenDlg::OnSysCommand(UINT nID, LPARAM lParam)
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

void CListGenDlg::OnPaint() 
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
HCURSOR CListGenDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CListGenDlg::OnStart() 
{
	// TODO: Add your control notification handler code here
	CString path,filter;
	GetDlgItemText(IDC_PATH,path);
	GetDlgItemText(IDC_FILTER,filter);
	if(!m_listInterface.Open(path,filter))
	{
		MessageBox("请选择目录","错误");
		return;
	}
	((CListBox*)GetDlgItem(IDC_PLAYLIST))->ResetContent();
	for(CPlayList::ListIterator it=m_listInterface.begin();
		it!=m_listInterface.end();
		it++)
	{
			((CListBox*)GetDlgItem(IDC_PLAYLIST))->AddString(it->c_str());
	}
	m_opend = true;
}

void CListGenDlg::OnExit() 
{
	// TODO: Add your control notification handler code here
	CDialog::OnCancel();
}

void CListGenDlg::OnBrowse() 
{
	// TODO: Add your control notification handler code here
	::BROWSEINFO bInfo;
	::LPCITEMIDLIST lpItemList;
	char FileName[MAX_PATH];
	memset(FileName,0,sizeof(FileName));
	memset(&bInfo,0,sizeof(bInfo));
	bInfo.hwndOwner = m_hWnd;
	bInfo.lpszTitle = "选择要生成列表的目录";

	lpItemList=SHBrowseForFolder(&bInfo);
	SHGetPathFromIDList(lpItemList,FileName);
	SetDlgItemText(IDC_PATH,FileName);
}

void CListGenDlg::OnSave() 
{
	// TODO: Add your control notification handler code here
	if(!m_opend)
		return;
	CFileDialog tempDlg(FALSE,"*.m3u",0,OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT,"m3u files(*.m3u)|*.m3u|text files(*.txt)|*.txt|All files (*.*)|*.*||",this);
	if(tempDlg.DoModal()!=IDOK)
		return;
	CString filename = tempDlg.GetFileName();
	m_listInterface.Save(filename);
}


void CListGenDlg::OnUp() 
{
	// TODO: Add your control notification handler code here
	
}

void CListGenDlg::OnDown() 
{
	// TODO: Add your control notification handler code here
	
}

void CListGenDlg::OnDelete() 
{
	// TODO: Add your control notification handler code here
	
}
