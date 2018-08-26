package task.interpreter;

import java.util.*;
import task.lexer.LexemeType;
import task.parser.ParseTreeNode;

public class Interpreter {
	
	private final Scanner console;
	private final List<ParseTreeNode> ptns;
	private final Map<String, Double> map;

	public Interpreter(List<ParseTreeNode> ptns) {
		this.ptns = ptns;
		map = new HashMap<String, Double>();
		console = new Scanner(System.in);
	}

	public Map<String, Double> evaluate() {
		for (ParseTreeNode ptn : ptns) {
			if (ptn.getLexeme().getType().equals(LexemeType.EQUALS)) {
				String variable = ptn.getLeft().getLexeme().getValueAsString();
				double value = getTreeValue(ptn.getRight());
				map.put(variable, value);
			}
		}
		return map;
	}

	private double getTreeValue(ParseTreeNode v) {
		LexemeType type = v.getLexeme().getType();
		if (type.equals(LexemeType.NUMBER)) {
			return v.getLexeme().getValueAsNumber();
		}
		String operator = v.getLexeme().getValueAsString();
		double left = getNodeValue(v.getLeft());
		double right = getNodeValue(v.getRight());
		return compute(left, right, operator);
	}

	private double getNodeValue(ParseTreeNode v) {
		double res;
		LexemeType type = v.getLexeme().getType();
		if (type.equals(LexemeType.USER_INPUT)) {
			System.out.print("Input? ");
			res = console.nextDouble();
		} else if (type.equals(LexemeType.VARIABLE)) {
			res = map.get(v.getLexeme().getValueAsString());
		} else {
			res = getTreeValue(v);
		}
		return res;
	}

	private double compute(double left, double right, String operator) {
		double res = 0;
		if (operator.equals("+")) {
			res = left + right;
		} else if (operator.equals("-")) {
			res = left - right;
		} else if (operator.equals("*")) {
			res = left * right;
		} else if (operator.equals("/") && right != 0) {
			res = left / right;
		} else if (operator.equals("^")) {
			res = Math.pow(left, right);
		}
		return res;
	}
}
