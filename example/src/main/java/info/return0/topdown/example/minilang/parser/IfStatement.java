package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Option;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SIf;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class IfStatement implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Token("if").parse(source).then(_1 -> {
			return new WithParen((s) -> {
				return new Expression().parse(s);
			}).parse(source).then(condition -> {
				return new Statement().parse(source).then(ifStatement -> {
					var el = new Option<>(new Token("else"), null).parse(source);
					if (el.getValue() != null) {
						return new Statement().parse(source).then(elseStatement -> {
							return Result.success(new SIf(condition, ifStatement, elseStatement));
						});
					}
					return Result.success(new SIf(condition, ifStatement, null));
				});
			});
		});
	}

}
