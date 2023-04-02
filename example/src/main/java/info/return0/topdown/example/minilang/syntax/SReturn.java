package info.return0.topdown.example.minilang.syntax;

public class SReturn implements Syntax {
	public final Syntax expression;

	public SReturn(Syntax expression) {
		super();
		this.expression = expression;
	}
	
	
}
