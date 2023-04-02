package info.return0.topdown;

import java.util.ArrayList;
import java.util.Collection;

public class AtLeastOne<T> implements Parser<Collection<T>> {
	private Parser<T> delegate;
	
	public AtLeastOne(Parser<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public Result<Collection<T>> parse(CharacterSequence source) {
		var ret = new ArrayList<T>();
		var result = this.delegate.parse(source);
		while (!result.failed()) {
			ret.add(result.getValue());
			result = this.delegate.parse(source);
		}
		if (ret.size() == 0) {
			return Result.end(result.getReason());
		}
		return Result.success(ret);
	}
	
}
