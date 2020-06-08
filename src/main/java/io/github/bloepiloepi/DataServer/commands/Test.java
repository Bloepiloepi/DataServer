package io.github.bloepiloepi.DataServer.commands;

import io.github.bloepiloepi.DataServer.network.packets.Status;
import io.github.bloepiloepi.DataServer.network.Server;

public class Test extends Command {
	
	public Test() {
		super("test", 0, false);
	}
	
	@Override
	Status onExecute(Server server, String[] args) {
		setLastExecution("Running on port " + server.getServerSocket().getLocalPort());
		return Status.TEST_SUCCEEDED;
	}
}
