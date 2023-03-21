package info.return0.topdown;

import java.util.function.BiFunction;

public class ChainRight<T> implements Parser<T> {
	private Parser<T> delegate;
	private Parser<BiFunction<T, T, T>> operator;
	private boolean allowTrailingOperator;
	
	public ChainRight(Parser<T> delegate, Parser<BiFunction<T, T, T>>  operator) {
		this(delegate, operator, false);
	}
	
	public ChainRight(Parser<T> delegate, Parser<BiFunction<T, T, T>> operator, boolean allowTrailingOperator) {
		this.delegate = delegate;
		this.operator = operator;
		this.allowTrailingOperator = allowTrailingOperator;
	}

	@Override
	public Result<T> parse(CharacterSequence source) {
		return chain(this.delegate.parse(source), source);
	}
	
	Result<T> chain(Result<T> r, CharacterSequence source) {
		return r.then((v) -> {
			var o = this.operator.parse(source);
			if (o.failed()) {
				return Result.success(v);
			}
			var n = this.delegate.parse(source);
			if (n.failed()) {
				if (this.allowTrailingOperator) {
					return Result.success(v);
				} else {
					return n;
				}
			}
			var right = chain(n, source);
			return Result.success(o.getValue().apply(v, right.getValue()));
		});
	}
}
