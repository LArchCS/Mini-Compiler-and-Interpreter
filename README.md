# Mini-Compiler-and-Interpreter
The project imagines an arbitary script language with an arbitary grammer. It asks user for inputs for variables, then follows the arbitary syntax and compute the result.

## Lexer
Transform a raw script input string into a sequence of lexemes
#### Lexeme
Stores a single lexeme from a statement of the script. Contains a type and a value.
#### Lexer
Parses a raw script statement into a series of Lexemes.

## Parser
Takes the Lexemes you created in part one, and parse them into a tree
#### ParseTreeNode
A single node in the tree constructed to store script statements. Each1. ParseTreeNode stores a Lexeme
#### Parser
Takes a Lexer and organizes the Lexemes it returns into a tree, made up of ParseTreeNodes.

## Interpreter
The Interpreter class takes in a List of ParseTreeNodes through its constructor and has a single method you will need to implement called evaluate.
The output of the evaluate method should be a Map<String, Double> containing the final values of all the variables defined in the program.

## Compiler
The Compiler class, like the Interpreter, takes in a list of ParseTreeNodes in its constructor. However, the Compiler has a compile method which will return a List of VMInstruction instances.
The compiler transform the given script statement into low level AL language. The AL language here uses 3 registers, and has commands SET, STORE, ADD, MUL, POW, (etc.). More details see the VM package.
