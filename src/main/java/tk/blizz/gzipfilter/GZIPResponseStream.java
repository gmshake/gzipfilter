package tk.blizz.gzipfilter;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;

/**
 * GZIP response stream
 * 
 * @author Zhenlei Huang (zlei.huang@gmail.com)
 *
 */
final class GZIPResponseStream extends ServletOutputStream {
	private final GZIPOutputStream gzipstream;
	private int count;

	GZIPResponseStream(ServletOutputStream stream) throws IOException {
		this.gzipstream = new GZIPOutputStream(stream);
	}

	@Override
	public void write(int b) throws IOException {
		this.gzipstream.write(b);
		this.count++;
	}

	@Override
	public void write(byte[] b) throws IOException {
		this.gzipstream.write(b);
		this.count += b.length;
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		this.gzipstream.write(b, off, len);
		this.count += len;
	}

	@Override
	public void flush() throws IOException {
		this.gzipstream.flush();
	}

	@Override
	public void close() throws IOException {
		this.gzipstream.close();
	}

	public void finish() throws IOException {
		this.gzipstream.finish();
	}

	public int getCount() {
		return this.count;
	}
}
