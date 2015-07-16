package cn.seu.bingluo.server;

public class ResourceProcessor {

	public static void process(Request request, Response response) {
		try {
			response.getResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
