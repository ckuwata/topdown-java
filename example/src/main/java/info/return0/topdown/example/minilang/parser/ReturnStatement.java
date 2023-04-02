package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SReturn;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class ReturnStatement implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Token("return").parse(source).then(_1 -> {
			return new Expression().parse(source).then(e -> {
				return Result.success(new SReturn(e));
			});
		});
	}
	
}
