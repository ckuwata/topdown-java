package info.return0.topdown;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SplitTest {
    @Test
    public void testSuccess() {
        var parser = new Split<>(new Regex("[a-z]+"), new Str(","));
        var cs = CharacterSequence.builder().source("a,b,c").autoRemoveWS(true).build();
        var result = parser.parse(cs);
        
        Assertions.assertFalse(result.failed());
        var list = new ArrayList<>(result.getValue());
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("b", list.get(1));
        Assertions.assertEquals("c", list.get(2));
    }

    @Test
    public void testWithTrailingSeparator() {
        var parser = new Split<>(new Regex("[a-z]+"), new Str(","), true);
        var cs = CharacterSequence.builder().source("a,b,c,").autoRemoveWS(true).build();
        var result = parser.parse(cs);
        
        Assertions.assertFalse(result.failed());
        var list = result.getValue();
        Assertions.assertEquals(3, list.size());
    }

    @Test
    public void testEmptyInput() {
        var parser = new Split<>(new Regex("[a-z]+"), new Token(","));
        var cs = CharacterSequence.builder().source("").autoRemoveWS(true).build();
        var result = parser.parse(cs);
        
        Assertions.assertFalse(result.failed());
        Assertions.assertTrue(result.getValue().isEmpty());
    }
}