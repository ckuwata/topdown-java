package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SAccess;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class StructureAccess implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Token(".").parse(source).then(_1 -> {
			return new Identifier().parse(source).then(ex -> {
				return Result.success(new SAccess.FieldAccess(ex));
			});
		});
	}

}
