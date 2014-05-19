package socket.threadNio.timeserver;

import java.util.Date;

import socket.threadNio.nioserver.Request;
import socket.threadNio.nioserver.event.EventAdapter;

/**
 * 日志记录
 */
public class LogHandler extends EventAdapter {
	public LogHandler() {
	}

	public void onClosed(Request request) throws Exception {
		String log = new Date().toString() + " from " + request.getAddress().toString();
		System.out.println(log);
	}

	public void onError(String error) {
		System.out.println("Error: " + error);
	}
}
