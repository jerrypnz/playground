#include <iostream>
#include <map>
#include <string>
#include <cstdlib>
#include <cstring>
#include "huffmantree.h"

using namespace std;
//哈夫曼树在huffman命名空间里,要使用的话就要加上这一句 
using namespace huffman;

typedef HuffmanTree<char> MyTree;

int main(int argc, char *argv[])
{
    MyTree tempTree;
    char buffer[100];
    char tempData='#';
    long tempWeight; 
    cout<<"哈夫曼编码解码测试程序"<<endl;
    cout<<"作者:Jerry"<<endl;
    cout<<endl<<endl; 
    cout<<"请输入一系列的字符和对应的权值:(0结束)"<<endl; 
    while(tempData!='0')
    {
        cout<<"字符:";
        cin>>tempData;
        cout<<"权值:";
        cin>>tempWeight;
        if(tempData!='0')
        	tempTree.PushData(tempData,tempWeight);
     } 
     cout<<"正在建立哈夫曼树..."<<endl;    
    tempTree.BuildHuffmanTree();
    //tempTree.PrintAllNodes(cout);
    cout<<"正在编码..."<<endl; 
    MyTree::CODEMAP& temp = tempTree.GetCodeMap();
    cout<<"哈夫曼编码如下:"<<endl;
    string sq;
    for(MyTree::ITERATOR it = temp.begin();it!=temp.end();it++)
    {
        cout<<it->first<<":"<<it->second<<endl;
       sq += it->second;
    }    
    int pos = tempTree.Decode(sq.c_str(),buffer,100);
    cout<<"解码测试:"<<endl;
    cout<<"待解码数据:"<<sq<<endl;
    cout<<"解码后:";
    for(int i=0;i<pos;i++)
    	cout<<buffer[i];
	cout<<"\n原数据长度:"<<strlen(buffer)<<endl;
 	cout<<"编码后数据长度:"<<sq.length()/8+1<<endl; 
	cout<<endl;
    system("PAUSE");
    return 0;
}