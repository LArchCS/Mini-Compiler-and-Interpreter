import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import task.interpreter.Interpreter;
import task.lexer.Lexer;
import task.parser.ParseTreeNode;
import task.parser.Parser;

public class InterpreterTest {

	private static List<ParseTreeNode> parse(String s) {
		Parser p = new Parser(new Lexer(s));
		List<ParseTreeNode> ptns = p.parse();

		// if you need to, print out each ParseTreeNode here for debugging
		System.out.println(ptns.get(0).toString());

		return ptns;
	}

	@Test
	public void test1() {
		String s = "x = 5 + 6 + 1;";
		Interpreter i = new Interpreter(parse(s));

		Map<String, Double> values = i.evaluate();

		assertEquals(12.0, values.get("x"), 0.01);
	}

	@Test
	public void test2() {
		String s = "x = 5 + (6 * 4);";
		Interpreter i = new Interpreter(parse(s));

		Map<String, Double> values = i.evaluate();

		assertEquals(29.0, values.get("x"), 0.01);
	}

	@Test
	public void test3() {
		String s = "x = (5 - 4) + (6 * 4);";
		Interpreter i = new Interpreter(parse(s));

		Map<String, Double> values = i.evaluate();

		assertEquals(25.0, values.get("x"), 0.01);
	}

	@Test
	public void test4() {
		String s = "x = (5 - 4) + (6 * 4);\ny = x * 4;";
		Interpreter i = new Interpreter(parse(s));

		Map<String, Double> values = i.evaluate();

		assertEquals(25.0, values.get("x"), 0.01);
		assertEquals(100.0, values.get("y"), 0.01);

	}

	@Test
	public void test5() {
		String s = "x = (5 - 4) + (6 * 4); y = x * 4; z = x + (y * (2^2));";
		Interpreter i = new Interpreter(parse(s));

		Map<String, Double> values = i.evaluate();

		assertEquals(25.0, values.get("x"), 0.01);
		assertEquals(100.0, values.get("y"), 0.01);
		assertEquals(425.0, values.get("z"), 0.01);


	}

	@Test
	public void test6() {
		String s = "x = (5 - 4) + (6 * 4); y = x * 4; z = x + (y * (2^2)); y = 5;";
		Interpreter i = new Interpreter(parse(s));

		Map<String, Double> values = i.evaluate();

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

		Interpreter i = new Interpreter(parse(s));
		Map<String, Double> values = i.evaluate();

		System.setIn(sysin);
		System.setOut(sysout);

		assertEquals(11.0, values.get("x"), 0.01);



	}

	@Test
	public void test8() {
		String s = "x = (5 - 4) + (6 * 4); y = x * 4; z = x + (y * (2^2)); y = 5/2;";
		Interpreter i = new Interpreter(parse(s));

		Map<String, Double> values = i.evaluate();

		assertEquals(25.0, values.get("x"), 0.01);
		assertEquals(2.5, values.get("y"), 0.01);
		assertEquals(425.0, values.get("z"), 0.01);


	}

	public static List<Integer> mergeK(int k, Iterator<Integer> a, Iterator<Integer> b) {
		Integer currA = (a.hasNext() ? a.next() : null);
		Integer currB = (b.hasNext() ? b.next() : null);
		List<Integer> toR = new LinkedList<Integer>();

		while (toR.size() != k) {
			if (currA == null && currB == null) // if there's no more values, we're done
				return toR;

			if (currA == null || (currB != null && currA >= currB)) {
				toR.add(currB);
				currB = (b.hasNext() ? b.next() : null);
			} else if (currB == null || currA < currB) {
				toR.add(currA);
				currA = (a.hasNext() ? a.next() : null);
			}
		}

		return toR;
	}

	@Test
	public void tmp() {
		List<Integer> l1 = new LinkedList<Integer>();
		List<Integer> l2 = new LinkedList<Integer>();

		l1.add(1);
		l1.add(3);
		l1.add(5);

		l2.add(2);
		l2.add(9);

		System.out.println(mergeK(4, l1.iterator(), l2.iterator()));
	}
}
