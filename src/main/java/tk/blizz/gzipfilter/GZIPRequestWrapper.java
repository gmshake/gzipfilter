package tk.blizz.gzipfilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * GZIP Http ServletRequest Wrapper
 * 
 * @author Zhenlei Huang (zlei.huang@gmail.com)
 *
 */
final class GZIPRequestWrapper extends HttpServletRequestWrapper {
	private final ServletInputStream inStream;
	private final BufferedReader reader;

	GZIPRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.inStream = new GZIPRequestStream(request.getInputStream());
		this.reader = new BufferedReader(new InputStreamReader(this.inStream));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return this.inStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return this.reader;
	}
}
