package info.return0.topdown.example.minilang.parser;

import org.apache.commons.math3.util.Pair;

import info.return0.topdown.CharacterSequence;
import info.return0.topdown.Many;
import info.return0.topdown.Parser;
import info.return0.topdown.Result;
import info.return0.topdown.Split;
import info.return0.topdown.Token;
import info.return0.topdown.example.minilang.syntax.SFunctionDef;
import info.return0.topdown.example.minilang.syntax.Syntax;

public class FunctionDefinition implements Parser<Syntax> {
	class Parameter implements Parser<Pair<String, String>> {

		@Override
		public Result<Pair<String, String>> parse(CharacterSequence source) {
			return new Identifier().parse(source).then(pname -> {
				return new Token(":").parse(source).then(tname -> {
					return Result.success(new Pair<>(pname, tname));
				});
			});
		}
		
	}

	@Override
	public Result<Syntax> parse(CharacterSequence source) {
		return new Identifier().parse(source).then(name -> {
			return new Token("(").parse(source).then(_1 -> {
				return new Split<>(new Parameter(), new Token(","), false).parse(source).then(params -> {
					return new Token(")").parse(source).then(_2 -> {
						return new Token(":").parse(source).then(_3 -> {
							return new Identifier().parse(source).then(retType -> {
								return new Token("{").parse(source).then(_4 -> {
									return new Many<>(new Statement()).parse(source).then(statements -> {
										return new Token("}").parse(source).then(_5 -> {
											return Result.success(new SFunctionDef(name, retType, params, statements));
										});
									});
								});
							});
						});
					});
				});
			});
		});
	}

}
