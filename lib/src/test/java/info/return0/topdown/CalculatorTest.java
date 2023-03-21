package info.return0.topdown;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
	interface Expr {
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

	@Test
	public void test01() {
		String expr = "1 + 2";
		var ret = new CalculatorParser().parse(CharacterSequence.builder().source(expr).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals(3, ret.getValue().calc());
	}
	
	@Test
	public void test02() {
		String expr = "1 + 2 a";
		var ret = new CalculatorParser().parse(CharacterSequence.builder().source(expr).autoRemoveWS(true).build());
		Assertions.assertTrue(ret.failed());
	}
	
	@Test
	public void test03() {
		String expr = "1 + 2 * 3";
		var ret = new CalculatorParser().parse(CharacterSequence.builder().source(expr).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals(7, ret.getValue().calc());
	}
	
	@Test
	public void test04() {
		String expr = "(1 + 2) * 3";
		var ret = new CalculatorParser().parse(CharacterSequence.builder().source(expr).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed(), () -> ret.getReason().toString());
		Assertions.assertEquals(9, ret.getValue().calc());
	}
	
	@Test
	public void test05() {
		String expr = "(1 + 2 * 3";
		var ret = new CalculatorParser().parse(CharacterSequence.builder().source(expr).autoRemoveWS(true).build());
		Assertions.assertTrue(ret.failed());
	}
	
	@Test
	public void test06() {
		String expr = "(1 + 2) * (10 - 2)";
		var ret = new CalculatorParser().parse(CharacterSequence.builder().source(expr).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals(24, ret.getValue().calc());
	}
}
