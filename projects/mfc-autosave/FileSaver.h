// FileSaver.h: interface for the CFileSaver class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_FILESAVER_H__4F67F4A8_F42A_40CE_B5E3_03135BBADF3F__INCLUDED_)
#define AFX_FILESAVER_H__4F67F4A8_F42A_40CE_B5E3_03135BBADF3F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#define DEFAULT_DIR   "C:\\"
#define DEFAULT_NAME  "MyFile"

#define FILESAVER (CFileSaver::GetInstance())


//CFileSaver��һ��������,����ʵ�ֳ������Ҫ����ģ��,
//����һ����������ʽ�������������ı�

class CFileSaver  
{
public:
	
	enum
	{
		STATUS_NORMAL,
		STATUS_NODATA_ERROR,
		STATUS_CLIPBOARD_ERROR
	};
	
	virtual ~CFileSaver();

	static CFileSaver* GetInstance();
	static bool CheckName(const CString&);

	CString GetBaseName();
	CString GetFileDir();
	void SetFileDir(const CString& newDir);
	void SetBaseName(const CString& newName);
	void SaveFile();
	int GetStatus();
	bool ClearClipboard();

private:	
	int m_status;
	//����������Ψһʵ��
	static CFileSaver m_theInstance;	
	//����ѹ��캯������Ϊ˽��,
	//���Բ������û��Լ��������
	CFileSaver();
	bool GetData();
	int m_nameNumber;
	CString m_baseName;
	CString m_fileDir;
	CString m_data;
	//bool m_bNewDataArrived;
};

#endif // !defined(AFX_FILESAVER_H__4F67F4A8_F42A_40CE_B5E3_03135BBADF3F__INCLUDED_)
