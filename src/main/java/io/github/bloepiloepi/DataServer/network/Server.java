package io.github.bloepiloepi.DataServer.network;

import io.github.bloepiloepi.DataServer.commands.*;
import io.github.bloepiloepi.DataServer.config.Settings;
import io.github.bloepiloepi.DataServer.config.SettingsBuilder;
import io.github.bloepiloepi.DataServer.console.Console;
import io.github.bloepiloepi.DataServer.network.client.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Server extends ServerThread {
	
	public static final String BUILD = "1.1.7";
	private static Server server;
	
	private int clientCount = 0;
	public boolean stopping = false;
	
	public ArrayList<Command> commands = new ArrayList<>();
	private ServerSocket serverSocket;
	private File root;
	
	public Server(int port, File root) throws IOException {
		super("Main Server Thread");
		Console.newLine();
		Console.info("Initializing DataServer v" + BUILD + "...");
		Console.info("Root directory: " + root.getAbsolutePath());
		Console.info("Running on port " + port);
		
		Server.server = this;
		
		serverSocket = new ServerSocket(port);
		Console.info("Server socket initialized");
		this.root = root;
		
		new Console().start();
		
		registerCommand(new Read());
		registerCommand(new Save());
		registerCommand(new Delete());
		registerCommand(new MakeDir());
		registerCommand(new List());
		registerCommand(new Test());
		registerCommand(new Stop());
		registerCommand(new Help());
		Console.info("All commands initialized");
		
		Console.info("Done! For help, type \"help\".");
	}
	
	public int getClientCount() {
		return clientCount;
	}
	
	public void increaseClientCount() {
		clientCount = clientCount + 1;
	}
	
	public void decreaseClientCount() {
		if (clientCount > 0) {
			clientCount = clientCount - 1;
		}
	}
	
	public void stopServer() {
		Console.info("The server is being stopped, waiting for all connections to finish...");
		this.stopping = true;
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getRoot() {
		return root;
	}
	
	public void registerCommand(Command command) {
		for (Command command1 : commands) {
			if (command1.getName().equalsIgnoreCase(command.getName())) {
				Console.error("Tried to register command with a name already registered!");
				return;
			}
		}
		commands.add(command);
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	@Override
	public void run() {
		while (true) {
			if (!stopping) {
				try {
					Socket socket = serverSocket.accept();
					increaseClientCount();
					new ClientHandler(socket).start();
				} catch (IOException e) {
					if (!stopping) {
						Console.error("Failed to accept client request: " + e);
					}
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				Console.info("Stop process done. Bye!");
				break;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		SettingsBuilder.setupSettings(getJarFileDirectory());
		
		int port = Settings.getPort();
		String rootPath = Settings.getRoot();
		File root = new File(getJarFileDirectory(), rootPath);
		
		if (!root.exists()) {
			System.out.println("Creating root directory...");
			if (!root.mkdir()) {
				Console.fatal("Something went wrong whilst creating root directory!");
			}
		} else if (!root.isDirectory()) {
			Console.fatal("The root directory you specified is not a directory!");
		}
		try {
			new Server(port, root).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Server getServer() {
		return server;
	}
	
	private static File cached = null;
	
	public static File getJarFileDirectory() {
		if (cached == null) {
			try {
				cached = new File(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
			} catch (URISyntaxException e) {
				Console.fatal("Error getting jar file directory!");
				throw new RuntimeException();
			}
		}
		
		return cached;
	}
}
