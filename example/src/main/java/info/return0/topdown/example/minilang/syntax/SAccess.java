package info.return0.topdown.example.minilang.syntax;

import java.util.Collection;

public class SAccess implements Syntax {
	public final Syntax expression;
	public final Collection<Syntax> successor;
	
	public static class IndexAccess implements Syntax  {
		public final Syntax expression;
		
		public IndexAccess(Syntax expression) {
			this.expression = expression;
		}
	}
	
	public static class FieldAccess implements Syntax  {
		public final String name;
		
		public FieldAccess(String name) {
			this.name = name;
		}
	}
	
	public SAccess(Syntax expression, Collection<Syntax> successor) {
		this.expression = expression;
		this.successor = successor;
	}	
}