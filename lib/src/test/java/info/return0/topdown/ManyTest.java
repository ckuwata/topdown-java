package info.return0.topdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManyTest {
	@Test
	public void testSuccess() {
		String data = "abc abc abc";
		var ret = new Many<>(new Token("abc")).parse(CharacterSequence.builder().source(data).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals(3, ret.getValue().size());
	}
	
	@Test
	public void testFail() {
		String data = "abc abc abc";
		var ret = new Many<>(new Token("xxx")).parse(CharacterSequence.builder().source(data).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals(0, ret.getValue().size());
	}
}
