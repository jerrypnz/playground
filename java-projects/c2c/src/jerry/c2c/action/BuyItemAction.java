package jerry.c2c.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jerry.c2c.domain.Item;
import jerry.c2c.domain.Message;
import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.ItemService;
import jerry.c2c.service.MessageService;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BuyItemAction extends Action
{
	private ItemService itemService;

	private MessageService msgService;

	/**
	 * @return the itemService
	 */
	public ItemService getItemService()
	{
		return itemService;
	}

	/**
	 * @param itemService
	 *            the itemService to set
	 */
	public void setItemService(ItemService itemService)
	{
		this.itemService = itemService;
	}

	/**
	 * @return the msgService
	 */
	public MessageService getMsgService()
	{
		return msgService;
	}

	/**
	 * @param msgService
	 *            the msgService to set
	 */
	public void setMsgService(MessageService msgService)
	{
		this.msgService = msgService;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		String target = "pleaseLogin";
		HttpSession session = request.getSession();
		User buyer = (User) session.getAttribute("user");
		if (buyer != null)
		{
			try
			{
				long itemId = Integer.parseInt(request.getParameter("itemId"));
				Item itemToBuy = itemService.getById(itemId);
				User solder = itemToBuy.getBelongTo().getOwner();
				itemToBuy.setSoldOut(true);
				itemService.update(itemToBuy);
				Message solderMsg = new Message();
				Message buyerMsg = new Message();
				solderMsg.setTitle("您的商品" + itemToBuy.getName() + "已经售出");
				solderMsg.setContent(itemToBuy.getName() + "已经被我("
						+ buyer.getName() + ")购买，请及时与我联系并发货。");
				buyerMsg.setTitle("您已经购买商品" + itemToBuy.getName());
				buyerMsg.setContent("您已经购买了本店("
						+ itemToBuy.getBelongTo().getName() + ")的商品"
						+ itemToBuy.getName() + "，请及时与我(" + solder.getName()
						+ ")联系商量支付和送货方式");
				msgService.send(buyer, solder, solderMsg);
				msgService.send(solder, buyer, buyerMsg);
				target = "result";
				request.setAttribute("message_title", "成功购买物品");
				request.setAttribute("message_content", "您已经购买物品，请等待卖家和您联系");
			}
			catch (Exception e)
			{
				target = "result";
				request.setAttribute("message_title", "致命错误");
				request.setAttribute("message_content", "请与系统管理员联系，错误信息："
						+ e.getMessage());
			}
		}
		return mapping.findForward(target);
	}
}
