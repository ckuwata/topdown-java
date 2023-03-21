package info.return0.topdown;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class Choice<T> implements Parser<T> {
	private Collection<Parser<T>> choice;

	private Choice(Collection<Parser<T>> choice) {
		super();
		this.choice = choice;
	}
	
	public static <T> Choice<T> ofTry(Collection<Parser<T>> choice) {
		return new Choice<T>(choice.stream().map(p -> new Try<>(p)).collect(Collectors.toList()));
	}
	
	public static <T> Choice<T> of(Collection<Parser<T>> choice) {
		return new Choice<>(choice);
	}
	
	@SafeVarargs
	public static <T> Choice<T> ofTry(Parser<T>... choice) {
		return Choice.ofTry(Arrays.asList(choice));
	}
	
	@SafeVarargs
	public static <T> Choice<T> of(Parser<T>... choice) {
		return Choice.of(Arrays.asList(choice));
	}

	@Override
	public Result<T> parse(CharacterSequence source) {
		for (var p : this.choice) {
			var ret = p.parse(source);
			if (!ret.failed()) {
				return ret;
			}
		}
		return Result.end(new ParsingError("not matched" + this.choice));
	}
	
	
}
