package com.project.ambition;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StopWordsRemoval {
	ArrayList<String> stopWords = new ArrayList<String>();
	public StopWordsRemoval() throws IOException {
	
		BufferedReader br = new BufferedReader(new FileReader("stopwords_en.txt"));
		try {
		    String line = br.readLine();

		    while (line != null) {
		        stopWords.add(line);
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
		
	}
	
	public String removeStopWords(String str)
	{
		String[] splited = str.split("\\s+");
		String ret = "";
		for(int i=0;i<splited.length;i++)
		{
			if(!stopWords.contains(splited[i]))
					{
						ret = ret+splited[i]+" ";
						
					}
		}
		return ret;
	}

}
