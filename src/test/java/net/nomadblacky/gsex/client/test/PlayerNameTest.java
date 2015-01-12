package net.nomadblacky.gsex.client.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.nomadblacky.gsex.client.PlayerName;
import net.nomadblacky.test.utils.TestParameter;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class PlayerNameTest {
	
	@DataPoint public static TestParameter<String, Boolean> PARAM_1 = new TestParameter<>("BLACKYv2", true);
	@DataPoint public static TestParameter<String, Boolean> PARAM_2 = new TestParameter<>("hogehogehogheoggheo", false);

	@Theory
	public void validateTest(TestParameter<String, Boolean> param) throws Exception {
		
		boolean actual = PlayerName.validate(param.arg);
		assertThat(actual, is(param.expected));
	}

}
