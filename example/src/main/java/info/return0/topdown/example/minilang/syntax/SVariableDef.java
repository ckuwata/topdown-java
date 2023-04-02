package info.return0.topdown.example.minilang.syntax;

public class SVariableDef implements Syntax {
	public final String name;
	public final String typeName;
	public final Syntax expression;
	
	public SVariableDef(String name, String typeName, Syntax expression) {
		this.name = name;
		this.typeName = typeName;
		this.expression = expression;
	}
	
}
