package io.github.bloepiloepi.DataServer.network.packets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

public class ReturnPacket {
	
	private ArrayList<String> lines;
	
	private ReturnPacket(ArrayList<String> lines) {
		this.lines = lines;
	}
	
	public ReturnPacket(Status status0, String result) {
		this(new ArrayList<String>() {{
			add(String.valueOf(status0.getNumber()));
			add(result);
		}});
	}
	
	public int getReturn() {
		return Integer.parseInt(lines.get(0));
	}
	
	public String getResult() {
		return lines.get(1);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		Iterator<String> iterator = lines.iterator();
		if (iterator.hasNext()) {
			builder.append(iterator.next());
			
			while (iterator.hasNext()) {
				builder.append("%l%");
				builder.append(iterator.next());
			}
		}
		
		return builder.toString();
	}
	
	public static ReturnPacket fromString(String string) {
		String[] split = string.split(Pattern.quote("%l%"));
		ArrayList<String> lines = new ArrayList<>(Arrays.asList(split));
		return new ReturnPacket(lines);
	}
}
