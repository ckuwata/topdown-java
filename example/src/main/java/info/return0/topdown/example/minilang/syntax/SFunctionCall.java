package info.return0.topdown.example.minilang.syntax;

import java.util.Collection;

public class SFunctionCall implements Syntax {
	public String name;
	public Collection<Syntax> args;
	
	public SFunctionCall(String name, Collection<Syntax> args) {
		this.name = name;
		this.args = args;
	}
}
