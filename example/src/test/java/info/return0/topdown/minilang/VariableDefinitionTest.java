package info.return0.topdown.minilang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.example.minilang.parser.VariableDefinition;

public class VariableDefinitionTest {
	@Test
	void test1() {
		var parser = new VariableDefinition();
		var code = "n: int = 1;";
		var seq = CharacterSequence.builder().source(code).autoRemoveWS(true).build();
		var result = parser.parse(seq);
		Assertions.assertFalse(result.failed(), () -> result.getReason().getReason());
		Assertions.assertNotNull(result.getValue());
	}
	
	@Test
	void test2() {
		var parser = new VariableDefinition();
		var code = "n: string = \"aaa\";";
		var seq = CharacterSequence.builder().source(code).autoRemoveWS(true).build();
		var result = parser.parse(seq);
		Assertions.assertFalse(result.failed(), () -> result.getReason().getReason());
		Assertions.assertNotNull(result.getValue());
	}
}
