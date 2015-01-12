package net.nomadblacky.gsex.client.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.nomadblacky.gsex.client.NesicaId;
import net.nomadblacky.test.utils.TestParameter;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class NesicaIdTest {
	
	@DataPoint public static TestParameter<String, Boolean> PARAMS_1 = new TestParameter<>("1234123412341234", true);

	@Theory
	public void validateTest(TestParameter<String, Boolean> param) throws Exception {
		
		boolean actual = NesicaId.validate(param.arg);
		
		assertThat(actual, is(param.expected));
	}
	

}
