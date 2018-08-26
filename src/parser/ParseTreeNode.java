package task.parser;

import task.lexer.*;

public class ParseTreeNode {
	private Lexeme value;
	private ParseTreeNode left;
	private ParseTreeNode right;




	public ParseTreeNode(Lexeme value) {
		this.value = value;
	}

	public ParseTreeNode getLeft() {
		return left;
	}

	public void setLeft(ParseTreeNode left) {
		this.left = left;
	}
	public ParseTreeNode getRight() {
		return right;
	}
	public void setRight(ParseTreeNode right) {
		this.right = right;
	}

	public Lexeme getLexeme() {
		return value;
	}



	public void toJUnit(String path) {
		System.out.print("assertEquals(LexemeType." + this.getLexeme().getType() + ", root");
		System.out.println(path + ".getLexeme().getType());");

		String val = (this.getLexeme().getValueAsString() != null ?
				"\"" + this.getLexeme().getValueAsString() + "\""
				: "null");
		System.out.print("assertEquals(" + val + ", ");
		System.out.println("root" + path + ".getLexeme().getValueAsString());");

		if (getLeft() != null)
			getLeft().toJUnit(path + ".getLeft()");

		if (getRight() != null)
			getRight().toJUnit(path + ".getRight()");
	}

	@Override
	public String toString() {
		return "digraph G {\n" + toString("") + "\n}";
	}


	private String toString(String path) {
		StringBuilder toR = new StringBuilder();
		String myLabel = getLexeme().getType().toString() + path;

		String label = getLexeme().getValueAsString();
		if (label == null && getLexeme().getType() == LexemeType.EQUALS)
			label = "=";

		toR.append(myLabel + " [ label=\"" + label + "\" ];\n");


		if (getLeft() != null) {
			String newPath = path + "L";
			toR.append(myLabel
					+ " -> "
					+ getLeft().getLexeme().getType().toString() + newPath
					+ ";\n");
			toR.append(getLeft().toString(newPath));
		}

		if (getRight() != null) {
			String newPath = path + "R";
			toR.append(myLabel
					+ " -> "
					+ getRight().getLexeme().getType().toString() + newPath
					+ ";\n");
			toR.append(getRight().toString(newPath));
		}

		return toR.toString();
	}
}
