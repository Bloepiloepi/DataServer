package io.github.bloepiloepi.DataServer.commands;

import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.Server;

public class Stop extends Command {
	
	public Stop() {
		super("stop", 0, true);
		this.addAlias("end");
	}
	
	@Override
	Status onExecute(Server server, String[] args) {
		server.stopServer();
		return Status.SUCCESSFULLY_EXECUTED;
	}
}
