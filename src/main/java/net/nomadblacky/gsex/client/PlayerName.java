package net.nomadblacky.gsex.client;

public class PlayerName {
	
	private final String _name;

	public PlayerName(String name) {
		this._name = name;
	}
	
	public static boolean validate(String name) {
		return true;
	}
}
