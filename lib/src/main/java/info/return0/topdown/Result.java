package info.return0.topdown;

import java.util.function.Function;

public class Result<T> {
	private Either<StopParsing, T> value;

	private Result(Either<StopParsing, T> value) {
		this.value = value;
	}
	
	public static <T> Result<T> success(T t) {
		return new Result<>(Either.right(t));
	}
	
	public static <T> Result<T> end(StopParsing e) {
		return new Result<>(Either.left(e));
	}
	
	public boolean failed() {
		return this.value.isLeft();
	}
	
	public T getValue() {
		return this.value.getRight();
	}
	
	public StopParsing getReason() {
		return this.value.getLeft();
	}
	
	public <S> Result<S> then(Function<T, Result<S>> action) {
		if (this.failed()) {
			return Result.end(getReason());
		}
		return action.apply(getValue());
	}
}
