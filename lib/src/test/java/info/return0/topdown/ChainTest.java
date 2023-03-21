package info.return0.topdown;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChainTest {
	interface Data {
		
	}
	
	class Node implements Data {
		String val;

		public Node(String val) {
			super();
			this.val = val;
		}
		
		@Override
		public String toString() {
			return val;
		}
	}
	
	class Branch implements Data {
		Data left;
		Data right;
		public Branch(Data left, Data right) {
			super();
			this.left = left;
			this.right = right;
		}
		@Override
		public String toString() {
			return "(" + left.toString() + "," + right.toString() + ")";
		}
	}
	
	class NodeParser implements Parser<Data> {

		@Override
		public Result<Data> parse(CharacterSequence source) {
			return new Regex("[a-z]+").parse(source).then(r -> Result.success(new Node(r)));
		}
		
	}
	
	class BranchParser implements Parser<BiFunction<Data, Data, Data>> {

		@Override
		public Result<BiFunction<Data, Data, Data>> parse(CharacterSequence source) {
			return new Str("|").parse(source).then(s -> {
				return Result.success((l, r) -> new Branch(l, r));
			});
		}
		
	}
	
	class ChainLeftParser implements Parser<Data> {

		@Override
		public Result<Data> parse(CharacterSequence source) {
			return new ChainLeft<>(new NodeParser(), new BranchParser()).parse(source);
		}
		
	}
	
	class ChainRightParser implements Parser<Data> {

		@Override
		public Result<Data> parse(CharacterSequence source) {
			return new ChainRight<>(new NodeParser(), new BranchParser()).parse(source);
		}
		
	}
	
	
	@Test
	public void testLeft() {
		String data = "a|b|c";
		var ret = new ChainLeftParser().parse(CharacterSequence.builder().source(data).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals("((a,b),c)", ret.getValue().toString());
	}
	
	@Test
	public void testRight() {
		String data = "a|b|c";
		var ret = new ChainRightParser().parse(CharacterSequence.builder().source(data).autoRemoveWS(true).build());
		Assertions.assertFalse(ret.failed());
		Assertions.assertEquals("(a,(b,c))", ret.getValue().toString());
	}
}
