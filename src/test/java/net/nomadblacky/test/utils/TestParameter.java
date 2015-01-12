package net.nomadblacky.test.utils;

/**
 * 1つの引数を取るテストパラメタ
 * @author blacky
 *
 * @param <T1>
 * @param <T2>
 */
public class TestParameter<T1, T2> {
	
	public final T1 arg;
	public final T2 expected;

	public TestParameter(T1 arg, T2 expected) {
		this.arg = arg;
		this.expected = expected;
	}
	
}
