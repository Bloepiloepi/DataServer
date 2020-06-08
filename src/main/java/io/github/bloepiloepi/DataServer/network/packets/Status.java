package io.github.bloepiloepi.DataServer.network.packets;

public enum Status {
	SUCCESSFULLY_EXECUTED("The command was successfully executed.", 0),
	PERMISSION_DENIED("Permission was denied.", 1),
	ACCESSOR_NOT_TRUSTED("You are not allowed to connect with this server!", 2),
	ACCESSOR_TRUSTED("You are allowed to connect with this server.", 3),
	FILE_DOES_NOT_EXIST("The file does not exist.", 4),
	FILE_IS_DIRECTORY("You can't ask for a directory.", 5),
	UNKNOWN_COMMAND("That command is not known!", 6),
	INTERNAL_ERROR("An internal error occurred.", 7),
	MISSING_ARGUMENTS("Not enough arguments were specified!", 8),
	PARENT_FILE_MUST_BE_DIRECTORY("The parent file of the file you specified must be a directory, which is isn't.", 9),
	ALREADY_EXISTS_AS_FILE("We can't make a directory which already exists as file!", 10),
	NOT_A_DIRECTORY("The path you specified does not lead to a directory.", 11),
	TEST_SUCCEEDED("Connection test succeeded!", 12),
	ONLY_CONSOLE("Only the console can execute this command!", 13),
	TOO_MUCH_CLIENTS("Currently, this server has too many clients. Please wait before reconnecting.", 14);
	
	private String message;
	private int number;
	
	Status(String message, int number) {
		this.message = message;
		this.number = number;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getNumber() {
		return number;
	}
	
	public boolean isError() {
		return number == 1 || number == 2 || (number >= 4 && number <= 11) || number == 13 || number == 14;
	}
	
	public static Status getByNumber(int number) {
		for (Status status0 : Status.values()) {
			if (status0.getNumber() == number) return status0;
		}
		return null;
	}
}
