package info.return0.topdown;

public class ParsingError implements StopParsing {
	private String reason;
	
	public ParsingError(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String getReason() {
		return this.reason;
	}
	
	@Override
	public boolean isError() {
		return true;
	}
	
	@Override
	public String toString() {
		return this.reason;
	}

}
