package info.return0.topdown.example.minilang.parser;

import java.math.BigDecimal;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Regex;
import info.return0.topdown.Result;
import info.return0.topdown.example.minilang.syntax.SNumber;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class NumberEx implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Regex("-?(([1-9][0-9]+)|([0-9]))(\\.[0-9]+)?").parse(source).then(n -> {
			return Result.success(new SNumber(new BigDecimal(n)));
		});
	}

}
