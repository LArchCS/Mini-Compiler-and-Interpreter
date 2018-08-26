
import java.util.*;

import task.compiler.Compiler;
import task.interpreter.Interpreter;
import task.lexer.Lexer;
import task.parser.ParseTreeNode;
import task.parser.Parser;
import task.vm.VM;

public class TestSelf {
	public static void main(String[] args) {
		//lexer(); System.out.println();
		//parser(); System.out.println();
		interpreter(); System.out.println();
		compile(); System.out.println();
	}

	private static List<ParseTreeNode> parse(String s) {
		Parser p = new Parser(new Lexer(s));
		List<ParseTreeNode> ptns = p.parse();
		return ptns;
	}

	public static void compile() {
		String s = "x = ((\t5\r*4)/2) / 10;\n\t y=x*2;";
		Compiler c = new Compiler(parse(s));
		VM vm = new VM();
		Map<String, Double> values = vm.evaluate(c.compile());
		System.out.println(values);
	}

	public static void interpreter() {
		String s = "x = ((? - 4) + (? * 4))* (2 - ?); y = ? * ?; y = ?/1;z = 6 - x + (y * (2^ (1.0 + ?))); ";
		Parser p = new Parser(new Lexer(s));
		List<ParseTreeNode> ptns = p.parse();
		Interpreter interpreter = new Interpreter(ptns);
		Map<String, Double> values = interpreter.evaluate();
		System.out.println(values);
	}

	public static void parser() {
		String s = "x = 5 + (6 *   2);";
		Parser p = new Parser(new Lexer(s));
		System.out.println(p);
		List<ParseTreeNode> ptns = p.parse();
		for (int i = 0; i < ptns.size(); i++) {
			ParseTreeNode ptn = ptns.get(i);
			System.out.println(ptn.toString());
		}
	}

	public static void lexer() {
		Lexer l = new Lexer("x = 5 + (6 *   2);");
		while (l.hasNext()) {
			System.out.println(l.next());
		}
		System.out.println("END");
	}
}
