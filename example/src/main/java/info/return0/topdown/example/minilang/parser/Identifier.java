package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Regex;
import info.return0.topdown.Result;

public class Identifier implements Parser<String> {

	@Override
	public Result<String> parse(CharacterSequence source) {
		return new Regex("[a-zA-Z][a-zA-Z0-9]*").parse(source);
	}

}
