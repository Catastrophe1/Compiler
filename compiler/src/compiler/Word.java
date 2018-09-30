package compiler;

import java.util.ArrayList;

public class Word {
	public final static int KEY = 0;
	public final static int OPERATOR = 1;
	public final static int INTEGER = 2;
	public final static int CHARACTER = 3;
	public final static int INDENTIFIER = 4;
	public final static int BOUNDARYSIGN = 5;
	public final static int END = 6;
	public final static int UNDEFINE = 7;
	public static ArrayList<String> key = new ArrayList<String>();
	public static ArrayList<String> operator = new ArrayList<String>();
	public static ArrayList<String> boundarySign = new ArrayList<String>();
	static {
		key.add("main");
		key.add("int");
		key.add("char");
		key.add("if");
		key.add("else");
		key.add("while");
		key.add("write");
		key.add("read");
		operator.add(">");
		operator.add("<");
		operator.add(">=");
		operator.add("<=");
		operator.add("==");
		operator.add("!=");
		operator.add("=");
		operator.add("+");
		operator.add("-");
		operator.add("*");
		operator.add("/");
		boundarySign.add("{");
		boundarySign.add("}");
		boundarySign.add("[");
		boundarySign.add("]");
		boundarySign.add("(");
		boundarySign.add(")");
	}
	
	int id;
	int line;
	int type;
	String value;
	boolean flag = true;
	
	public Word() {
		
	}
	
	public Word(int id, int line, int type, String value) {
		this.id = id;
		this.line = line;
		this.type = type;
		this.value = value;
	}
	
	public static boolean isKey(String value) {
		return key.contains(value);
	}
	
	public static boolean isOperator(String value) {
		return operator.contains(value);
	}
	
	public static boolean isBoundarySign(String value) {
		return boundarySign.contains(value);
	}
}








