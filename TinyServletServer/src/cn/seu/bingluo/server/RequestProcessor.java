package cn.seu.bingluo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * RequestProcessor 类说明
 * @author chengwei.tcw 2015年7月15日 下午9:24:51
 */
public class RequestProcessor implements Runnable{
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	
	public RequestProcessor(Socket socket){
		try {
			this.socket = socket;
			this.in = socket.getInputStream();
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		Request request = new Request(in);
		request.parse();

		Response response = new Response(out);
		response.setRequest(request);

		if (request.getUri().startsWith("/servlets/")) {
			System.out.println("Request servlet: " + request.getUri());
			ServletProcessor.process(request, response);
		} else {
			System.out.println("Request static source: "
					+ request.getUri());
			ResourceProcessor.process(request, response);
		}
		System.out.println("Responsed.");

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
