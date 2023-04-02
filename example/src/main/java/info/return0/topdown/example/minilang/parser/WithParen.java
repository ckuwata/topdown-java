package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class WithParen implements Parser<Syntax> {
	private Parser<Syntax>  delegate;
	
	public WithParen(Parser<Syntax> delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Token("(").parse(source).then(_1 -> {
			return this.delegate.parse(source).then(r -> {
				return new Token(")").parse(source).then(_2 -> {
					return Result.success(r);
				});
			});
		});
	}
	
}
