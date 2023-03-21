package info.return0.topdown;

import java.util.ArrayList;
import java.util.Collection;

public class Split<T> implements Parser<Collection<T>> {
	private Parser<T> delegate;
	private Parser<T> delimeter;
	private boolean allowTrailingDelimeter;

	public Split(Parser<T> delegate, Parser<T> delimeter) {
		this(delegate, delimeter, false);
	}
	
	public Split(Parser<T> delegate, Parser<T> delimeter, boolean allowTrailingDelimeter) {
		this.delegate = delegate;
		this.delimeter = delimeter;
		this.allowTrailingDelimeter = allowTrailingDelimeter;
	}
	
	@Override
	public Result<Collection<T>> parse(CharacterSequence source) {
		var ret = new ArrayList<T>();
		var result = this.delegate.parse(source);
		while (true) {
			ret.add(result.getValue());
			if (this.delimeter.parse(source).failed()) {
				break;
			}
			result = this.delegate.parse(source);
			if (result.failed()) {
				if (this.allowTrailingDelimeter) {
					break;
				} else {
					return Result.end(result.getReason());
				}
			}
		}
		return Result.success(ret);
	}

}
