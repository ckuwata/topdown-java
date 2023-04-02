package info.return0.topdown.minilang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import info.return0.topdown.example.minilang.parser.MiniLangParser;

public class MiniLangParserTest {
	
	public static String readResourceAsString(String resourceName) {
        StringBuilder content = new StringBuilder();

        try (InputStream is = MiniLangParserTest.class.getResourceAsStream(resourceName);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("リソースの読み込みに失敗しました: " + resourceName);
            e.printStackTrace();
        }

        return content.toString();
    }
	@Test
	void test1() {
		var parser = new MiniLangParser();
		var code = readResourceAsString("test1.mil");
		var result = parser.parse(code);
		Assertions.assertFalse(result.failed(), () -> result.getReason().getReason());
		Assertions.assertNotNull(result.getValue());
	}
}
