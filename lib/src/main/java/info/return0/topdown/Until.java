package info.return0.topdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Until implements Parser<String> {
	private Collection<String> targets;
	
	public Until(String... targets) {
		assert targets.length > 0;
		this.targets= Arrays.asList(targets);
	}
	
	@Override
	public Result<String> parse(CharacterSequence source) {
		var posList = new ArrayList<Integer>();
		var rest = source.rest();
		for (var t : targets) {
			int pos = rest.indexOf(t);
			posList.add(pos);
		}
		posList.sort((a, b) -> a - b);
		var ret = posList.get(0);
		if (ret == -1) {
			source.forward(rest.length());
			return Result.success(rest);
		}
		source.forward(ret);
		return Result.success(rest.substring(0, ret));
	}

}
