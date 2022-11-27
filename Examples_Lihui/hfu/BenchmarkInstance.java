package hfu;

public class BenchmarkInstance<P extends BenchmarkInfo>{
	String file;
	Parser<P> parser;
	
	public BenchmarkInstance(String file, Parser<P> parser){
//		System.out.println("BenchmarkInstance line8 "+file);
		this.file = file;
		this.parser = parser;
	}
	
	P load(){
		return parser.parse(file);
	}
	
}