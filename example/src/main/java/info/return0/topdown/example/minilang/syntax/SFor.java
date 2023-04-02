package info.return0.topdown.example.minilang.syntax;

public class SFor implements Syntax {
	public final Syntax variable;
	public final Syntax condition;
	public final Syntax assign;
	public final Syntax statement;
	public SFor(Syntax variable, Syntax condition, Syntax assign, Syntax statement) {
		super();
		this.variable = variable;
		this.condition = condition;
		this.assign = assign;
		this.statement = statement;
	}
}
