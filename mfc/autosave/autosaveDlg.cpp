// autosaveDlg.cpp : implementation file
//

#include "stdafx.h"
#include "autosave.h"
#include "autosaveDlg.h"
#include "OptionDlg.h"
#include "FileSaver.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define IDH_HOTKEY 0x000F
#define KEY_S 'S'

/////////////////////////////////////////////////////////////////////////////
// CAutosaveDlg dialog

CAutosaveDlg::CAutosaveDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CAutosaveDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CAutosaveDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDI_MAIN);
}

void CAutosaveDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAutosaveDlg)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CAutosaveDlg, CDialog)
	//{{AFX_MSG_MAP(CAutosaveDlg)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_EXIT, OnExit)
	ON_WM_LBUTTONDOWN()
	ON_BN_CLICKED(IDC_OPTION, OnOption)
	ON_BN_CLICKED(IDC_SAVE, OnSave)
	ON_BN_CLICKED(IDC_CLEAR, OnClear)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CAutosaveDlg message handlers

BOOL CAutosaveDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	SetWindowPos(&wndTopMost,0,0,0,0, SWP_NOMOVE | SWP_NOSIZE);
	
	//注册全局的快捷键
	::RegisterHotKey(m_hWnd,IDH_HOTKEY,MOD_ALT,KEY_S);
	
	// TODO: Add extra initialization here
	//SetDlgItemText(IDC_STATIC,FILESAVER->GetFileDir());
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CAutosaveDlg::OnPaint() 
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
HCURSOR CAutosaveDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CAutosaveDlg::OnExit() 
{
	// TODO: Add your control notification handler code here
	CDialog::OnOK();
	
}

void CAutosaveDlg::OnLButtonDown(UINT nFlags, CPoint point) 
{
	// TODO: Add your message handler code here and/or call default
	CDialog::OnLButtonDown(nFlags, point);  
	// 发送WM_NCLBUTTONDOWN消息  
	// 使Windows认为鼠标在标题条上  
	PostMessage(WM_NCLBUTTONDOWN, HTCAPTION, MAKELPARAM(point.x, point.y));  
}

void CAutosaveDlg::OnOption() 
{
	// TODO: Add your control notification handler code here
	COptionDlg optDlg;
	optDlg.DoModal();
}

void CAutosaveDlg::OnSave() 
{
	FILESAVER->SaveFile();
	//MessageBox("Saved");
}

/**************重构前的代码***************************************************************
bool CAutosaveDlg::GetData()
{
	if(!::OpenClipboard(NULL))
	{
		TRACE0("Cannot open clipboard!");
		return false;
	}
	HGLOBAL hClip = ::GetClipboardData(CF_TEXT);
	if(hClip == NULL)
	{
		return false;
	}
	char *data = (char*)::GlobalLock(hClip);
	m_data.Format("%s",data);
	::GlobalUnlock(hClip);
	::CloseClipboard();
	return true;
}

bool CAutosaveDlg::SaveToFile()
{
	if(m_data.IsEmpty())
		return false;
	int nameSize = 2;
	if( (nameSize = m_data.GetLength() ) > 16)
		nameSize = 16;
	CString temp = m_data.Left(nameSize);
	CString filename;
	filename.Format("%s%s%d.txt",m_dir,m_baseName,GetFullName());
	CFile theFile;
	theFile.Open(filename,OF_CREATE | OF_WRITE);
	theFile.Write((LPCTSTR)m_data,m_data.GetLength());
	theFile.Close();
	return true;
}

int CAutosaveDlg::GetFullName()
{
	static int num = 1;
	return num++;
}
************************************************************************************/

void CAutosaveDlg::OnClear() 
{
	// TODO: Add your control notification handler code here
	if(!FILESAVER->ClearClipboard())
		MessageBox("清空剪贴板失败","不是我的错");
	
}

BOOL CAutosaveDlg::PreTranslateMessage(MSG* pMsg) 
{
	// TODO: Add your specialized code here and/or call the base class
	switch(pMsg->message)
	{
		case WM_HOTKEY:
			if(pMsg->wParam == IDH_HOTKEY)
			{
				//MessageBox("HotKey pressed","Yes,it did");
				OnSave();
			}
			break;

		default:
			break;
	}
	return CDialog::PreTranslateMessage(pMsg);
}
