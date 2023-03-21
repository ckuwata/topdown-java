package info.return0.topdown;

public final class EndOfBuffer implements StopParsing {
	public static EndOfBuffer instance = new EndOfBuffer();
	
	private EndOfBuffer() {
		
	}

	@Override
	public boolean isError() {
		return false;
	}
}
