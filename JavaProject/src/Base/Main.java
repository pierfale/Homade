package Base;

import Compile.Compile;
import Compile.Parser;
import Exception.IncorrectFormatSource;
import Processor.Processor;

public class Main {

	public static void main(String [] args) {
		
		Processor.init();
		Processor.start(Compile.exec(args));
	}
}
