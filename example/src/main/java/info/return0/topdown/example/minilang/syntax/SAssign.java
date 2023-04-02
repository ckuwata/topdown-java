package info.return0.topdown.example.minilang.syntax;

public class SAssign implements Syntax {
	public final String name;
	public final Syntax expression;
	public SAssign(String name, Syntax expression) {
		super();
		this.name = name;
		this.expression = expression;
	}
	
}
