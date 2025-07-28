package info.return0.topdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChoiceTest {
    @Test
    public void testFirst() {
        var parser = Choice.of(new Token("abc"), new Token("def"));
        var result = parser.parse(CharacterSequence.builder().source("abc").build());
        Assertions.assertFalse(result.failed());
        Assertions.assertEquals("abc", result.getValue());
    }

    @Test
    public void testSecond() {
        var parser = Choice.of(new Token("abc"), new Token("def"));
        var result = parser.parse(CharacterSequence.builder().source("def").build());
        Assertions.assertFalse(result.failed());
        Assertions.assertEquals("def", result.getValue());
    }

    @Test
    public void testFail() {
        var parser = Choice.of(new Token("abc"), new Token("def"));
        var result = parser.parse(CharacterSequence.builder().source("ghi").build());
        Assertions.assertTrue(result.failed());
    }
}
