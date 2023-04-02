package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SAccess;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class ArrayAccess implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Token("[").parse(source).then(_1 -> {
			return new Expression().parse(source).then(ex -> {
				return new Token("]").parse(source).then(_2 -> {
					return Result.success(new SAccess.IndexAccess(ex));
				});
			});
		});
	}

}
