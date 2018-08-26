import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import task.lexer.LexemeType;
import task.lexer.Lexer;
import task.parser.ParseTreeNode;
import task.parser.Parser;

public class ParserTest {

	@Test
	public void test1() {
		String s = "x = 5 + 6;";
		Parser p = new Parser(new Lexer(s));

		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		assertEquals(1, nodes.size());
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("x", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("+", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals(5.0, root.getRight().getLeft().getLexeme().getValueAsNumber(), 0.01);
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLexeme().getType());
		assertEquals(6.0, root.getRight().getRight().getLexeme().getValueAsNumber(), 0.01);


	}

	@Test
	public void test2() {
		String s = "x = 5 + (6 *   2);";
		Parser p = new Parser(new Lexer(s));

		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		assertEquals(1, nodes.size());
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("x", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals(5.0, root.getRight().getLeft().getLexeme().getValueAsNumber(), 0.01);
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLexeme().getType());


	}

	@Test
	public void test3() {
		String s = "x = 5 + (6 *   2); y = 5 + 6;";
		Parser p = new Parser(new Lexer(s));

		List<ParseTreeNode> nodes = p.parse();

		assertEquals(2, nodes.size());

		ParseTreeNode root = nodes.get(0);
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("x", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals(5.0, root.getRight().getLeft().getLexeme().getValueAsNumber(), 0.01);
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLexeme().getType());


		root = nodes.get(1);
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("y", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("+", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals(5.0, root.getRight().getLeft().getLexeme().getValueAsNumber(), 0.01);
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLexeme().getType());
		assertEquals(6.0, root.getRight().getRight().getLexeme().getValueAsNumber(), 0.01);

	}



	@Test
	public void test4() {
		String s = "l = (5 + (6 - 3)) * ((2 / 4) * 4);";
		Parser p = new Parser(new Lexer(s));

		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		assertEquals(1, nodes.size());
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("l", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getRight().getLexeme().getType());


		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLexeme().getType());


	}

	@Test
	public void test5() {
		String s = "l = (5 / (3^2)) * ((8 * 2) / (4^4));";
		Parser p = new Parser(new Lexer(s));

		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(null, root.getLexeme().getValueAsString());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("l", root.getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLexeme().getType());
		assertEquals("/", root.getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getLeft().getLexeme().getType());
		assertEquals("3", root.getRight().getLeft().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getLeft().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals("/", root.getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("*", root.getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals("8", root.getRight().getRight().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("4", root.getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("4", root.getRight().getRight().getRight().getRight().getLexeme().getValueAsString());


	}

	@Test
	public void test6() {
		String s = "l = ((5*5*5) / (3^(2*1))) * (((8/2) * 2) / (4^4));";
		Parser p = new Parser(new Lexer(s));

		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(null, root.getLexeme().getValueAsString());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("l", root.getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLexeme().getType());
		assertEquals("/", root.getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals("*", root.getRight().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getLeft().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLeft().getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLeft().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getRight().getLeft().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLeft().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getRight().getRight().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLeft().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getLeft().getLexeme().getType());
		assertEquals("3", root.getRight().getLeft().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getRight().getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLeft().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("2", root.getRight().getLeft().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("1", root.getRight().getLeft().getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals("/", root.getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("*", root.getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals("/", root.getRight().getRight().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLeft().getLeft().getLexeme().getType());
		assertEquals("8", root.getRight().getRight().getLeft().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLeft().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getLeft().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("4", root.getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("4", root.getRight().getRight().getRight().getRight().getLexeme().getValueAsString());
	}


	@Test
	public void test7() {
		String s = "myVar = 5 * 6 - 3 + 2 / 8 ^ 2;";
		Parser p = new Parser(new Lexer(s));

		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);


		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(null, root.getLexeme().getValueAsString());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("myVar", root.getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals("-", root.getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("6", root.getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getLexeme().getType());
		assertEquals("+", root.getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("3", root.getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("/", root.getRight().getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getRight().getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("8", root.getRight().getRight().getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getRight().getRight().getRight().getRight().getLexeme().getValueAsString());

	}


}
