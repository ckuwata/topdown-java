package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SVariableDef;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class VariableDefinition implements Parser<Syntax> {
	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Identifier().parse(source).then((varName) -> {
			return new Token(":").parse(source).then(_1 -> {
				return new Identifier().parse(source).then((typeName) -> {
					return new Token("=").parse(source).then(_2 -> {
						return new Expression().parse(source).then((expression) -> {
							return new Token(";").parse(source).then(_3 -> {
								return Result.success(new SVariableDef(varName, typeName, expression));
							});
						});
					});
				});
			});
		});
	}
}
