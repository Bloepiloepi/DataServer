package io.github.bloepiloepi.DataServer.network.client;

import io.github.bloepiloepi.DataServer.console.Console;
import io.github.bloepiloepi.DataServer.network.Server;
import io.github.bloepiloepi.DataServer.network.ServerThread;
import io.github.bloepiloepi.DataServer.network.packets.CommandPacket;
import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.security.SecurityManager;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler extends ServerThread {
	
	private Socket socket;
	
	public ClientHandler(Socket socket) {
		super("Client-Handler " + Server.getServer().getClientCount());
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			socket.setSoTimeout(2500);
			Console.connection("Connected to " + socket.getRemoteSocketAddress());
			
			Communication com = new Communication(socket.getInputStream(), socket.getOutputStream());
			
			if (!SecurityManager.isAllowed(socket.getInetAddress().getHostAddress())) {
				com.sendNotTrusted();
				socket.close();
				
				throw new InterruptedException();
			} else if (Server.getServer().getClientCount() < 25) {
				com.sendTrusted();
			} else {
				com.sendTooMuchClients();
				socket.close();
				
				throw new InterruptedException();
			}
			
			CommandPacket receivedPacket = com.receive();
			Console.info("Received command packet with " + receivedPacket.getNumberOfCommands() + " command" + (receivedPacket.getNumberOfCommands() > 1 ? "s" : "") + ":");
			
			for (String command : receivedPacket.getCommands()) {
				String[] args = command.split(" ");
				Status status0 = Console.processCommand(args, false);
				
				if (Console.commandResult != null) {
					com.addReply(status0, Console.commandResult);
				} else {
					com.addReply(status0, "No result");
				}
			}
			com.sendReplies();
		} catch (SocketTimeoutException e) {
			Console.connection("Connection timed out: " + socket.getRemoteSocketAddress());
		} catch (IOException e) {
			Console.error("Something went wrong! I/O Error: " + e);
		} catch (InterruptedException ignored) {
		
		} finally {
			Server.getServer().decreaseClientCount();
			if (socket != null) {
				try {
					socket.close();
					Console.connection("Disconnected from " + socket.getRemoteSocketAddress());
				} catch (IOException ignored) {}
			}
		}
	}
}
