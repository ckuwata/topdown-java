package info.return0.topdown;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UntilTest {

    @Test
    void testUntilParserSuccess() {
        // Arrange
        CharacterSequence sequence = CharacterSequence.builder().source("abc;def").build();
        Until untilParser = new Until(";");

        // Act
        Result<String> result = untilParser.parse(sequence);

        // Assert
        assertFalse(result.failed());
        assertEquals("abc", result.getValue());
        assertEquals(";def", sequence.rest());
    }

    @Test
    void testUntilParserFailure() {
        // Arrange
        CharacterSequence sequence = CharacterSequence.builder().source("abcdef").build();
        Until untilParser = new Until(";");

        // Act
        Result<String> result = untilParser.parse(sequence);

        // Assert
        assertFalse(result.failed());
        assertEquals("abcdef", result.getValue());
        assertEquals("", sequence.rest());
    }
}
