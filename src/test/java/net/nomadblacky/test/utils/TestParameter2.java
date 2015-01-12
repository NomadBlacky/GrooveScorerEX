package net.nomadblacky.test.utils;

/**
 * 2つの引数を取るテストパラメタ
 * @author blacky
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */
public class TestParameter2<T1, T2, T3> {
	
	public final T1 arg1;
	public final T2 arg2;
	public final T3 expected;

	public TestParameter2(T1 arg1, T2 arg2, T3 expected) {

		this.arg1 = arg1;
		this.arg2 = arg2;
		this.expected = expected;
	}
}
