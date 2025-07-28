package info.return0.topdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegexTest {
    @Test
    public void testSuccess() {
        var parser = new Regex("[a-z]+");
        var result = parser.parse(CharacterSequence.builder().source("abcdef").build());
        Assertions.assertFalse(result.failed());
        Assertions.assertEquals("abcdef", result.getValue());
    }

    @Test
    public void testFail() {
        var parser = new Regex("[a-z]+");
        var result = parser.parse(CharacterSequence.builder().source("12345").build());
        Assertions.assertTrue(result.failed());
    }

    @Test
    public void testPartialMatch() {
        var parser = new Regex("[a-z]+");
        var cs = CharacterSequence.builder().source("abc123def").build();
        var result = parser.parse(cs);
        Assertions.assertFalse(result.failed());
        Assertions.assertEquals("abc", result.getValue());
        Assertions.assertEquals("123def", cs.rest());
    }
}
