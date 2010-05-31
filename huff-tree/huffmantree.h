//哈夫曼编码解码之STL实现 
//作者:Jerry
//日期:15-06-06 01:10

#ifndef HUFFMANTREE_H
#define HUFFMANTREE_H
#include "huffmannode.h"
#include <queue>
#include <string>
#include <map>

//命名空间定义 
namespace huffman
{

using namespace std;                

//哈夫曼树类 
template<typename T>
class HuffmanTree
{
    public:
        
        //把这个类需要的各种类型typedef下,代码好看(STL,泛型,妈啊.....:-) 
        typedef map<T,string> CODEMAP;
		typedef HuffmanNode<T> NODE,*PNODE;
		typedef priority_queue<PNODE,vector<PNODE>,typename NODE::Smaller> PQUEUE;
		typedef typename CODEMAP::value_type PAIR;
		typedef typename CODEMAP::iterator ITERATOR;
		
    private:
        
        //哈夫曼树根 
        PNODE root;
        //构造哈夫曼树的优先队列 
        PQUEUE pqNodes;
        //存放哈夫曼编码的MAP 
        CODEMAP huffmanCodes;
        //标志变量,表示树是否建立好 
     	bool treeBuilt;    
     	bool codeCalced;
     	vector<char> code; 
     	//计算哈夫曼编码 
     	void CalcHuffmanCode(PNODE node);
     	//删除树中所有节点 
        void DeleteNodes(PNODE start);
     	//前序,中序,后序遍历,主要用于调试 
        void PreTraverse(std::ostream& out,NODE *r); 
        void MidTraverse(std::ostream& out,NODE *r); 
        void PostTraverse(std::ostream& out,NODE *r);      
        
	public:	    
		
	    HuffmanTree();
	    ~HuffmanTree();
	    //打印所有节点(包括前序,中序,后序遍历序列) 
	    void PrintAllNodes(std::ostream& out);
	    //输入数据,包括数据和对应权值 
	    void PushData(T data,long weight)
	    {
	        if(!treeBuilt)
	        	pqNodes.push(NODE::CreateNode(data,weight));
	    }   
	    //建立哈夫曼树,建立好了就不能再向队列中输入数据了 
 		void BuildHuffmanTree();
   		
