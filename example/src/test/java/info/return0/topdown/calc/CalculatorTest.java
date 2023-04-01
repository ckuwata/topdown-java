package info.return0.topdown.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import info.return0.topdown.example.calc.Calculator;

public class CalculatorTest {

	@Test
	public void test01() {
		String expr = "1 + 2";
		var ret = new Calculator().parse(expr);;
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals(3, ret.getValue().calc());
	}
	
	@Test
	public void test02() {
		String expr = "1 + 2 a";
		var ret = new Calculator().parse(expr);;
		Assertions.assertTrue(ret.failed());
	}
	
	@Test
	public void test03() {
		String expr = "1 + 2 * 3";
		var ret = new Calculator().parse(expr);;
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals(7, ret.getValue().calc());
	}
	
	@Test
	public void test04() {
		String expr = "(1 + 2) * 3";
		var ret = new Calculator().parse(expr);;
		Assertions.assertFalse(ret.failed(), () -> ret.getReason().toString());
		Assertions.assertEquals(9, ret.getValue().calc());
	}
	
	@Test
	public void test05() {
		String expr = "(1 + 2 * 3";
		var ret = new Calculator().parse(expr);;
		Assertions.assertTrue(ret.failed());
	}
	
	@Test
	public void test06() {
		String expr = "(1 + 2) * (10 - 2)";
		var ret = new Calculator().parse(expr);;
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals(24, ret.getValue().calc());
	}
}
