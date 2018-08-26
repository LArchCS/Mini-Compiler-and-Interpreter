package task.parser;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import task.lexer.*;

public class Parser {
	private final Iterator<Lexeme> lexer;

	public Parser(Iterator<Lexeme> lexemes) {
		lexer = lexemes;
	}

	public List<ParseTreeNode> parse() {
		ArrayList<ParseTreeNode> tree_nodes = new ArrayList<ParseTreeNode>();
		while (lexer.hasNext()) {
			Lexeme variable = lexer.next();
			Lexeme equals = lexer.next();
			List<Lexeme> expression = new ArrayList<Lexeme>();
			while (expression.size() == 0 || !expression.get(expression.size() - 1).getType().equals(LexemeType.SEMICOLON)) {
				expression.add(lexer.next());
			}
			expression.remove(expression.size() - 1);
			ParseTreeNode toR = new ParseTreeNode(equals);
			toR.setLeft(new ParseTreeNode(variable));
			toR.setRight(evalExpr(expression));
			tree_nodes.add(toR);
		}
		return tree_nodes;
	}

	private ParseTreeNode evalExpr(List<Lexeme> expression) {
		if (expression.size() == 0) {
			return null;
		}
		if (expression.size() == 1) {
			return new ParseTreeNode(expression.get(0));
		}
		
		ParseTreeNode left, right;
		Lexeme operator;
		if (expression.get(0).getType().equals(LexemeType.LEFT_PAREN)) {
			int rightParen = findMatchingParen(expression);
			left = evalExpr(expression.subList(1, rightParen));
			if (rightParen == expression.size() - 1) {
				return left;
			}
			operator = expression.get(rightParen + 1);
			right = evalExpr(expression.subList(rightParen + 2, expression.size()));
		} else {
			left = evalExpr(expression.subList(0, 1));
			operator = expression.get(1);
			right = evalExpr(expression.subList(2, expression.size()));
		}
		
		ParseTreeNode toR = new ParseTreeNode(operator);
		toR.setLeft(left);
		toR.setRight(right);;
		return toR;
	}

	private int findMatchingParen(List<Lexeme> expression) {
		int stack = 1;
		for (int i = 1; i < expression.size(); i++) {
			if (expression.get(i).getType().equals(LexemeType.LEFT_PAREN)) {
				stack += 1;
			}
			if (expression.get(i).getType().equals(LexemeType.RIGHT_PAREN)) {
				stack -= 1;
				if (stack == 0) {
					return i;
				}
			}
		}
		return -1;
	}
}
