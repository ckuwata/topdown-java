package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Split;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SFunctionCall;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class FunctionCall implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Identifier().parse(source).then(fname -> {
			return new Token("(").parse(source).then(_1 -> {
				var args = new Split<Syntax>(new Expression(), new Token(","), false).parse(source);
				return new Token(")").parse(source).then(_2 -> {
					return Result.success(new SFunctionCall(fname, args.getValue()));
				});
			});
		});
	}

}
