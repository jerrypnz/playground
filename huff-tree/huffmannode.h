//�������������֮STLʵ�� 
//����:Jerry
//����:15-06-06 01:10
 

#ifndef HUFFMANNODE_H
#define HUFFMANNODE_H
#include <iostream>

//�����ռ䶨�� 
namespace huffman
{

//���������ڵ��� 
template <typename T>
class HuffmanNode
{
    private:
        //���Һ���ָ�� 
        HuffmanNode *_leftChild,*_rightChild;
        HuffmanNode *_parent;
        //Ȩֵ 
        long _weight;
        //����(ģ�廯��) 
        T _data;
        
        //���캯������Ϊ˽��,���Բ���ֱ�ӹ������
        //����HuffmanNode<char> fooNode;�����Ķ���Ͳ���ͨ������ 
        //Ҫ�õ�һ���ڵ�,����ͨ����̬��Ա����CreateNode 
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
	    
	    //����ڲ���ֻ��һ�������˵�()�����,�������ȶ�����,���ڱȽ������ڵ� 
	    class Smaller
	    {
	        public:
	        	bool operator()(const HuffmanNode* node1,const HuffmanNode* node2)
	        	{
	            	return (node1->_weight) > (node2->_weight);
	            }
         };   
         
        //����Ψһ�Ĵ�������Ľӿ�,��ȷ���˶��󴴽��ڶ���,�������  
        static HuffmanNode* CreateNode(T data,long weight,HuffmanNode* left=NULL,HuffmanNode* right=NULL,HuffmanNode* parent=NULL)
        {
            return new HuffmanNode(data,weight,left,right,parent);
        }       
		
		//һϵ�е�getter��setter��Ա����(��ô��ô��Java?:-)) 
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
		
		//���ص�"<"��">"�����,���ڱȽ������ڵ��С 
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
          		      
		//��������,ɾ����������ڴ�,����û�����ڴ�й©���� (��ɵ��,�����ﲻ��Ŀ��!) 
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

