// PlayList.cpp: implementation of the CPlayList class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "PlayList.h"
#include <fstream>

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

using namespace std;


CPlayList::~CPlayList()
{

}

bool CPlayList::Open(const char *path, const char *filter)
{
	m_path	=	path;
	m_filter=	filter;
	if(m_path.empty() || m_filter.empty())
		return false;
	if( *(m_path.end()-1) != '\\')
		m_path+='\\';
	return SearchFiles();
}

bool CPlayList::Save(const char *filename)
{
	ofstream ofs(filename);
	if(!ofs)
		return false;
	for(ListIterator it=begin();it!=end();it++)
		ofs<<*it<<endl;
	return true;
	ofs.close();
}

bool CPlayList::SearchFiles()
{
	HANDLE hFind;
	WIN32_FIND_DATA fileInfo;
	
	string filename = m_path+m_filter;
	m_list.erase(m_list.begin(),m_list.end());
	hFind = FindFirstFile(filename.c_str(),&fileInfo);
	if(hFind == INVALID_HANDLE_VALUE)
		return false;
	if( !(fileInfo.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY))
		m_list.push_back(m_path+fileInfo.cFileName);
	while(FindNextFile(hFind,&fileInfo) )
	{
		if(fileInfo.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY)
			continue;
		m_list.push_back(m_path+fileInfo.cFileName);
	}

	return true;
}
