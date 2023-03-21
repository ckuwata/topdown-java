package info.return0.topdown;

public class Str implements Parser<String> {
	private String token;

	public Str(String token) {
		this.token = token;
	}
	
	@Override
	public Result<String> parse(CharacterSequence source) {
		if (source.rest().startsWith(this.token)) {
			source.forward(this.token.length());
			return Result.success(this.token);
		}
		return Result.end(new ParsingError(token));
	}

}
