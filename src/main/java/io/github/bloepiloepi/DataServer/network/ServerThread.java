package io.github.bloepiloepi.DataServer.network;

public class ServerThread extends Thread {
	
	private String name;
	
	public ServerThread(String name) {
		this.name = name;
	}
	
	public String getThreadName() {
		return name;
	}
	
	public static ServerThread currentServerThread() {
		Thread current = Thread.currentThread();
		if (current instanceof ServerThread) {
			return (ServerThread) current;
		} else {
			return new ServerThread("Main Server Thread");
		}
	}
}
