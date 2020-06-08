package io.github.bloepiloepi.DataServer.config;

import io.github.bloepiloepi.DataServer.console.Console;

import java.util.ArrayList;

public class Settings {
	
	private static String root;
	private static int port;
	private static ArrayList<String> trustedHostAddresses;
	
	private static boolean initialized;
	
	public static void setup(String root, int port, ArrayList<String> trustedHostAddresses) {
		initialized = true;
		Settings.root = root;
		Settings.port = port;
		Settings.trustedHostAddresses = trustedHostAddresses;
	}
	
	public static String getRoot() {
		if (initialized) {
			return root;
		}
		Console.fatal("Settings are not initialized!");
		throw new RuntimeException();
	}
	
	public static Integer getPort() {
		if (initialized) {
			return port;
		}
		Console.fatal("Settings are not initialized!");
		throw new RuntimeException();
	}
	
	public static ArrayList<String> getTrustedHostAddresses() {
		if (initialized) {
			return trustedHostAddresses;
		}
		Console.fatal("Settings are not initialized!");
		throw new RuntimeException();
	}
}
