package info.return0.topdown.example.minilang.parser;

import org.apache.commons.math3.util.Pair;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Many;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SStruct;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class StructureDefinition implements Parser<Syntax> {
	static class Field implements Parser<Pair<String, String>> {

		@Override
		public Result<Pair<String, String>> parse(CharacterSequence source) {
			return new Identifier().parse(source).then(name -> {
				return new Token(":").parse(source).then(_1 -> {
					return new Identifier().parse(source).then(tname -> {
						return new Token(";").parse(source).then(_2 -> {
							return Result.success(new Pair<>(name, tname));
						});
					});
				});
			});
		}

	}

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Token("struct").parse(source).then(_1 -> {
			return new Identifier().parse(source).then(name -> {
				return new Token("{").parse(source).then(_2 -> {
					return new Many<>(new Field()).parse(source).then(fields -> {
						return new Token("}").parse(source).then(_3 -> {
							return Result.success(new SStruct(name, fields));
						});
					});
				});
			});
		});
	}

}
