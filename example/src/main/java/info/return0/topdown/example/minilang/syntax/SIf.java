package info.return0.topdown.example.minilang.syntax;

public class SIf implements Syntax {
	public final Syntax condition;
	public final Syntax ifStatement;
	public final Syntax elseStatement;
	public SIf(Syntax condition, Syntax ifStatement, Syntax elseStatement) {
		super();
		this.condition = condition;
		this.ifStatement = ifStatement;
		this.elseStatement = elseStatement;
	}
}
