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
 * HttpServletRequest/HttpServletResponse Filter
 * decompress gziped Http request, compress http response with gzip
 * 
 * @author Zhenlei Huang (zlei.huang@gmail.com)
 *
 */
public class GZIPFilter implements Filter {
	private boolean decompressRequest = true;
	private boolean compressResponse = true;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		final String acceptEncoding;
		if (request instanceof HttpServletRequest) {
			final HttpServletRequest req = (HttpServletRequest)request;

			final String contentEncoding = req.getHeader("Content-Encoding");
			acceptEncoding = req.getHeader("Accept-Encoding");

			if (contentEncoding != null && contentEncoding.toLowerCase().indexOf("gzip") > -1 && this.decompressRequest)
				request = new GZIPRequestWrapper(req);
		} else
			acceptEncoding = null;

		if (response instanceof HttpServletResponse) {
			if (acceptEncoding != null && acceptEncoding.toLowerCase().indexOf("gzip") > -1 && this.compressResponse)
				response = new GZIPResponseWrapper((HttpServletResponse)response);
		}

		chain.doFilter(request, response);

		if (response instanceof GZIPResponseWrapper)
			((GZIPResponseWrapper)response).finish();
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String dmp = config.getInitParameter("DecompressRequest");
		String cmp = config.getInitParameter("CompressResponse");

		if (dmp != null)
			this.decompressRequest = Boolean.valueOf(dmp);

		if (cmp != null)
			this.compressResponse = Boolean.valueOf(cmp);
	}

	@Override
	public void destroy() {
	}
}
