package info.return0.topdown.example.minilang.syntax;

public class SOperator implements Syntax {
	public final String operator;
	public final Syntax left;
	public final Syntax right;
	public SOperator(String operator, Syntax left, Syntax right) {
		super();
		this.operator = operator;
		this.left = left;
		this.right = right;
	}
	
}
