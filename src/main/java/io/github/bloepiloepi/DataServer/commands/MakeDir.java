package io.github.bloepiloepi.DataServer.commands;

import io.github.bloepiloepi.DataServer.console.Console;
import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.Server;

import java.io.File;

public class MakeDir extends Command {
	
	public MakeDir() {
		super("makedir", 1, false);
	}
	
	@Override
	Status onExecute(Server server, String[] args) {
		File root = server.getRoot();
		File target = new File(root, args[0]);
		
		if (target.isFile()) {
			return Status.ALREADY_EXISTS_AS_FILE;
		}
		if (!target.getParentFile().isDirectory()) {
			return Status.PARENT_FILE_MUST_BE_DIRECTORY;
		}
		if (!target.exists()) {
			if (!target.mkdir()) {
				Console.error("Error creating directory!");
				
				return Status.INTERNAL_ERROR;
			}
		}
		
		return Status.SUCCESSFULLY_EXECUTED;
	}
}
