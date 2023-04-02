package info.return0.topdown;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class CharacterSequence {
	/**
	 * Source text
	 */
	private String source;

	/**
	 * If true, parser will automatically remove encountered white spaces and line
	 * breaks
	 */
	private boolean autoRemoveWS;

	private ParserHook hook;

	private Set<String> whitespaceChars;

	private boolean ignoreHook;

	private int position;

	private Stack<Integer> cursor;

	private int length;

	private CharacterSequence() {
		cursor = new Stack<>();
		whitespaceChars = Set.of(" ", "\\r", "\\n", "\\t");
		hook = new ParserHook();
		ignoreHook = false;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		CharacterSequence ret = new CharacterSequence();

		public Builder source(String source) {
			ret.source = source;
			ret.length = source.length();
			ret.position = 0;
			return this;
		}

		public Builder autoRemoveWS(boolean autoRemoveWS) {
			ret.autoRemoveWS = autoRemoveWS;
			return this;
		}

		public Builder hook(ParserHook hook) {
			ret.hook = hook;
			return this;
		}

		public Builder whitespaceChars(Set<String> whitespaceChars) {
			ret.whitespaceChars = whitespaceChars;
			return this;
		}
		
		public CharacterSequence build() {
			assert ret.source != null;
			if (ret.autoRemoveWS) {
				ret.hook = ret.hook.combine(new Regex("[" + ret.whitespaceChars.stream().collect(Collectors.joining("")) + "]+"));
			}
			return ret;
		}
	}

	public Result<String> next() {
		if (this.length <= this.position) {
			return Result.end(EndOfBuffer.instance);
		}
		this.position++;
		return Result.success(this.current());
	}

	public Result<String> back() {
		if (this.position == 0) {
			Result.success(this.current());
		}
		this.position--;
		return Result.success(this.current());
	}

	public String current() {
		return this.source.substring(position, position + 1);
	}

	public boolean isLineBreak(int pos) {
		if (pos >= this.length) {
			return false;
		}
		return this.source.substring(pos, pos + 1).matches("\r|\n");
	}

	public String rest() {
		return this.source.substring(this.position);
	}

	public boolean isEof() {
		return this.position >= this.length;
	}

	public void pushCursor() {
		this.cursor.push(this.position);
	}

	public void popCursor() {
		this.cursor.pop();
	}

	public void rollbackPosition() {
		this.position = this.cursor.pop();
	}

	public void forward(int length) {
		if (this.hook != null && !this.ignoreHook) {
			this.ignoreHook = true;
			this.hook.accept(this);
			this.ignoreHook = false;
		}
		this.position += length;
		if (this.hook != null && !this.ignoreHook && !this.isEof()) {
			this.ignoreHook = true;
			this.hook.accept(this);
			this.ignoreHook = false;
		}
	}

	public void suspendHook() {
		this.ignoreHook = true;
	}
	
	public void resumeHook() {
		this.ignoreHook = false;
	}

}
