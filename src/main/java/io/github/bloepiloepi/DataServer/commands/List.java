package io.github.bloepiloepi.DataServer.commands;

import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.Server;

import java.io.File;

public class List extends Command {
	
	public List() {
		super("list", 1, false);
	}
	
	@Override
	Status onExecute(Server server, String[] args) {
		File root = server.getRoot();
		File target = new File(root, args[0]);
		
		if (target.isFile()) {
			return Status.NOT_A_DIRECTORY;
		}
		if (!target.getParentFile().isDirectory()) {
			return Status.PARENT_FILE_MUST_BE_DIRECTORY;
		}
		if (target.exists()) {
			setLastExecution(arrayToString(target.listFiles()));
			
			return Status.SUCCESSFULLY_EXECUTED;
		}
		
		return Status.FILE_DOES_NOT_EXIST;
	}
	
	private String arrayToString(File[] files) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		
		for (int i = 0; i < files.length; i++) {
			stringBuilder.append(files[i].getName());
			if (i < files.length - 1) {
				stringBuilder.append(",");
			}
		}
		
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
