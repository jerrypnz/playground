//哈夫曼编码解码之STL实现 
//作者:Jerry
//日期:15-06-06 01:10
 

#ifndef HUFFMANNODE_H
#define HUFFMANNODE_H
#include <iostream>

//命名空间定义 
namespace huffman
{

//哈夫曼树节点类 
template <typename T>
class HuffmanNode
{
    private:
        //左右孩子指针 
        HuffmanNode *_leftChild,*_rightChild;
        HuffmanNode *_parent;
        //权值 
        long _weight;
        //数据(模板化了) 
        T _data;
        
        //构造函数声明为私有,所以不能直接构造对象
        //比如HuffmanNode<char> fooNode;这样的定义就不能通过编译 
        //要得到一个节点,必须通过静态成员函数CreateNode 
        HuffmanNode(T data,long weight,HuffmanNode* left,HuffmanNode* right,HuffmanNode *parent)
		:_data(data),
         _weight(weight),
		 _leftChild(left),
		 _rightChild(right),
		 _parent(parent)
		 {
		 } 
      
		HuffmanNode(T data,long weight)
		:_data(data),
         _weight(weight),
		 _leftChild(NULL),
		 _rightChild(NULL),
		 _parent(NULL)
		 {
		 }    
        
	public:
	    
	    //这个内部类只有一个重载了的()运算符,用在优先队列中,用于比较两个节点 
	    class Smaller
	    {
	        public:
	        	bool operator()(const HuffmanNode* node1,const HuffmanNode* node2)
	        	{
	            	return (node1->_weight) > (node2->_weight);
	            }
         };   
         
        //这是唯一的创建对象的接口,它确保了对象创建在堆上,方便管理  
        static HuffmanNode* CreateNode(T data,long weight,HuffmanNode* left=NULL,HuffmanNode* right=NULL,HuffmanNode* parent=NULL)
        {
            return new HuffmanNode(data,weight,left,right,parent);
        }       
		
		//一系列的getter和setter成员函数(怎么那么像Java?:-)) 
		void SetLeftChild(HuffmanNode *left)
		{
		    _leftChild = left;
		}    
		
		void SetRightChild(HuffmanNode *right)
		{
		    _rightChild = right;
		}    
		
		void SetParent(HuffmanNode* parent)
		{
		    _parent = parent;
		}    
		
		HuffmanNode* LeftChild() const
		{
		    return _leftChild;
		}   
   
		HuffmanNode* RightChild() const
		{
		    return _rightChild;
		}
		
		HuffmanNode* Parent() const
		{
		    return _parent;
		}    
		
		long Weight() const
		{
		    return _weight;
		}
		
		T Data() const
		{
		    return _data;
		}    
		
		//重载的"<"和">"运算符,用于比较两个节点大小 
		bool operator<(const HuffmanNode& other) const
		{
		    return this->_weight <= other._weight;
		}  
  
        bool operator>(const HuffmanNode& other) const
        {
            return this->_weight > other._weight;
        }
        
        friend std::ostream& operator<<(std::ostream& out,const HuffmanNode& node)
        {
            return out<<"(Data:"<<node._data<<" Weight:"<<node._weight<<")";
        }    
          		      
		//析构函数,删除本对象的内存,所以没有了内存泄漏问题 (大傻瓜,根本达不到目的!) 
		~HuffmanNode()
  		{
  		    #ifdef _DEBUG
  		    cout<<"Node deleted:"<<_data<<"/"<<_weight<<endl;
  		    #endif
        	//delete this;
     	}
};

};//namespace huffman
    
#endif//

