package net.nomadblacky.gsex.client;

import java.util.regex.Pattern;

public class NesicaId {

	private final String _id;
	
	public NesicaId(String id) {
		
		if( !validate(id) ) {
			throw new IllegalArgumentException(
					String.format("nesicaIDは16桁の数字です。 : %s", id));
		}
		
		this._id = id;
	}
	
	public String getId() {
		return _id;
	}
	
	public static boolean validate(String id) {
		
		Pattern pattern = Pattern.compile("\\d{16}");
		return pattern.asPredicate().test(id);
	}
}
