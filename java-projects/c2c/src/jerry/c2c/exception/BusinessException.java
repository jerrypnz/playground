package jerry.c2c.exception;

public class BusinessException extends Exception
{
	private String msg;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BusinessException(final String msg)
	{
		this.msg = msg;
	}
	
	public BusinessException()
	{
		this("The object does not exist");
	}
	
	public String getMessage()
	{
		return msg;
	}

}
