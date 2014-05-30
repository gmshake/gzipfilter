package tk.blizz.gzipfilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest Filter
 * decompress gziped Http request
 * 
 * @author Zhenlei Huang (zlei.huang@gmail.com)
 *
 */
public class GZIPRequestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			final HttpServletRequest req = (HttpServletRequest)request;
			final String contentEncoding = req.getHeader("Content-Encoding");
			if (contentEncoding != null && contentEncoding.toLowerCase().indexOf("gzip") > -1)
				request = new GZIPRequestWrapper(req);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
