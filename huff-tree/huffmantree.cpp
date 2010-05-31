// Class automatically generated by Dev-C++ New Class wizard

#include "huffmantree.h" // class's header file

// class constructor
template <class T>
HuffmanTree<T>::HuffmanTree()
:root(NULL),
 treeBuilt(false)
{
}

// class destructor
template <class T>
HuffmanTree<T>::~HuffmanTree()
{
	// insert your code here
}

template <class T>
#ifdef _DEBUG
void HuffmanTree<T>::PrintAllNodes(std::ostream& out)
{
     while(!pqNodes.empty())
     {
         out<<*pqNodes.top()<<endl;
         pqNodes.pop();
     }    
}
#else
void HuffmanTree<T>::PrintAllNodes(std::ostream& out){}
#endif