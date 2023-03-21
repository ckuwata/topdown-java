package info.return0.topdown;

public interface Parser<T> {
    public Result<T> parse(CharacterSequence source);
}