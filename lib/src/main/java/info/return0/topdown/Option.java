package info.return0.topdown;

public class Option<T> implements Parser<T> {
	private Parser<T> delegate;
	private T defaultValue;
	
	
	public Option(Parser<T> delegate, T defaultValue) {
		this.delegate = delegate;
		this.defaultValue = defaultValue;
	}


	@Override
	public Result<T> parse(CharacterSequence source) {
		var ret = this.delegate.parse(source);
		if (ret.failed()) {
			return Result.success(this.defaultValue);
		}
		return ret;
	}
	
	
}
