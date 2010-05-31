//�������������֮STLʵ�� 
//����:Jerry
//����:15-06-06 01:10

#ifndef HUFFMANTREE_H
#define HUFFMANTREE_H
#include "huffmannode.h"
#include <queue>
#include <string>
#include <map>

//�����ռ䶨�� 
namespace huffman
{

using namespace std;                

//���������� 
template<typename T>
class HuffmanTree
{
    public:
        
        //���������Ҫ�ĸ�������typedef��,����ÿ�(STL,����,�谡.....:-) 
        typedef map<T,string> CODEMAP;
		typedef HuffmanNode<T> NODE,*PNODE;
		typedef priority_queue<PNODE,vector<PNODE>,typename NODE::Smaller> PQUEUE;
		typedef typename CODEMAP::value_type PAIR;
		typedef typename CODEMAP::iterator ITERATOR;
		
    private:
        
        //���������� 
        PNODE root;
        //����������������ȶ��� 
        PQUEUE pqNodes;
        //��Ź����������MAP 
        CODEMAP huffmanCodes;
        //��־����,��ʾ���Ƿ����� 
     	bool treeBuilt;    
     	bool codeCalced;
     	vector<char> code; 
     	//������������� 
     	void CalcHuffmanCode(PNODE node);
     	//ɾ���������нڵ� 
        void DeleteNodes(PNODE start);
     	//ǰ��,����,�������,��Ҫ���ڵ��� 
        void PreTraverse(std::ostream& out,NODE *r); 
        void MidTraverse(std::ostream& out,NODE *r); 
        void PostTraverse(std::ostream& out,NODE *r);      
        
	public:	    
		
	    HuffmanTree();
	    ~HuffmanTree();
	    //��ӡ���нڵ�(����ǰ��,����,�����������) 
	    void PrintAllNodes(std::ostream& out);
	    //��������,�������ݺͶ�ӦȨֵ 
	    void PushData(T data,long weight)
	    {
	        if(!treeBuilt)
	        	pqNodes.push(NODE::CreateNode(data,weight));
	    }   
	    //������������,�������˾Ͳ���������������������� 
 		void BuildHuffmanTree();
   		
   		//���ع����������MAP 
     	CODEMAP& GetCodeMap()
      	{
      	    if(!codeCalced)
      	    	CalcHuffmanCode(root);
           	return huffmanCodes;
        }  
        //���뺯��,����scr�������Ӧ������,���������d��,�����С��size����
        //����ֵ:0,����ʧ��(������û�н�����);���ɹ�,������Ч����Ԫ�ظ��� 
        int Decode(const char* scr,T* d,const int size);
        //ɾ���ڵ�,ʹ���ָ�����ʼ״̬(û������,û�б���) 
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


//���캯�� 
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
//��ӡ���������нڵ�,���ڵ��� 
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

//������������ 
template <typename T>
void HuffmanTree<T>::BuildHuffmanTree()
{
    if(pqNodes.empty())
    	return;//�������Ϊ�վ�ֱ���˳� 
    while(pqNodes.size()!=1)
    {
    	//�����ȶ����е���Ȩֵ��С�������ڵ�,����left��right�� 
     	PNODE left = pqNodes.top();
    	pqNodes.pop();
    	PNODE right = pqNodes.top();
    	pqNodes.pop();
    	//�����ǵ�Ȩֵ���,����һ���½ڵ� 
    	PNODE newNode = NODE::CreateNode(0,(left->Weight() + right->Weight()),left,right);
    	//�ոյ����������ڵ�ĸ��ڵ��������½ڵ� 
    	left->SetParent(newNode);
    	right->SetParent(newNode);
    	//�ŵ�������
	    pqNodes.push(newNode);
	}      
	root = pqNodes.top();
	pqNodes.pop();
	treeBuilt = true;
} 

template <typename T>
//�������������,���������MAP�� 
void HuffmanTree<T>::CalcHuffmanCode(PNODE node)
{ 
    //�������������,Ҳ�Ǻܱ��ķ���(�Ժ�д��һ�����õݹ�,����ջ�İ汾��)    
    if((!treeBuilt) || (node==NULL))
    	return;
	codeCalced = true;
	if(node->LeftChild()==NULL && node->RightChild()==NULL)
	{
	    huffmanCodes.insert( PAIR(node->Data(),string(code.begin(),code.end())) );
	}
	//��Ϊ0 
 	code.push_back('0');
    CalcHuffmanCode(node->LeftChild());
    code.pop_back();
    //��Ϊ1 
    code.push_back('1');
    CalcHuffmanCode(node->RightChild());
    code.pop_back();  	    
} 

template <typename T>
//���뺯��,����scr�������Ӧ������,���������d��,�����С��size����
//����ֵ:0,����ʧ��(������û�н�����);���ɹ�,������Ч����Ԫ�ظ��� 
int HuffmanTree<T>::Decode(const char* scr,T* d,const int size)
{
    if(!treeBuilt)
    	return 0;
    PNODE currentNode = root;
    const char* temp = scr;
    int pos = 0;
    while(*temp)
    {
        //0,������	
        if(*temp == '0' && currentNode->LeftChild()!=NULL)
        {
            currentNode = currentNode->LeftChild();
            temp++;
        }
        //1,������ 
        else if(*temp == '1' && currentNode->RightChild()!=NULL)
        {
            currentNode = currentNode->RightChild();
            temp++;
        }
        else
        	temp++;
        //�����Ҷ�ӽڵ�ʹ�ջ��ȡ����Ӧ�ı���,���Ӹ����¿�ʼ����	
        if(currentNode->LeftChild()==NULL && currentNode->RightChild()==NULL)
        {
            d[pos] = currentNode->Data();
            pos++;
            //ȷ������������           
            if(pos>=size)
            	return size;
        	currentNode = root;
     	}   
        
    }
    return pos;                    	
}         

template <typename T>
//�ú��������ɾ�����нڵ�(��ʵ���൱���ķ���,�������벻�����õ���) 
void HuffmanTree<T>::DeleteNodes(PNODE node)
{
    if(node == NULL)
    	return;
	DeleteNodes(node->LeftChild());
	DeleteNodes(node->RightChild());
	delete node;
}	

//���ֱ���,�����ҷϻ��˰�?:-) 
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

