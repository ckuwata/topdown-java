package info.return0.topdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenTest {
	@Test
	public void testSuccess() {
		String data = "abc def";
		var ret = new Token("abc").parse(CharacterSequence.builder().source(data).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals("abc", ret.getValue());
	}
	
	@Test
	public void testFail() {
		String data = "abc def";
		var ret = new Token("ab").parse(CharacterSequence.builder().source(data).autoRemoveWS(true).build());
		Assertions.assertTrue(ret.failed());
	}
}
