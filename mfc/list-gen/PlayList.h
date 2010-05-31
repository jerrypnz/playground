// PlayList.h: interface for the CPlayList class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PLAYLIST_H__0A0A2166_C404_4E14_9277_E868196DB308__INCLUDED_)
#define AFX_PLAYLIST_H__0A0A2166_C404_4E14_9277_E868196DB308__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <vector>
#include <string>


class  CPlayList  
{
public:


	typedef std::vector<std::string>::iterator  ListIterator;

	ListIterator begin()
	{
		return m_list.begin();
	}

	ListIterator end()
	{
		return m_list.end();
	}

	bool Save(const char* filename);
	bool Open(const char* path,const char* filter);


	CPlayList()
	{
	}

	CPlayList(const char* path,const char* filter)
		:m_path(path),m_filter(filter)
	{
		if( *(m_path.end()-1) != '\\')
			m_path+='\\';
		SearchFiles();
	}
	virtual ~CPlayList();

	

private:
	bool SearchFiles();
	
	std::vector<std::string>	m_list;
	std::string				m_path;
	std::string				m_filter;
};

#endif // !defined(AFX_PLAYLIST_H__0A0A2166_C404_4E14_9277_E868196DB308__INCLUDED_)
