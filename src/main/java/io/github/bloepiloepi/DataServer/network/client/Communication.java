package io.github.bloepiloepi.DataServer.network.client;

import io.github.bloepiloepi.DataServer.console.Console;
import io.github.bloepiloepi.DataServer.network.packets.CommandPacket;
import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.packets.ReturnPacket;

import java.io.*;

public class Communication {
	
	private BufferedReader in;
	private PrintWriter out;
	
	public Communication(InputStream in, OutputStream out) {
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = new PrintWriter(new OutputStreamWriter(out));
	}
	
	public void sendTrusted() {
		out.println(Status.ACCESSOR_TRUSTED.getNumber());
		out.flush();
	}
	
	public void sendTooMuchClients() {
		out.println(Status.TOO_MUCH_CLIENTS.getNumber());
		out.flush();
		Console.error("The server has too many clients!");
	}
	
	public void sendNotTrusted() {
		out.println(Status.ACCESSOR_NOT_TRUSTED.getNumber());
		out.flush();
	}
	
	public void addReply(Status status0, String result) {
		String fit = io.github.bloepiloepi.DataServer.console.Console.fit(result);
		out.println(new ReturnPacket(status0, fit).toString());
	}
	
	public void sendReplies() {
		out.flush();
	}
	
	public CommandPacket receive() throws IOException {
		String result = in.readLine();
		return CommandPacket.fromString(result);
	}
}
