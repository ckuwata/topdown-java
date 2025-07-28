package info.return0.topdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StrTest {
    @Test
    public void testSuccess() {
        var parser = new Str("hello");
        var result = parser.parse(CharacterSequence.builder().source("hello, world").build());
        Assertions.assertFalse(result.failed());
        Assertions.assertEquals("hello", result.getValue());
    }

    @Test
    public void testFail() {
        var parser = new Str("hello");
        var result = parser.parse(CharacterSequence.builder().source("goodbye, world").build());
        Assertions.assertTrue(result.failed());
    }

    @Test
    public void testCaseSensitive() {
        var parser = new Str("Hello");
        var result = parser.parse(CharacterSequence.builder().source("hello, world").build());
        Assertions.assertTrue(result.failed());
    }
}
