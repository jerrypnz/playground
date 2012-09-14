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


//CFileSaver是一个独体类,用来实现程序的主要功能模块,
//即以一定的命名方式保存剪贴板里的文本

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
	//这是这个类的唯一实例
	static CFileSaver m_theInstance;	
	//这里把构造函数声明为私有,
	//所以不允许用户自己定义对象
	CFileSaver();
	bool GetData();
	int m_nameNumber;
	CString m_baseName;
	CString m_fileDir;
	CString m_data;
	//bool m_bNewDataArrived;
};

#endif // !defined(AFX_FILESAVER_H__4F67F4A8_F42A_40CE_B5E3_03135BBADF3F__INCLUDED_)
