package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.ParsingError;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SString;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class StringEx implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		source.suspendHook();
		try {
			if (!source.current().contentEquals("\"")) {
				return Result.end(new ParsingError("not a string"));
			}
			source.next();
			var sb = new StringBuilder();
			boolean escaped = false;
			boolean ended = false;
			while (!ended) {
				var c = source.current();
				if (escaped) {
					switch (c) {
					case "t":
						sb.append("\t");
						break;
					case "n":
						sb.append("\n");
						break;
					case "r":
						sb.append("\r");
						break;
					case "b":
						sb.append("\b");
						break;
					case "\"":
						sb.append("\"");
						break;
					default:
						return Result.end(new ParsingError("Invalid character: \\" + c));
					}
					escaped = false;
				} else {
					switch (c) {
					case "\\":
						escaped = true;
						break;
					case "\"":
						ended = true;
						break;
					default:
						sb.append(c);
					}
				}
				source.next();
			}
			return Result.success(new SString(sb.toString()));
		} finally {
			source.resumeHook();
		}
	}

}
