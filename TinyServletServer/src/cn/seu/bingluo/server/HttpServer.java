package cn.seu.bingluo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private ServerSocket server = null;

	public void start() {
		try {
			server = new ServerSocket(Constants.PORT, Constants.BACKLOG,
					InetAddress.getByName(Constants.HOST));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server started. Enter '/SHUTDOWN' to shutdown.");

		boolean shutdown = false;
		while (!shutdown) {
			Socket socket = null;
			InputStream in = null;
			OutputStream out = null;

			try {
				socket = server.accept();
				in = socket.getInputStream();
				out = socket.getOutputStream();

				Request request = new Request(in);
				request.parse();

				Response response = new Response(out);
				response.setRequest(request);

				if (request.getUri().startsWith("/servlets/")) {
					System.out.println("Request servlet: " + request.getUri());
					ServletProcessor processor = new ServletProcessor();
					processor.process(request, response);
				} else {
					System.out.println("Request static source: "
							+ request.getUri());
					ResourceProcessor processor = new ResourceProcessor();
					processor.process(request, response);
				}
				System.out.println("Responsed.");

				socket.close();

				shutdown = request.getUri().equals(Constants.SHUTDOWN_COMMAND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Server is shutdown.");
	}

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.start();
	}
}
