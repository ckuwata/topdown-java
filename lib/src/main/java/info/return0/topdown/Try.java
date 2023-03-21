package info.return0.topdown;

public class Try<T> implements Parser<T> {
	private Parser<T> delegate;

	public Try(Parser<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public Result<T> parse(CharacterSequence source) {
		source.pushCursor();
		var ret = this.delegate.parse(source);
		if (ret.failed()) {
			source.rollbackPosition();
		}
		return ret;
	}
}
