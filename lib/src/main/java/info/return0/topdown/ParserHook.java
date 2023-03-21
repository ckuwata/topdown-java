package info.return0.topdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ParserHook {
	private Collection<Parser<?>> hooks;
	
	public ParserHook(Parser<?> ...hooks) {
		this.hooks = new ArrayList<>();
		this.hooks.addAll(Arrays.asList(hooks));
	}
	
	public ParserHook(Collection<Parser<?>> hooks) {
		this.hooks = hooks;
	}
	
	public ParserHook combine(ParserHook another) {
		var ret = new ParserHook();
		ret.hooks.addAll(this.hooks);
		ret.hooks.addAll(another.hooks);
		return ret;
	}
	
	
	public ParserHook combine(Parser<?> another) {
		var ret = new ParserHook();
		ret.hooks.addAll(this.hooks);
		ret.hooks.add(another);
		return ret;
	}

	public void accept(CharacterSequence cs) {
		for (var hook : this.hooks) {
			hook.parse(cs);
		}
	}

}