package com.project.ambition;
import java.util.ArrayList;

public class Tokenizer {

	public static String[] tokenize(String s)
	{
		ArrayList<Character> delimeters = new ArrayList<Character>();

		delimeters.add('/');
		delimeters.add('.');
		delimeters.add('*');
		delimeters.add(':');
		delimeters.add(';');
		delimeters.add('<');
		delimeters.add('>');
		delimeters.add('{');
		delimeters.add('}');
		delimeters.add('_');
		delimeters.add('^');
		delimeters.add('%');
		delimeters.add('$');
		delimeters.add('#');
		delimeters.add('@');
		delimeters.add('!');
		delimeters.add('-');
		delimeters.add('+');
		delimeters.add('\'');
		delimeters.add('"');
		delimeters.add(',');
		delimeters.add('&');
		delimeters.add('?');
		delimeters.add('|');
		
		String str = "";
		
        // calculate number of delimiter characters
        for (int i = 0; i < s.length(); i++)
        {
        	 if (!delimeters.contains(s.charAt(i)))
             	str+=s.charAt(i);
        }
          // System.out.println(str);     	
        String[] tokens = str.split("\\s+");
        
        return tokens;
        
//        ArrayList<String> tokenList = new ArrayList<String>();
//        // print results for testing
//        for (int i = 0; i < tokens.length; i++)
//        {
//        	System.out.println(tokens[i]);
//        	tokenList.add(tokens[i]);
//        	System.out.println(tokenList);
//        	
//        }
//        return tokenList;
	}
	
}
