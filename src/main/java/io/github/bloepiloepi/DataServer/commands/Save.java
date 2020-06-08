package io.github.bloepiloepi.DataServer.commands;

import io.github.bloepiloepi.DataServer.console.Console;
import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save extends Command {
	
	public Save() {
		super("save", 2, false);
	}
	
	@Override
	Status onExecute(Server server, String[] args) {
		File root = server.getRoot();
		File target = new File(root, args[0]);
		
		StringBuilder data = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			data.append(args[i]);
			if (!(i == args.length - 1)) {
				data.append(" ");
			}
		}
		
		if (target.isDirectory()) {
			return Status.FILE_IS_DIRECTORY;
		}
		if (!target.getParentFile().isDirectory()) {
			return Status.PARENT_FILE_MUST_BE_DIRECTORY;
		}
		if (!target.exists()) {
			try {
				target.createNewFile();
			} catch (IOException e) {
				Console.error("Error creating file!");
				e.printStackTrace();
				
				return Status.INTERNAL_ERROR;
			}
		}
		
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(target);
			fileWriter.write(data.toString());
			
			return Status.SUCCESSFULLY_EXECUTED;
		} catch (IOException e) {
			Console.error("Error creating FileWriter!");
			e.printStackTrace();
			
			return Status.INTERNAL_ERROR;
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					Console.error("Error closing FileWriter!");
					e.printStackTrace();
					
					return Status.INTERNAL_ERROR;
				}
			}
		}
	}
}
