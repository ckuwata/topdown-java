package info.return0.topdown;

public class Failure implements StopParsing {
	public static Failure instance;
	private Failure() {
		
	}
	
	@Override
	public boolean isError() {
		return true;
	}

}
