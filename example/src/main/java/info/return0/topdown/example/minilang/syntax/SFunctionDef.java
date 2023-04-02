package info.return0.topdown.example.minilang.syntax;

import java.util.Collection;

import org.apache.commons.math3.util.Pair;

public class SFunctionDef implements Syntax {
	public final String name;
	public final String returnType;;
	public final  Collection<Pair<String, String>> params;
	public final  Collection<Syntax> statements;
	public SFunctionDef(String name, String returnType, Collection<Pair<String, String>> params, Collection<Syntax> statements) {
		super();
		this.name = name;
		this.returnType = returnType;
		this.params = params;
		this.statements = statements;
	}
}
