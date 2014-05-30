package tk.blizz.gzipfilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServletResponse Filter
 * compress http response with gzip
 * 
 * @author Zhenlei Huang (zlei.huang@gmail.com)
 *
 */
public class GZIPResponseFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			final String acceptEncoding = ((HttpServletRequest)request).getHeader("Accept-Encoding");

			if (response instanceof HttpServletResponse) {
				if (acceptEncoding != null && acceptEncoding.toLowerCase().indexOf("gzip") > -1) {
					GZIPResponseWrapper gzipResponse = new GZIPResponseWrapper((HttpServletResponse)response);

					chain.doFilter(request, gzipResponse);

					gzipResponse.finish();

					return;
				}
			}
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
