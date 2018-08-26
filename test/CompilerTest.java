import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import task.compiler.Compiler;
import task.lexer.Lexer;
import task.parser.ParseTreeNode;
import task.parser.Parser;
import task.vm.VM;

public class CompilerTest {
	private static List<ParseTreeNode> parse(String s) {
		Parser p = new Parser(new Lexer(s));
		List<ParseTreeNode> ptns = p.parse();

		// if you need to, print out each ParseTreeNode here for debugging

		return ptns;
	}

	@Test
	public void test1() {
		String s = "x = 5 + 6;";
		Compiler c = new Compiler(parse(s));
		VM vm = new VM();


		Map<String, Double> values = vm.evaluate(c.compile());

		assertEquals(11.0, values.get("x"), 0.01);
	}

	@Test
	public void test2() {
		String s = "x = 5 + (6 * 4);";
		Compiler c = new Compiler(parse(s));
		VM vm = new VM();


		Map<String, Double> values = vm.evaluate(c.compile());

		assertEquals(29.0, values.get("x"), 0.01);
	}

	@Test
	public void test3() {
		String s = "x = (5 - 4) + (6 * 4);";
		Compiler c = new Compiler(parse(s));
		VM vm = new VM();


		Map<String, Double> values = vm.evaluate(c.compile());

		assertEquals(25.0, values.get("x"), 0.01);
	}

	@Test
	public void test4() {
		String s = "x = (5 - 4) + (6 * 4);\ny = x * 4;";
		Compiler c = new Compiler(parse(s));
		VM vm = new VM();


		Map<String, Double> values = vm.evaluate(c.compile());

		assertEquals(25.0, values.get("x"), 0.01);
		assertEquals(100.0, values.get("y"), 0.01);

	}

	@Test
	public void test5() {
		String s = "x = (5 - 4) + (6 * 4); y = x * 4; z = x + (y * (2^2));";
		Compiler c = new Compiler(parse(s));
		VM vm = new VM();


		Map<String, Double> values = vm.evaluate(c.compile());

		assertEquals(25.0, values.get("x"), 0.01);
		assertEquals(100.0, values.get("y"), 0.01);
		assertEquals(425.0, values.get("z"), 0.01);


	}

	@Test
	public void test6() {
		String s = "x = (5 - 4) + (6 * 4); y = x * 4; z = x + (y * (2^2)); y = 5;";
		Compiler c = new Compiler(parse(s));
		VM vm = new VM();


		Map<String, Double> values = vm.evaluate(c.compile());

		assertEquals(25.0, values.get("x"), 0.01);
		assertEquals(5.0, values.get("y"), 0.01);
		assertEquals(425.0, values.get("z"), 0.01);


	}

	@Test
	public void test7() {
		String s = "x = ? + 5;";

		InputStream sysin = System.in;
		PrintStream sysout = System.out;
		System.setIn(new ByteArrayInputStream("6\n".getBytes()));
		System.setOut(new PrintStream(new ByteArrayOutputStream()));

		Compiler c = new Compiler(parse(s));
		VM vm = new VM();

		Map<String, Double> values = vm.evaluate(c.compile());

		System.setIn(sysin);
		System.setOut(sysout);

		assertEquals(11.0, values.get("x"), 0.01);



	}
}
