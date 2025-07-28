package info.return0.topdown;

import java.util.ArrayList;
import java.util.Collection;

public class Split<T> implements Parser<Collection<T>> {
	private Parser<T> delegate;
	private Parser<?> delimeter;
	private boolean allowTrailingDelimeter;

	public Split(Parser<T> delegate, Parser<?> delimeter) {
		this(delegate, delimeter, false);
	}
	
	public Split(Parser<T> delegate, Parser<?> delimeter, boolean allowTrailingDelimeter) {
		this.delegate = delegate;
		this.delimeter = delimeter;
		this.allowTrailingDelimeter = allowTrailingDelimeter;
	}
	
	@Override
	public Result<Collection<T>> parse(CharacterSequence source) {
		var ret = new ArrayList<T>();
		while (true) {
			var result = this.delegate.parse(source);
			if (result.failed()) {
				if (ret.isEmpty()) {
					if (source.rest().length() == 0) {
						return Result.success(ret);
					}
					return Result.end(result.getReason());
				} else {
					if (this.allowTrailingDelimeter) {
						break;
					} else {
						return Result.end(result.getReason());
					}
				}
			}
			ret.add(result.getValue());

			if (this.delimeter.parse(source).failed()) {
				break;
			}
		}
		return Result.success(ret);
	}

}