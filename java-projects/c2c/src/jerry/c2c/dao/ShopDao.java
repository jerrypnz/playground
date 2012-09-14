package jerry.c2c.dao;

import java.util.List;

import jerry.c2c.domain.Shop;
import jerry.c2c.domain.ShopComment;
import jerry.c2c.domain.User;

public interface ShopDao
{
	public void saveShop(Shop shop);
	public void saveShopComment(ShopComment comment);
	
	public Shop getShopById(long id);
	public Shop getShopByName(String name);
	public ShopComment getShopCommentById(long id);
	
	public void deleteShop(Shop shop);
	public void deleteShopComment(ShopComment comment);
	
	public List listShopsByOwner(User owner);
	public List listShopsByKeyword(String keyWord);
	
	public List listShopCommentsByUser(User user);
	public List listShopCommentsByShop(Shop shop);
	public List listShopCommentsByKeyword(String keyWord);
	
	public List queryShops(String query);
	
}
