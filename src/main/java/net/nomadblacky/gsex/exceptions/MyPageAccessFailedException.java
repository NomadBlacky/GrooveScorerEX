package net.nomadblacky.gsex.exceptions;

public class MyPageAccessFailedException extends MyPageClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6969864043080300386L;

	public MyPageAccessFailedException(int httpStatusCode) {
		super("マイページへのアクセスに失敗(HTTPStatusCode : " + httpStatusCode + ")");
	}
}
