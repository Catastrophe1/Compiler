package compiler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LexAnalyser {
    ArrayList<Word> wordList = new ArrayList<Word>();
	int wordCount = 0;
	
	public LexAnalyser() {
		
	}
	
    public LexAnalyser(String filePath) throws IOException {
		lexAnalyse(filePath);
		for(Word word: wordList) {
			System.out.printf("%d, %d, %d, %s\n", word.id, word.line, word.type, word.value);
		}
	}
    
    public static boolean isDigit(char ch) {
    	if(ch >= '0' && ch <= '9') {
    		return true;
    	}
    	return false;
    }
    
    public static boolean isLetter(char ch) {
    	if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
    		return true;
    	}
    	return false;
    }
    
    public static boolean isSpace(char ch) {
    	if(ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n') {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public static boolean isInteger(String value) {
    	for(int i = 0;i < value.length();++i) {
    		if(!isDigit((value.charAt(i)))) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static boolean isId(String value) {
		if(!isLetter(value.charAt(0))) {
			return false;
		}
		for(int i = 1;i < value.length();++i) {
			char ch = value.charAt(i);
			if(!isDigit(ch) && !isLetter(ch)) {
				return false;
			}
		}
		return true; 
	}
    
    public void lexAnalyse(String filePath) throws IOException {
    	FileInputStream file = new FileInputStream(filePath);
		BufferedInputStream buffer = new BufferedInputStream(file);
		InputStreamReader input = new InputStreamReader(buffer, "utf-8");
		BufferedReader reader = new BufferedReader(input);
		String str = "";
		int line = 1;
		while((str = reader.readLine()) != null) {
			analyse(str.trim(), line++);
		}
    	reader.close();
    }
    
    public void analyse(String src, int line) {
    	int index = 0;
    	while(index < src.length()) {
    		char temp = src.charAt(index);
    		if(isLetter(temp) || isDigit(temp)) {
    			int startIndex = index;
    			int endIndex = index;
    			while((isLetter(temp) || isDigit(temp)) && ++index < src.length()) {
    				endIndex = index - 1;
    				temp = src.charAt(index);
    			}
    			if(index > startIndex) {
    				--index;
    			}
    			String value = src.substring(startIndex, endIndex + 1);
    			if(Word.isKey(value)) {
    				wordList.add(new Word(++wordCount, line, Word.KEY, value));
    			}
    			else if(isInteger(value)) {
    				wordList.add(new Word(++wordCount, line, Word.INTEGER, value));
    			}
    			else if(isId(value)) {
    				wordList.add(new Word(++wordCount, line, Word.INDENTIFIER, value));
    			}
    			else {
    				wordList.add(new Word(++wordCount, line, Word.UNDEFINE, value));
    			}
    		}
    		else if(temp == '\'') {
    			if(index + 2 >= src.length()) {
    				wordList.add(new Word(++wordCount, line, Word.UNDEFINE, "'"));
    			}
    			if(src.charAt(index + 2) == '\'') {
    				wordList.add(new Word(++wordCount, line, Word.CHARACTER, src.substring(index + 1, index + 2)));
    				index += 2;
    			}
    			else {
    				wordList.add(new Word(++wordCount, line, Word.UNDEFINE, "'"));
    			}
    		}
    		else if(!isSpace(temp)) {
    			int startIndex = index;
    			int endIndex = index;
    			while(!isLetter(temp) && !isDigit(temp) && !isSpace(temp) && ++index < src.length()) {
    				endIndex = index - 1;
    				temp = src.charAt(index);
    			}
    			if(index > startIndex) {
    				--index;
    			}
    			String value = src.substring(startIndex, endIndex + 1);
    			if(Word.isOperator(value)) {
    				wordList.add(new Word(++wordCount, line, Word.OPERATOR, value));
    			}
    			else if(Word.isBoundarySign(value)) {
    				wordList.add(new Word(++wordCount, line, Word.BOUNDARYSIGN, value));
    			}
    			else if(value.equals(";")) {
    				wordList.add(new Word(++wordCount, line, Word.END, value));
    			}
    			else {
    				wordList.add(new Word(++wordCount, line, Word.UNDEFINE, value));
    			}
    		}
    		++index;
    	}
    }

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		LexAnalyser l = new LexAnalyser("C:\\Users\\Raven\\Desktop\\compiler practise\\testProgram");

	}

}
