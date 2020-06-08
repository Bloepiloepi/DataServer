package io.github.bloepiloepi.DataServer.commands;

import io.github.bloepiloepi.DataServer.console.Console;
import io.github.bloepiloepi.DataServer.network.Server;
import io.github.bloepiloepi.DataServer.network.packets.Status;

public class Help extends Command {
	
	public Help() {
		super("help", 0, true);
	}
	
	@Override
	Status onExecute(Server server, String[] args) {
		Console.commandReturn("Test: Use this to test the connection (only works from a client, from the console this will always return success).", "help");
		Console.commandReturn("Stop: Stops this server.", "help");
		Console.commandReturn("Save [path] [text]: Save the text into the file.", "help");
		Console.commandReturn("Read [path]: Read the contents of a file.", "help");
		Console.commandReturn("MakeDir [path]: Create a directory (this creates ONLY the directory you specified).", "help");
		Console.commandReturn("List [path]: List the files in a directory.", "help");
		Console.commandReturn("Delete [path]: Delete a file or directory.", "help");
		Console.commandReturn("Help: Get help for commands.", "help");
		
		return Status.SUCCESSFULLY_EXECUTED;
	}
}
