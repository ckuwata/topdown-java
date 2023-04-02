package info.return0.topdown.example.minilang.parser;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Choice;
import info.return0.topdown.Many;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SStatements;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class Statement implements Parser<Syntax> {
	static class With implements Parser<Syntax> {
		Parser<Syntax> delegate;
		With(Parser<Syntax> delegate) {
			this.delegate = delegate;
		}
		
		@Override
		public Result<Syntax> parse(CharacterSequence source) {
			return this.delegate.parse(source).then(r -> {
				return new Token(";").parse(source).then(_1 -> {
					return Result.success(r);
				});
			});
		}
		
	}
	
	static class Nested implements Parser<Syntax> {

		@Override
		public Result<Syntax> parse(CharacterSequence source) {
			return new Token("{").parse(source).then(_1 -> {
				return new Many<>(new Statement()).parse(source).then(s -> {
					return Result.success(new SStatements(s));
				});
			});
		}
		
	}
	
	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return Choice.ofTry(
				new VariableDefinition(), 
				new With(new Expression()),
				new With(new Assignment()),
				new IfStatement(),
				new ForStatement(),
				new ReturnStatement(),
				new Nested()
				).parse(source);
	}

}
