package info.return0.topdown.example.minilang.syntax;

import java.math.BigDecimal;

public class SNumber implements Syntax {
	public final BigDecimal value;
	
	public SNumber(BigDecimal value) {
		this.value = value;
	}

}
