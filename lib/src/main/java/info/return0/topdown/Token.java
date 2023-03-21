package info.return0.topdown;

import java.util.regex.Pattern;

public class Token implements Parser<String> {
	private Pattern  pattern;
	private String token;

	public Token(String token) {
		this.pattern = Pattern.compile("^((" + Pattern.quote(token) + ")(\\s|$))");
		this.token = token;
	}
	
	@Override
	public Result<String> parse(CharacterSequence source) {
		if (this.pattern.matcher(source.rest()).find()) {
			source.forward(this.token.length());
			return Result.success(this.token);
		}
		return Result.end(new ParsingError(this.token));
	}
	
	@Override
	public String toString() {
		return this.token;
	}
}
