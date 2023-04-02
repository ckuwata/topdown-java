package info.return0.topdown.example.minilang.parser;

import java.util.function.BiFunction;

import info.return0.topdown.ChainLeft;
import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Choice;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Str;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SOperator;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class BinaryEx implements Parser<Syntax> {

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new ChainLeft<Syntax>(new TermParser(), new BinOp1Parser()).parse(source);
	}

	class BinOp1Parser implements Parser<BiFunction<Syntax, Syntax, Syntax>> {

		@Override
		public Result<BiFunction<Syntax, Syntax, Syntax>> parse(CharacterSequence source) {
			return Choice
					.of(new Token("+"), new Token("-"), new Token("=="), new Token("!="), new Token("<="),
							new Token("=>"), new Token("<"), new Token(">"), new Token("&&"), new Token("||"))
					.parse(source).then(op -> {
						return Result.success((l, r) -> {
							return new SOperator(op, l, r);
						});
					});
		}
	}

	class MulDivParser implements Parser<BiFunction<Syntax, Syntax, Syntax>> {

		@Override
		public Result<BiFunction<Syntax, Syntax, Syntax>> parse(CharacterSequence source) {
			return Choice.of(new Token("*"), new Token("/")).parse(source).then(op -> {
				return Result.success((l, r) -> {
					return new SOperator(op, l, r);
				});
			});
		}
	}

	class TermParser implements Parser<Syntax> {

		@Override
		public Result<Syntax> parse(CharacterSequence source) {
			return new ChainLeft<Syntax>(new FactorParser(), new MulDivParser()).parse(source);
		}
	}

	class FactorParser implements Parser<Syntax> {

		@Override
		public Result<Syntax> parse(CharacterSequence source) {
			var n = new Expression().parse(source);
			if (n.failed()) {
				return new Str("(").parse(source).then(o -> {
					return new BinaryEx().parse(source).then(ret -> {
						return new Str(")").parse(source).then(c -> Result.success(ret));
					});
				});
			}
			return n;
		}

	}
}