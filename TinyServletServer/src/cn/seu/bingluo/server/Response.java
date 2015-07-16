package cn.seu.bingluo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import cn.seu.bingluo.server.utils.Constants;

public class Response implements ServletResponse {
	private Request request = null;
	private OutputStream out = null;
	private int bufferSize = 1024;
	private String content = "";
	private String encoding = "utf-8";
	
	private boolean isCommitted = false;
	
	private Map<String, String> headerMap = new HashMap<String, String>();

	public Response(OutputStream out) {
		this.out = out;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void getResource() throws IOException {
		if (request == null || out == null) {
			return;
		}

		byte[] bytes = new byte[bufferSize];
		FileInputStream in = null;
		try {
			File file = new File(Constants.WEB_ROOT, request.getUri());
			if (file.exists()) {
				in = new FileInputStream(file);
				int count = in.read(bytes, 0, bufferSize);
				while (count != -1) {
					out.write(bytes);
					count = in.read(bytes, 0, bufferSize);
				}
			} else {
				String error = "HTTP/1.1 404 File Not Found\r\n"
						+ "Content-Type: text/html\r\n"
						+ "Content-Length: 51\r\n" + "\r\n"
						+ "<h1>File Not Found</h1><br/>MyStaticServer 1.0<br/>";
				out.write(error.getBytes());
				System.out.println("Source not found: " + request.getUri());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}

	}

	@Override
	public void flushBuffer() throws IOException {
		out.flush();
	}

	@Override
	public int getBufferSize() {
		return bufferSize;
	}

	@Override
	public String getCharacterEncoding() {
		return encoding;
	}

	@Override
	public String getContentType() {
		return content+"; charset="+encoding.toUpperCase();
	}

	@Override
	public Locale getLocale() {
		return Locale.CHINESE;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(out, true);
	}

	@Override
	public boolean isCommitted() {
		return isCommitted;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentType(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub

	}
}
