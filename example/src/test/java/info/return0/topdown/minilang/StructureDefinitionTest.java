package info.return0.topdown.minilang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.example.minilang.parser.StructureDefinition;

public class StructureDefinitionTest {
	@Test
	void test1() {
		var parser = new StructureDefinition();
		var code = MiniLangParserTest.readResourceAsString("struct.mil");
		var seq = CharacterSequence.builder().source(code).autoRemoveWS(true).build();
		var result = parser.parse(seq);
		Assertions.assertFalse(result.failed(), () -> result.getReason().getReason());
		Assertions.assertNotNull(result.getValue());
	}
}
