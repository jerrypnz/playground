// FileSaver.cpp: implementation of the CFileSaver class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "autosave.h"
#include "FileSaver.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

//构造函数和析构函数都是private

CFileSaver::CFileSaver()
	:m_fileDir(DEFAULT_DIR),
	 m_baseName(DEFAULT_NAME),
	 m_nameNumber(1),
	 m_status(STATUS_NODATA_ERROR)
	 //m_bNewDataArrived(true)
{

}

CFileSaver::~CFileSaver()
{

}

//独体对象的定义
CFileSaver CFileSaver::m_theInstance;

CString CFileSaver::GetBaseName()
{
	return m_baseName;
}

//获得目前的文件存储路径
CString CFileSaver::GetFileDir()
{
	return m_fileDir;
}

//得到类的唯一实例
CFileSaver* CFileSaver::GetInstance()
{
	return &m_theInstance;
}

//设置文件名(这个文件名加上数字是最终的文件名)
void CFileSaver::SetBaseName(const CString& newName)
{
	if(CheckName(newName) )
	{
		if(newName != m_baseName)
			m_nameNumber = 1; 
		m_baseName = newName;
	}
}

// 设置获得目前的文件存储路径
void CFileSaver::SetFileDir(const CString& newDir)
{
	m_fileDir = newDir;
	if(m_fileDir.Right(1)!="\\")
		m_fileDir+='\\';
}

void CFileSaver::SaveFile()
{
	GetData();
	if(m_data.IsEmpty())
		return;
	/*
	int nameSize = 2;
	if( (nameSize = m_data.GetLength() ) > 16)
		nameSize = 16;
	CString temp = m_data.Left(nameSize);
	*/
	CString filename;
	filename.Format("%s%s%d.txt",m_fileDir,m_baseName,m_nameNumber++);
	CFile theFile;
	theFile.Open(filename,OF_CREATE | OF_WRITE);
	theFile.Write((LPCTSTR)m_data,m_data.GetLength());
	theFile.Close();
}

//检查文件名是否合法的函数,文件名不能包含\/*?:"|等字符
bool CFileSaver::CheckName(const CString& name)
{
	if( (name.FindOneOf("\"\\/*|<>?:")<0) || name.IsEmpty())
		return true;
	else
		return false;
}

//获取剪贴板里的文本数据
bool CFileSaver::GetData()
{
	if(!::OpenClipboard(NULL))
	{
		TRACE0("Cannot open clipboard!");
		m_status = STATUS_CLIPBOARD_ERROR;
		return false;
	}
	HGLOBAL hClip = ::GetClipboardData(CF_TEXT);
	if(hClip == NULL)
	{
		m_status = STATUS_NODATA_ERROR;
		m_data.Empty();
		return false;
	}
	char *data = (char*)::GlobalLock(hClip);
	m_data = data;
	::GlobalUnlock(hClip);
	::CloseClipboard();
	m_status = STATUS_NORMAL;
	return true;
}

int CFileSaver::GetStatus()
{
	return m_status;
}

bool CFileSaver::ClearClipboard()
{
	::OpenClipboard(NULL);
	bool result = (::EmptyClipboard() == TRUE);
	::CloseClipboard();
	return result;
}
