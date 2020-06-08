package io.github.bloepiloepi.DataServer.console;

import io.github.bloepiloepi.DataServer.commands.Command;
import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.Server;
import io.github.bloepiloepi.DataServer.network.ServerThread;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Console extends ServerThread {
	
	public Console() {
		super("Console Thread");
	}
	
	public static void connection(String message) {
		print("[Network] " + Color.RESET + message);
	}
	
	private static void print(String message) {
		System.out.println("[" + formatTime() + "] [" + ServerThread.currentServerThread().getThreadName() + "] " + message);
	}
	
	public static void info(String message) {
		print("[Info] " + message);
	}
	
	public static void error(String message) {
		print(Color.RED + "[Error] " + message + Color.RESET);
	}
	
	public static String fit(String message) {
		return message.replaceAll("\\n", "%n%");
	}
	
	public static void newLine() {
		System.out.println(Color.BLACK + Color.INVISIBLE_CHAR + Color.RESET);
	}
	
	public static void fatal(String message) {
		print(Color.RED + "[Fatal Error] " + message + Color.RESET);
		System.exit(-1);
	}
	
	public static void commandReturn(String message, String command) {
		print("[Command] " + Color.GREEN + command + Color.RESET + " => " + message);
	}
	
	public static String formatTime() {
		return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
	
	public static String commandResult = null;
	
	public static Status processCommand(String[] args, boolean console) {
		for (Command command : Server.getServer().commands) {
			if (command.getName().equalsIgnoreCase(args[0]) || isAlias(command, args[0])) {
				if (!console && command.isOnlyConsole()) {
					return Status.ONLY_CONSOLE;
				}
				Status status0 = command.execute(Server.getServer(), Arrays.copyOfRange(args, 1, args.length));
				commandResult = command.getLastExecution();
				return status0;
			}
		}
		commandResult = null;
		return Status.UNKNOWN_COMMAND;
	}
	
	public static boolean isAlias(Command command, String alias) {
		for (String string : command.getAliases()) {
			if (string.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
	
	public static String formatReturn(Status status0) {
		return Color.BLUE + status0.toString() + Color.RESET + ": " + status0.getMessage();
	}
	
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		Console.info("Built in command listener initialized");
		while (true) {
			if (!Server.getServer().stopping) {
				try {
					if (scanner.hasNextLine()) {
						String command = scanner.nextLine();
						
						String[] split = command.split(" ");
						String commandName = split[0].toLowerCase();
						
						Status status0 = processCommand(split, true);
						Console.commandReturn(formatReturn(status0), commandName);
						if (commandResult != null) {
							Console.commandReturn(Console.fit(commandResult), commandName);
						} else {
							Console.commandReturn("No result", commandName);
						}
					}
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					Console.error("Something went wrong executing user command: " + e);
				}
			} else {
				break;
			}
		}
	}
}
