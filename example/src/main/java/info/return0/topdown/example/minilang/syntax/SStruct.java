package info.return0.topdown.example.minilang.syntax;

import java.util.Collection;

import org.apache.commons.math3.util.Pair;

import info.return0.topdown.Result;

public class SStruct implements Syntax {
	public final String name;
	public final Collection<Pair<String, String>> fields;
	public SStruct(String name, Collection<Pair<String, String>> fields) {
		super();
		this.name = name;
		this.fields = fields;
	}
	
}
