package task.lexer;

import java.util.Iterator;

public class Lexer implements Iterator<Lexeme> {

	private static final Set<Character> OPERATORS = new HashSet<>(Arrays.asList('+', '-', '*', '/', '^'));

	private final String toLex;

	private int offset;
	private Lexeme nxt;

	public Lexer(String toLex) {
		offset = 0;
		this.toLex = toLex;
		next();
	}


	private Lexeme swap(Lexeme n) {
		Lexeme old = nxt;
		nxt = n;
		return old;
	}

	private Lexeme swapAndInc(Lexeme n) {
		offset++;
		return swap(n);
	}

	@Override
	public boolean hasNext() {
		return nxt != null;
	}

	@Override
	public Lexeme next() {
		// chomp whitespace
		while (offset < toLex.length() && Character.isWhitespace(toLex.charAt(offset)))
			offset++;

		if (offset >= toLex.length())
			return swap(null);

		if (Character.isAlphabetic(toLex.charAt(offset)))
			return swap(readVariable());


		if (Character.isDigit(toLex.charAt(offset)))
			return swap(readNumber());


		if (toLex.charAt(offset) == '(')
			return swapAndInc(new Lexeme(LexemeType.LEFT_PAREN));

		if (toLex.charAt(offset) == ')')
			return swapAndInc(new Lexeme(LexemeType.RIGHT_PAREN));

		if (toLex.charAt(offset) == '=')
			return swapAndInc(new Lexeme(LexemeType.EQUALS));

		if (toLex.charAt(offset) == ';')
			return swapAndInc(new Lexeme(LexemeType.SEMICOLON));

		if (toLex.charAt(offset) == '?')
			return swapAndInc(new Lexeme(LexemeType.USER_INPUT));
		
		if (OPERATORS.contains(toLex.charAt(offset))) {
			return swapAndInc(new Lexeme(LexemeType.OPERATOR, String.valueOf(toLex.charAt(offset))));
		}
		
		return null;
	}

	private Lexeme readNumber() {
		StringBuilder toR = new StringBuilder();
		while (offset < toLex.length() && (Character.isDigit(toLex.charAt(offset)) || toLex.charAt(offset) == '.')) {
			toR.append(toLex.charAt(offset));
			offset++;
		}
		return new Lexeme(LexemeType.NUMBER, toR.toString());
	}

	private Lexeme readVariable() {
		StringBuilder toR = new StringBuilder();
		while (offset < toLex.length()) {
			if (!Character.isAlphabetic(toLex.charAt(offset)))
				break;
			toR.append(toLex.charAt(offset));
			offset++;
		}
		return new Lexeme(LexemeType.VARIABLE, toR.toString());
	}
}
