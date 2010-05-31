// OptionDlg.cpp : implementation file
//

#include "stdafx.h"
#include "autosave.h"
#include "OptionDlg.h"
#include "FileSaver.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// COptionDlg dialog


COptionDlg::COptionDlg(CWnd* pParent /*=NULL*/)
	: CDialog(COptionDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(COptionDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
}

void COptionDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(COptionDlg)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(COptionDlg, CDialog)
	//{{AFX_MSG_MAP(COptionDlg)
	ON_BN_CLICKED(IDC_BROWSE, OnBrowse)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// COptionDlg message handlers

void COptionDlg::OnBrowse() 
{
	// TODO: Add your control notification handler code here
	::BROWSEINFO bInfo;
	::LPCITEMIDLIST lpItemList;
	char FileName[MAX_PATH];
	memset(FileName,0,sizeof(FileName));
	memset(&bInfo,0,sizeof(bInfo));
	bInfo.hwndOwner = m_hWnd;
	bInfo.lpszTitle = "选择要存放文件的目录";
	bInfo.ulFlags = BIF_RETURNONLYFSDIRS;

	lpItemList=SHBrowseForFolder(&bInfo);
	if(!lpItemList)
		return;
	SHGetPathFromIDList(lpItemList,FileName);
	SetDlgItemText(IDC_DIR,FileName);
}

BOOL COptionDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	// TODO: Add extra initialization here
	SetDlgItemText(IDC_DIR,FILESAVER->GetFileDir());
	SetDlgItemText(IDC_NAME,FILESAVER->GetBaseName());
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void COptionDlg::OnOK() 
{
	// TODO: Add extra validation here
	CString tempDir,tempName;
	GetDlgItemText(IDC_DIR,tempDir);
	GetDlgItemText(IDC_NAME,tempName);
	if(!CFileSaver::CheckName(tempName))
	{
		MessageBox("文件名里不能包含\\/<>\"?*|:","是吗?");
	}
	else
	{
		FILESAVER->SetFileDir(tempDir);
		FILESAVER->SetBaseName(tempName);
		CDialog::OnOK();
	}
	//CDialog::OnOK();
}

void COptionDlg::OnCancel() 
{
	// TODO: Add extra cleanup here
	
	CDialog::OnCancel();
}

