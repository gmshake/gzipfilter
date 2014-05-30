package tk.blizz.gzipfilter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * GZIP Http ServletResponse Wrapper
 * 
 * @author Zhenlei Huang (zlei.huang@gmail.com)
 *
 */
final class GZIPResponseWrapper extends HttpServletResponseWrapper {
	private final HttpServletResponse response;
	private GZIPResponseStream outStream;
	private PrintWriter writer;

	GZIPResponseWrapper(HttpServletResponse response) {
		super(response);
		this.response = response;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (this.writer != null)
			throw new IllegalStateException("getWriter() has already been called for this response");

		if (this.outStream == null) {
			this.outStream = new GZIPResponseStream(this.response.getOutputStream());
			this.response.addHeader("Content-Encoding", "gzip");
		}

		return this.outStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (this.writer == null) {
			if (this.outStream != null)
				throw new IllegalStateException("getOutputStream() has already been called for this response");

			this.outStream = new GZIPResponseStream(this.response.getOutputStream());
			this.writer = new PrintWriter(this.outStream);
			this.response.addHeader("Content-Encoding", "gzip");
		}

		return this.writer;
	}

	public void finish() throws IOException {
		if (this.writer != null) {
			this.writer.flush(); // HTTP 1.1 chunked encoding
			this.outStream.finish();
			//this.response.addHeader("Content-Length", Integer.toString(this.outStream.getCount()));
			this.writer.close();
		} else if (this.outStream != null) {
			this.outStream.flush(); // HTTP 1.1 chunked encoding
			this.outStream.finish();
			//this.response.addHeader("Content-Length", Integer.toString(this.outStream.getCount()));
			this.outStream.close();
		}
	}
}
