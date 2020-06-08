package io.github.bloepiloepi.DataServer.commands;

import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.Server;

import java.util.ArrayList;

public abstract class Command {
	
	private String name;
	private int numberOfArgs;
	private String lastExecution;
	private ArrayList<String> aliases = new ArrayList<>();
	
	private boolean onlyConsole;
	
	public Command(String name, int minimumNumberOfArgs, boolean onlyConsole) {
		this.name = name;
		this.numberOfArgs = minimumNumberOfArgs;
		this.onlyConsole = onlyConsole;
	}
	
	public void addAlias(String name) {
		aliases.add(name);
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<String> getAliases() {
		return aliases;
	}
	
	public String getLastExecution() {
		return lastExecution;
	}
	
	public boolean isOnlyConsole() {
		return onlyConsole;
	}
	
	public void setLastExecution(String lastExecution) {
		this.lastExecution = lastExecution;
	}
	
	public Status execute(Server server, String[] args) {
		setLastExecution(null);
		if (args.length >= numberOfArgs) {
			return onExecute(server, args);
		} else {
			return Status.MISSING_ARGUMENTS;
		}
	}
	
	abstract Status onExecute(Server server, String[] args);
}
