package jerry.c2c.service;

import jerry.c2c.domain.Bid;
import jerry.c2c.domain.Item;
import jerry.c2c.domain.Trade;
import jerry.c2c.exception.BusinessException;

public interface BidService
{
	public void save(Bid bid) throws BusinessException;
	public void delete(Bid bid);
	public Bid getById(long id) throws BusinessException;
	public Bid getHighestByItem(Item item);
	public void checkBidWinner() throws BusinessException;
	public BidType validateBid(Bid bid);
	
	public void saveTrade(Trade trade) throws BusinessException;
	public void deleteTrade(Trade trade);
	public Trade getTradeById(long id) throws BusinessException;
}
