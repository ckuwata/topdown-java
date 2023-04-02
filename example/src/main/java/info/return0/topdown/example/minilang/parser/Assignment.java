package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SAssign;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class Assignment implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Identifier().parse(source).then(name -> {
			return new Token("=").parse(source).then(_1 -> {
				return new Expression().parse(source).then(e -> {
					return Result.success(new SAssign(name, e));
				});
			});
		});
	}
	
}
