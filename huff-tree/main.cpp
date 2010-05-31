#include <iostream>
#include <map>
#include <string>
#include <cstdlib>
#include <cstring>
#include "huffmantree.h"

using namespace std;
//����������huffman�����ռ���,Ҫʹ�õĻ���Ҫ������һ�� 
using namespace huffman;

typedef HuffmanTree<char> MyTree;

int main(int argc, char *argv[])
{
    MyTree tempTree;
    char buffer[100];
    char tempData='#';
    long tempWeight; 
    cout<<"���������������Գ���"<<endl;
    cout<<"����:Jerry"<<endl;
    cout<<endl<<endl; 
    cout<<"������һϵ�е��ַ��Ͷ�Ӧ��Ȩֵ:(0����)"<<endl; 
    while(tempData!='0')
    {
        cout<<"�ַ�:";
        cin>>tempData;
        cout<<"Ȩֵ:";
        cin>>tempWeight;
        if(tempData!='0')
        	tempTree.PushData(tempData,tempWeight);
     } 
     cout<<"���ڽ�����������..."<<endl;    
    tempTree.BuildHuffmanTree();
    //tempTree.PrintAllNodes(cout);
    cout<<"���ڱ���..."<<endl; 
    MyTree::CODEMAP& temp = tempTree.GetCodeMap();
    cout<<"��������������:"<<endl;
    string sq;
    for(MyTree::ITERATOR it = temp.begin();it!=temp.end();it++)
    {
        cout<<it->first<<":"<<it->second<<endl;
       sq += it->second;
    }    
    int pos = tempTree.Decode(sq.c_str(),buffer,100);
    cout<<"�������:"<<endl;
    cout<<"����������:"<<sq<<endl;
    cout<<"�����:";
    for(int i=0;i<pos;i++)
    	cout<<buffer[i];
	cout<<"\nԭ���ݳ���:"<<strlen(buffer)<<endl;
 	cout<<"��������ݳ���:"<<sq.length()/8+1<<endl; 
	cout<<endl;
    system("PAUSE");
    return 0;
}