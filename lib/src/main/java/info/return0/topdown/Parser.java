package info.return0.topdown;

@FunctionalInterface
public interface Parser<T>  {
    public Result<T> parse(CharacterSequence source);
}
