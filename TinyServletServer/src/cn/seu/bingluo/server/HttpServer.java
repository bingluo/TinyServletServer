package cn.seu.bingluo.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.seu.bingluo.server.utils.Constants;

public class HttpServer {

	private ServerSocket server = null;
	private Executor executor = null;

	public void start() {
		try {
			server = new ServerSocket(Constants.PORT, Constants.BACKLOG,
					InetAddress.getByName(Constants.HOST));
			executor = Executors.newFixedThreadPool(Constants.THREAD_COUNT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			Socket socket = null;
			try {
				//accept the socket connection and push it to the thread pool.
				socket = server.accept();
				RequestProcessor processor = new RequestProcessor(socket);
				executor.execute(processor);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.start();
	}
}
