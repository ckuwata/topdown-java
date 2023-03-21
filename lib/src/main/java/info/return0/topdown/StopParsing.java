package info.return0.topdown;

public interface StopParsing {
	boolean isError();
	
	default String getReason() {
		return "";
	}
}
