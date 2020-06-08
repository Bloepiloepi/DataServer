package io.github.bloepiloepi.DataServer.commands;

import io.github.bloepiloepi.DataServer.console.Console;
import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Read extends Command {
	
	public Read() {
		super("read", 1, false);
	}
	
	@Override
	Status onExecute(Server server, String[] args) {
		File root = server.getRoot();
		File target = new File(root, args[0]);
		
		if (!target.exists()) {
			return Status.FILE_DOES_NOT_EXIST;
		} else if (target.isDirectory()) {
			return Status.FILE_IS_DIRECTORY;
		} else {
			try {
				Scanner scanner = new Scanner(target);
				StringBuilder lines = new StringBuilder();
				
				if (scanner.hasNextLine()) {
					while (scanner.hasNextLine()) {
						lines.append(scanner.nextLine()).append("%n%");
					}
					lines.delete(lines.length() - 3, lines.length());
				}
				scanner.close();
				
				this.setLastExecution(lines.toString());
				return Status.SUCCESSFULLY_EXECUTED;
			} catch (FileNotFoundException e) {
				Console.error("File not found while file exists!");
				e.printStackTrace();
				
				return Status.INTERNAL_ERROR;
			}
		}
	}
}
