package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Choice;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SFor;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class ForStatement implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Token("for").parse(source).then(_1 -> {
			return new Token("(").parse(source).then(_2 -> {
				return Choice.ofTry(new VariableDefinition(), new Statement.With(new Assignment())).parse(source)
						.then(variable -> {
							return new Expression().parse(source).then(condition -> {
								return new Token(";").parse(source).then(_3 -> {
									return new Assignment().parse(source).then(assign -> {
										return new Token(")").parse(source).then(_4 -> {
											return new Statement().parse(source).then(statement -> {
												return Result.success(new SFor(variable, condition, assign, statement));
											});
										});
									});
								});
							});
						});
			});
		});

	}

}
