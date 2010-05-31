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

//���캯����������������private

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

//�������Ķ���
CFileSaver CFileSaver::m_theInstance;

CString CFileSaver::GetBaseName()
{
	return m_baseName;
}

//���Ŀǰ���ļ��洢·��
CString CFileSaver::GetFileDir()
{
	return m_fileDir;
}

//�õ����Ψһʵ��
CFileSaver* CFileSaver::GetInstance()
{
	return &m_theInstance;
}

//�����ļ���(����ļ����������������յ��ļ���)
void CFileSaver::SetBaseName(const CString& newName)
{
	if(CheckName(newName) )
	{
		if(newName != m_baseName)
			m_nameNumber = 1; 
		m_baseName = newName;
	}
}

// ���û��Ŀǰ���ļ��洢·��
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

//����ļ����Ƿ�Ϸ��ĺ���,�ļ������ܰ���\/*?:"|���ַ�
bool CFileSaver::CheckName(const CString& name)
{
	if( (name.FindOneOf("\"\\/*|<>?:")<0) || name.IsEmpty())
		return true;
	else
		return false;
}

//��ȡ����������ı�����
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
