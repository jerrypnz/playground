/**
 * 
 */
package jerry.c2c.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Jerry
 *
 */
public class ChineseFilter implements Filter
{
	private String encoding = null;
	private boolean enabled = true;
	private FilterConfig filterConfig = null;
	

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy()
	{
		encoding = null;
		filterConfig = null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException
	{
		if(enabled || request.getCharacterEncoding()==null)
		{
				request.setCharacterEncoding(this.encoding);
		}
		//System.out.println("Encoding set to" + encoding);
		filterChain.doFilter(request, response);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException
	{
		this.filterConfig = config;
		this.encoding = filterConfig.getInitParameter("encoding");
		String disable = filterConfig.getInitParameter("disable");
		if(this.encoding == null)
			this.encoding = "UTF-8";
		if(disable != null && disable.equalsIgnoreCase("true"))
			this.enabled = false;

	}

}
