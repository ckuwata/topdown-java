package info.return0.topdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionTest {
    @Test
    public void testSuccess() {
        var parser = new Option<>(new Token("optional"), null);
        var result = parser.parse(CharacterSequence.builder().source("optional").build());
        Assertions.assertFalse(result.failed());
        Assertions.assertEquals("optional", result.getValue());
    }

    @Test
    public void testSuccessOnFailure() {
        var parser = new Option<>(new Token("optional"), null);
        var result = parser.parse(CharacterSequence.builder().source("required").build());
        Assertions.assertFalse(result.failed());
        Assertions.assertNull(result.getValue());
    }
}
