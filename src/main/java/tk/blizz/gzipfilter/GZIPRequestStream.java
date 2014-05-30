package tk.blizz.gzipfilter;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletInputStream;

/**
 * GZIP request stream
 * 
 * @author Zhenlei Huang (zlei.huang@gmail.com)
 *
 */
final class GZIPRequestStream extends ServletInputStream {
	private final GZIPInputStream in;

	GZIPRequestStream(ServletInputStream in) throws IOException {
		this.in = new GZIPInputStream(in);
	}

	@Override
	public int read() throws IOException {
		return this.in.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return this.in.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return this.in.read(b, off, len);
	}

	@Override
	public int available() throws IOException {
		return this.in.available();
	}

	@Override
	public void close() throws IOException {
		this.in.close();
	}
}
