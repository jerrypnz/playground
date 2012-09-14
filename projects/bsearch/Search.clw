; CLW file contains information for the MFC ClassWizard

[General Info]
Version=1
LastClass=CSearchDlg
LastTemplate=CDialog
NewFileInclude1=#include "stdafx.h"
NewFileInclude2=#include "Search.h"

ClassCount=3
Class1=CSearchApp
Class2=CSearchDlg
Class3=CAboutDlg

ResourceCount=3
Resource1=IDD_ABOUTBOX
Resource2=IDR_MAINFRAME
Resource3=IDD_SEARCH_DIALOG

[CLS:CSearchApp]
Type=0
HeaderFile=Search.h
ImplementationFile=Search.cpp
Filter=N

[CLS:CSearchDlg]
Type=0
HeaderFile=SearchDlg.h
ImplementationFile=SearchDlg.cpp
Filter=D
BaseClass=CDialog
VirtualFilter=dWC
LastObject=CSearchDlg

[CLS:CAboutDlg]
Type=0
HeaderFile=SearchDlg.h
ImplementationFile=SearchDlg.cpp
Filter=D

[DLG:IDD_ABOUTBOX]
Type=1
Class=CAboutDlg
ControlCount=4
Control1=IDC_STATIC,static,1342177283
Control2=IDC_STATIC,static,1342308480
Control3=IDC_STATIC,static,1342308352
Control4=IDOK,button,1342373889

[DLG:IDD_SEARCH_DIALOG]
Type=1
Class=CSearchDlg
ControlCount=6
Control1=IDC_STATIC,button,1342177287
Control2=IDC_STATIC,button,1342177287
Control3=IDC_NUM,edit,1350639744
Control4=IDC_SEARCH,button,1342242816
Control5=IDC_OK,button,1342242816
Control6=IDC_DATA_LIST,SysListView32,1350631425

