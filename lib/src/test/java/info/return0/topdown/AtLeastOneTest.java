package info.return0.topdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AtLeastOneTest {
	@Test
	public void testSuccess() {
		String data = "abc abc abc";
		var ret = new AtLeastOne<>(new Token("abc")).parse(CharacterSequence.builder().source(data).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals(3, ret.getValue().size());
	}
	
	@Test
	public void testFail() {
		String data = "abc abc abc";
		var ret = new AtLeastOne<>(new Token("xxx")).parse(CharacterSequence.builder().source(data).autoRemoveWS(true).build());
		Assertions.assertTrue(ret.failed());
	}
}
