package io.github.bloepiloepi.DataServer.network.packets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

public class CommandPacket {
	
	private ArrayList<String> commands;
	
	public CommandPacket(ArrayList<String> commands) {
		this.commands = commands;
	}
	
	public CommandPacket(String... commands) {
		this(new ArrayList<>(Arrays.asList(commands)));
	}
	
	public ArrayList<String> getCommands() {
		return commands;
	}
	
	public int getNumberOfCommands() {
		return commands.size();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		Iterator<String> iterator = commands.iterator();
		if (iterator.hasNext()) {
			builder.append(iterator.next());
			
			while (iterator.hasNext()) {
				builder.append("%l%");
				builder.append(iterator.next());
			}
		}
		
		return builder.toString();
	}
	
	public static CommandPacket fromString(String string) {
		String[] split = string.split(Pattern.quote("%l%"));
		ArrayList<String> commands = new ArrayList<>(Arrays.asList(split));
		return new CommandPacket(commands);
	}
}