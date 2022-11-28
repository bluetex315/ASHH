package hfu.parsers;

import hfu.BenchmarkInfo;
import hfu.parsers.cfg.EBNF;
import hfu.parsers.cfg.MyParseTree;
import hfu.parsers.cfg.MyTokenizer;
import hfu.parsers.cfg.pep.Category;
import hfu.parsers.cfg.pep.LLParser;
import hfu.parsers.cfg.pep.LLParser.ParseTree;


abstract public class LLCFGParser<P extends BenchmarkInfo> implements CFGParser<P>{

	public P parse(String file) {
		System.out.println("LLCFG "+file);
		//get EBNF description of the file to parse
		EBNF ebnf = getEBNF();
		//create a token stream for the input file
		MyTokenizer tokenizer = new MyTokenizer(file);
		
		//actual parsing using the LL Parser (Own implementation, supported by PEP library)...
		LLParser parser = new LLParser(ebnf.getGrammar());
		System.out.println("LLCFG23 ok");
		ParseTree tree = parser.parse(tokenizer, new Category("S"));
		System.out.println("LLCFG25 ok");
		return interpret(MyParseTree.produce(tree));
	}


}
