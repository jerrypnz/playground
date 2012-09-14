/**
 * 
 */
package jerry.c2c.dao;

import java.util.List;

import jerry.c2c.domain.Item;
import jerry.c2c.domain.Category;
import jerry.c2c.domain.ItemComment;
import jerry.c2c.domain.User;

/**
 * @author Jerry
 *
 */
public interface ItemDao
{
	public void saveItem(Item item);
	public void saveItemCategory(Category category);
	public void saveItemComment(ItemComment comment);
	
	public void deleteItem(Item item);
	public void deleteItemCategory(Category category);
	public void deleteItemComment(ItemComment comment);
	
	public Item getItemById(long id);
	public Category getItemCategoryById(long id);
	public Category getItemCategoryByName(String name);
	public ItemComment getItemCommentById(long id);
	
	public List listItemsByKeyword(String keyWord);
	public List listItemsByCategory(Category category);
	public List listItemsByExample(Item item);
	
	public List listItemCategoriesByKeyword(String keyWord);
	public List listItemCategoriesByParent(Category parent);
	
	public List listItemCommentsByItem(Item item);
	public List listItemCommentsByKeyword(String keyWord);
	public List listItemCommentsByMaker(User user);
	
	public List queryItems(String query);
	public List queryItemCategorys(String query);
}
