package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Choice;
import info.return0.topdown.Many;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Try;
import info.return0.topdown.example.minilang.syntax.SAccess;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class Expression implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return Choice.ofTry(new NumberEx(), new Try<>(new StringEx()), new FunctionCall(), new BinaryEx(), new VariableRef()).parse(source).then(ex -> {
			var access = new Many<Syntax>(Choice.of(new ArrayAccess(), new StructureAccess())).parse(source);
			if (access.failed()) {
				return Result.success(ex);
			} else {
				return Result.success(new SAccess(ex, access.getValue()));
			}
		});
	}

}
