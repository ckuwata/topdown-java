package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.example.minilang.syntax.SVariableRef;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class VariableRef implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Identifier().parse(source).then(n -> {
			return Result.success(new SVariableRef(n));
		});
	}
}
