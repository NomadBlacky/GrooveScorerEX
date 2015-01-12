package net.nomadblacky.gsex.exceptions;

public class JsonStatusException extends MyPageClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4058599301132817543L;

	public JsonStatusException() {
		super("JSONステータスが不正");
	}
}
