// PuzzleDlg.cpp : implementation file
//

#include "stdafx.h"
#include "Puzzle.h"
#include "PuzzleDlg.h"
#include <stdlib.h>
#include <time.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

const int PART_WIDTH = 42;
const int PART_HEIGHT = 42;
const int POS_MATRIX[6][2] = { {0,0}, {42,0}, {84,0}, {0,42}, {42,42}, {84,42} };

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
// CPuzzleDlg dialog

CPuzzleDlg::CPuzzleDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CPuzzleDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CPuzzleDlg)
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CPuzzleDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CPuzzleDlg)
	DDX_Control(pDX, IDC_DEST_IMAGE, m_destImage);
	DDX_Control(pDX, IDC_SRC_IMAGE, m_srcImage);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CPuzzleDlg, CDialog)
	//{{AFX_MSG_MAP(CPuzzleDlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_SPLIT, OnSplit)
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()
	ON_BN_CLICKED(IDC_RESTORE, OnRestore)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CPuzzleDlg message handlers

BOOL CPuzzleDlg::OnInitDialog()
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
	int yOffset;
	CRect dlgClient,dlgWindow;
	this->GetClientRect(&dlgClient);
	this->GetWindowRect(&dlgWindow);
	yOffset = dlgWindow.Height() - dlgClient.Height();

	CRect destRect;
	m_destImage.GetWindowRect(&destRect);
	destX = destRect.left;
	destY = destRect.top - yOffset;
	for(int i=0;i<6;i++)
	{
		which[i] = i;
		CStatic *pPart = (CStatic*)GetDlgItem(i+IDC_PART1);
		pPart->GetWindowRect(partRect + i);
		partRect[i].top -= yOffset;
		partRect[i].bottom -= yOffset;
		/**CString msg;
		msg.Format("%d,%d,%d,%d",partRect[i].left,partRect[i].top,partRect[i].Width(),partRect[i].Height());
		MessageBox(msg);**/
	}
	movingCursor = ::LoadCursor(::AfxGetInstanceHandle(),MAKEINTRESOURCE(IDC_MOVING_CURSOR));
	isDragging = false;
	gameStarted = false;
	// TODO: Add extra initialization here
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CPuzzleDlg::OnSysCommand(UINT nID, LPARAM lParam)
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

void CPuzzleDlg::OnPaint() 
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
HCURSOR CPuzzleDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}


void CPuzzleDlg::OnSplit() 
{
	InitGame();
	gameStarted = true;
}

void CPuzzleDlg::OnRestore() 
{
	gameStarted = false;
	this->RedrawWindow();
	DrawGrid();
}


void CPuzzleDlg::InitGame()
{
	time_t t;
	srand((unsigned)time(&t));
	for(int n=0;n<6;n++)
	{
		int one = n;
		int two = rand()%6;
		int temp = which[one];
		which[one] = which[two];
		which[two] = temp;
	}
	CRect destRect;
	m_destImage.GetClientRect(&destRect);
	CDC* pSrcDC = m_srcImage.GetDC();
	int x = 0;
	int y = 0;
	int flag = 0;
	for(int i=0;i<6;i++)
	{
		CStatic *pTemp = (CStatic*)GetDlgItem(i+IDC_PART1);
		CDC *pDestDC = pTemp->GetDC();
		x = POS_MATRIX[ which[i] ][0];
		y = POS_MATRIX[ which[i] ][1];
		pDestDC->BitBlt(0,0,PART_WIDTH,PART_HEIGHT,pSrcDC,x,y,SRCCOPY);
	}
	DrawGrid();
	nPartFinished = 0;

	
}

void CPuzzleDlg::OnLButtonDown(UINT nFlags, CPoint point) 
{
	//CString msg;
	//msg.Format("%d,%d",point.x,point.y);
	//MessageBox(msg);
	if(!gameStarted)
		return;
	for(int i=0;i<6;i++)
	{
		if(partRect[i].PtInRect(point))
		{
			isDragging = true;
			currentPart = i;
			::SetCursor(movingCursor);
			currentDest.left = destX + POS_MATRIX[ which[i] ][0];
			currentDest.top = destY + POS_MATRIX[ which[i] ][1];
			currentDest.right = currentDest.left + PART_WIDTH;
			currentDest.bottom = currentDest.top + PART_HEIGHT;
			break;
		}
	}
}

void CPuzzleDlg::OnLButtonUp(UINT nFlags, CPoint point) 
{
	isDragging = false;
	if(currentDest.PtInRect(point))
	{
		CDC *pDest = m_destImage.GetDC();
		CStatic *pPart = (CStatic*)GetDlgItem(currentPart+IDC_PART1);
		CDC *pSrc = pPart->GetDC();
		int x = POS_MATRIX[ which[currentPart] ][0];
		int y = POS_MATRIX[ which[currentPart] ][1];
		pDest->BitBlt(x+3,y+3,PART_WIDTH,PART_HEIGHT,pSrc,0,0,SRCCOPY);
		CRect temp;
		pPart->GetClientRect(&temp);
		ClearRect(pSrc,&temp);
		nPartFinished++;
		if(nPartFinished == 6)
			MessageBox("恭喜，你完成拼图了！","游戏结束",MB_OK|MB_ICONINFORMATION);
	}
}

void CPuzzleDlg::OnMouseMove(UINT nFlags, CPoint point) 
{
	if(isDragging)
		::SetCursor(movingCursor);
}

void CPuzzleDlg::ClearRect(CDC *pDC, const CRect *rect)
{
	CGdiObject* pOldPen = pDC->SelectStockObject(NULL_PEN);
	pDC->Rectangle(rect);
	pDC->SelectObject(pOldPen);
}

void CPuzzleDlg::DrawGrid()
{
	CDC* pDC = m_destImage.GetDC();
	CPen redPen;
	redPen.CreatePen(PS_SOLID,0,0x0000FF);
	CPen* pOldPen = pDC->SelectObject(&redPen);
	pDC->MoveTo(3,45);
	pDC->LineTo(129,45);
	pDC->MoveTo(45,3);
	pDC->LineTo(45,87);
	pDC->MoveTo(87,3);
	pDC->LineTo(87,87);
	pDC->SelectObject(pOldPen);
}
