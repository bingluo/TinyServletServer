package cn.seu.bingluo.server;

public class ResourceProcessor {

	public void process(Request request, Response response) {
		try {
			response.getResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
