package task.compiler;

import java.util.*;

import task.lexer.LexemeType;
import task.parser.ParseTreeNode;
import task.vm.VMOp;
import task.vm.VMInstruction;


public class Compiler {

	private final List<ParseTreeNode> ptns;
	private final Map<String, Integer> ramMap;
	private final List<VMInstruction> prgm;
	private final Map<String, VMOp> operators;

	public Compiler(List<ParseTreeNode> ptn) {	
		this.ptns = ptn;
		this.ramMap = new HashMap<String, Integer>();
		this.prgm = new LinkedList<VMInstruction>();
		this.operators = new HashMap<String, VMOp>();
		operators.put("+", VMOp.ADD);
		operators.put("-", VMOp.SUB);
		operators.put("*", VMOp.MUL);
		operators.put("/", VMOp.DIV);
		operators.put("^", VMOp.POW);
	}

	public List<VMInstruction> compile() {
		int num = 0;
		for (ParseTreeNode ptn : ptns) {
			if (!ptn.getLexeme().getType().equals(LexemeType.EQUALS)) continue;
			String value = ptn.getLeft().getLexeme().getValueAsString();
			emit(ptn.getRight(), num);
			prgm.add(new VMInstruction(VMOp.SET, "r0", String.valueOf(num)));
        	prgm.add(new VMInstruction(VMOp.OUTPUT, value, "r0"));
        	ramMap.put(value, num);
        	num += 1;
		}
		return prgm;
	}

	private void emit(ParseTreeNode ptn, int num) {
		// If the node is a NUMBER
		if (ptn.getLexeme().getType().equals(LexemeType.NUMBER)) {
			// set r0 to be that number
			prgm.add(new VMInstruction(VMOp.SET, "r0", ptn.getLexeme().getValueAsString()));
		}
		// If the node is a USER_INPUT node
		else if (ptn.getLexeme().getType().equals(LexemeType.USER_INPUT)) {
			// emit a PROMPT r0 instruction
			prgm.add(new VMInstruction(VMOp.PROMPT, "r0"));
		}
		// If the node is a VARIABLE
		else if (ptn.getLexeme().getType().equals(LexemeType.VARIABLE)) {
			// set r0 to that variable's memory location 
			int location = ramMap.get(ptn.getLexeme().getValueAsString());
			prgm.add(new VMInstruction(VMOp.SET, "r0", String.valueOf(location)));
			// and then emit LOAD r0 r0 (load the value of the variable into r0)
			prgm.add(new VMInstruction(VMOp.LOAD, "r0","r0"));
		}
		// If the node is an OPERATOR
		else if (ptn.getLexeme().getType().equals(LexemeType.OPERATOR)) {
			// emit the code for the left sub-tree (with the same memory offset
			emit(ptn.getLeft(), num);
			prgm.add(new VMInstruction(VMOp.SET, "r1", String.valueOf(num)));
			// save r0 into the memory offset
			prgm.add(new VMInstruction(VMOp.STORE, "r1", "r0"));
			// emit the code for the right sub-tree (with the memory offset + 1)
			emit(ptn.getRight(), num + 1);
			prgm.add(new VMInstruction(VMOp.SET, "r2", String.valueOf(num)));
			// load the value stored at the memory offset into r
			prgm.add(new VMInstruction(VMOp.LOAD, "r1", "r2"));
			// emit the code to perform the operation, storing the result into r0, i.e. ADD r0 r1 r0
			prgm.add(new VMInstruction(operators.get(ptn.getLexeme().getValueAsString()), "r0", "r1", "r0"));
		}
		prgm.add(new VMInstruction(VMOp.SET, "r1", String.valueOf(num)));
		prgm.add(new VMInstruction(VMOp.STORE, "r1", "r0"));
	}
}