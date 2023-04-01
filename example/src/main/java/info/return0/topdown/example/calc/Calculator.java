package info.return0.topdown.example.calc;

import java.util.function.BiFunction;

import info.return0.topdown.ChainLeft;
import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Choice;
import info.return0.topdown.Parser;
import info.return0.topdown.ParsingError;
import info.return0.topdown.Regex;
import info.return0.topdown.Result;
import info.return0.topdown.Str;
import info.return0.topdown.Token;

public class Calculator {
	public Result<Expr> parse(String expr) {
		var seq = CharacterSequence.builder().source(expr).autoRemoveWS(true).build();
		return new CalculatorParser().parse(seq);
	}
	
	public static interface Expr {
		long calc();
	}
	
	class CalculatorParser implements Parser<Expr> {
		@Override
		public Result<Expr> parse(CharacterSequence source) {
			var ret = new ExprParser().parse(source);
			if (!source.isEof()) {
				return Result.end(new ParsingError(source.rest()));
			}
			return ret;
		}
	}

	class ExprParser implements Parser<Expr> {

		@Override
		public Result<Expr> parse(CharacterSequence source) {
			return new ChainLeft<Expr>(new TermParser(), new AddSubParser()).parse(source);
		}

	}

	class AddSubParser implements Parser<BiFunction<Expr, Expr, Expr>> {

		@Override
		public Result<BiFunction<Expr, Expr, Expr>> parse(CharacterSequence source) {
			return Choice.of(new Token("+"), new Token("-")).parse(source).then(op -> {
				return Result.success((l, r) -> {
					return new OperatorNode(op, l, r);
				});
			});
		}
	}

	class MulDivParser implements Parser<BiFunction<Expr, Expr, Expr>> {

		@Override
		public Result<BiFunction<Expr, Expr, Expr>> parse(CharacterSequence source) {
			return Choice.of(new Token("*"), new Token("/")).parse(source).then(op -> {
				return Result.success((l, r) -> {
					return new OperatorNode(op, l, r);
				});
			});
		}
	}

	class TermParser implements Parser<Expr> {

		@Override
		public Result<Expr> parse(CharacterSequence source) {
			return new ChainLeft<Expr>(new FactorParser(), new MulDivParser()).parse(source);
		}
	}

	class NumberParser implements Parser<Expr> {
		private Regex delegate = new Regex("-?(([1-9][0-9]+)|([0-9]))");

		@Override
		public Result<Expr> parse(CharacterSequence source) {
			return delegate.parse(source).then(res -> {
				return Result.success(new NumberNode(Long.parseLong(res)));
			});
		}
	}

	class FactorParser implements Parser<Expr> {

		@Override
		public Result<Expr> parse(CharacterSequence source) {
			var n = new NumberParser().parse(source);
			if (n.failed()) {
				return new Str("(").parse(source).then(o -> {
					return new ExprParser().parse(source).then(ret -> {
						return new Str(")").parse(source).then(c -> Result.success(ret));
					});
				});
			}
			return n;
		}

	}

	class NumberNode implements Expr {
		long val;

		NumberNode(long val) {
			this.val = val;
		}

		@Override
		public long calc() {
			return val;
		}
		
		@Override
		public String toString() {
			return "["+this.val+"]";
		}
	}

	class OperatorNode implements Expr {
		String operator;
		Expr left;
		Expr right;

		public OperatorNode(String operator, Expr left, Expr right) {
			this.operator = operator;
			this.left = left;
			this.right = right;
		}

		@Override
		public long calc() {
			switch (this.operator) {
			case "+":
				return left.calc() + right.calc();
			case "-":
				return left.calc() - right.calc();
			case "*":
				return left.calc() * right.calc();
			case "/":
				return left.calc() / right.calc();
			default:
				throw new RuntimeException();
			}
		}

	}
}