   		//返回哈夫曼编码的MAP 
     	CODEMAP& GetCodeMap()
      	{
      	    if(!codeCalced)
      	    	CalcHuffmanCode(root);
           	return huffmanCodes;
        }  
        //解码函数,根据scr解码出对应的数据,存放在数组d中,数组大小由size决定
        //返回值:0,解码失败(比如树没有建立好);若成功,返回有效数据元素个数 
        int Decode(const char* scr,T* d,const int size);
        //删除节点,使树恢复到初始状态(没有数据,没有编码) 
        void Destroy()
        {
            DeleteNodes(root);
            root = NULL;
            treeBuilt=false;
            codeCalced=false;
            huffmanCodes.erase(huffmanCodes.begin(),huffmanCodes.end());
            code.clear();
        }  
};


//构造函数 
template <typename T>
HuffmanTree<T>::HuffmanTree()
:root(NULL),
 treeBuilt(false),
 codeCalced(false)
{
}

template <typename T>
HuffmanTree<T>::~HuffmanTree()
{
    Destroy();
}

template <typename T>
//打印出树中所有节点,用于调试 
#ifdef _DEBUG
void HuffmanTree<T>::PrintAllNodes(std::ostream& out)
{
    if(!treeBuilt)
    	return;
    cout<<"Preorder traverse:"<<endl;
    PreTraverse(out,root);
    cout<<"Midorder traverse:"<<endl;
    MidTraverse(out,root);
    cout<<"Postorder traverse:"<<endl;
    PostTraverse(out,root);
}
#else
void HuffmanTree<T>::PrintAllNodes(std::ostream& out){}
#endif

//建立哈夫曼树 
template <typename T>
void HuffmanTree<T>::BuildHuffmanTree()
{
    if(pqNodes.empty())
    	return;//如果队列为空就直接退出 
    while(pqNodes.size()!=1)
    {
    	//从优先队列中弹出权值最小的两个节点,放在left和right中 
     	PNODE left = pqNodes.top();
    	pqNodes.pop();
    	PNODE right = pqNodes.top();
    	pqNodes.pop();
    	//把它们的权值相加,构造一个新节点 
    	PNODE newNode = NODE::CreateNode(0,(left->Weight() + right->Weight()),left,right);
    	//刚刚弹出的两个节点的父节点就是这个新节点 
    	left->SetParent(newNode);
    	right->SetParent(newNode);
    	//放到队列中
	    pqNodes.push(newNode);
	}      
	root = pqNodes.top();
	pqNodes.pop();
	treeBuilt = true;
} 

template <typename T>
//计算哈夫曼编码,结果保存在MAP中 
void HuffmanTree<T>::CalcHuffmanCode(PNODE node)
{ 
    //本质是先序遍历,也是很笨的方法(以后写出一个不用递归,不用栈的版本来)    
    if((!treeBuilt) || (node==NULL))
    	return;
	codeCalced = true;
	if(node->LeftChild()==NULL && node->RightChild()==NULL)
	{
	    huffmanCodes.insert( PAIR(node->Data(),string(code.begin(),code.end())) );
	}
	//左为0 
 	code.push_back('0');
    CalcHuffmanCode(node->LeftChild());
    code.pop_back();
    //右为1 
    code.push_back('1');
    CalcHuffmanCode(node->RightChild());
    code.pop_back();  	    
} 

template <typename T>
//解码函数,根据scr解码出对应的数据,存放在数组d中,数组大小由size决定
//返回值:0,解码失败(比如树没有建立好);若成功,返回有效数据元素个数 
int HuffmanTree<T>::Decode(const char* scr,T* d,const int size)
{
    if(!treeBuilt)
    	return 0;
    PNODE currentNode = root;
    const char* temp = scr;
    int pos = 0;
    while(*temp)
    {
        //0,往左走	
        if(*temp == '0' && currentNode->LeftChild()!=NULL)
        {
            currentNode = currentNode->LeftChild();
            temp++;
        }
        //1,往右走 
        else if(*temp == '1' && currentNode->RightChild()!=NULL)
        {
            currentNode = currentNode->RightChild();
            temp++;
        }
        else
        	temp++;
        //如果是叶子节点就从栈中取出对应的编码,并从根重新开始解码	
        if(currentNode->LeftChild()==NULL && currentNode->RightChild()==NULL)
        {
            d[pos] = currentNode->Data();
            pos++;
            //确保不会出现溢出           
            if(pos>=size)
            	return size;
        	currentNode = root;
     	}   
        
    }
    return pos;                    	
}         

template <typename T>
//用后序遍历来删除所有节点(其实是相当笨的方法,可是我想不出更好的了) 
void HuffmanTree<T>::DeleteNodes(PNODE node)
{
    if(node == NULL)
    	return;
	DeleteNodes(node->LeftChild());
	DeleteNodes(node->RightChild());
	delete node;
}	

//三种遍历,不用我废话了吧?:-) 
template <typename T>
void HuffmanTree<T>::PreTraverse(std::ostream& out,PNODE r)
{
    if(r == NULL)
    	return;
	out<<"\t"<<*r<<endl;
	PreTraverse(out,r->LeftChild());
	PreTraverse(out,r->RightChild());	
}     

template <typename T>
void HuffmanTree<T>::MidTraverse(std::ostream& out,PNODE r)
{
    if(r == NULL)
    	return;
	MidTraverse(out,r->LeftChild());
	out<<"\t"<<*r<<endl;	
	MidTraverse(out,r->RightChild());	
}  

template <typename T>
void HuffmanTree<T>::PostTraverse(std::ostream& out,PNODE r)
{
    if(r == NULL)
    	return;
	PostTraverse(out,r->LeftChild());
	PostTraverse(out,r->RightChild());
	out<<"\t"<<*r<<endl;		
}  

};//namespace huffman

#endif // HUFFMANTREE_H

