package info.return0.topdown;

import java.util.regex.Pattern;

public class Regex implements Parser<String> {
	private Pattern  pattern;
	private String base;

	public Regex(String reg) {
		this.pattern = Pattern.compile("^"+reg);
		this.base = reg;
	}
	
	@Override
	public Result<String> parse(CharacterSequence source) {
		var m = this.pattern.matcher(source.rest());
		if (m.find()) {
			source.forward(m.group().length());
			return Result.success(m.group());
		}
		return Result.end(Failure.instance);
	}
}
