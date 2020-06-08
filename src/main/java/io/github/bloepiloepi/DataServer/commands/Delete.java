package io.github.bloepiloepi.DataServer.commands;

import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.Server;

import java.io.File;

public class Delete extends Command {
	
	public Delete() {
		super("delete", 1, false);
	}
	
	@Override
	Status onExecute(Server server, String[] args) {
		File root = server.getRoot();
		File target = new File(root, args[0]);
		
		if (!target.exists()) {
			return Status.FILE_DOES_NOT_EXIST;
		} else if (target.isDirectory()) {
			if (!deleteDir(target)) {
				return Status.INTERNAL_ERROR;
			}
		} else {
			if (!deleteFile(target)) {
				return Status.INTERNAL_ERROR;
			}
		}
		
		return Status.SUCCESSFULLY_EXECUTED;
	}
	
	private boolean deleteDir(File file) {
		boolean return0 = true;
		
		File[] files = file.listFiles();
		for (File file1 : files) {
			if (file1.isDirectory()) {
				if (!deleteDir(file1)) {
					return0 = false;
				}
			} else {
				if (!deleteFile(file1)) {
					return0 = false;
				}
			}
		}
		if (!file.delete()) {
			return0 = false;
		}
		
		return return0;
	}
	
	private boolean deleteFile(File file) {
		return file.delete();
	}
}
