package info.return0.topdown.example.minilang.syntax;

import java.util.Collection;

public class SStatements implements Syntax {
	public final Collection<Syntax> statements;

	public SStatements(Collection<Syntax> statements) {
		super();
		this.statements = statements;
	}
	
	
}
