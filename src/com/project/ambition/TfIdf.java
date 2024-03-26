package com.project.ambition;
import java.util.List;

public class TfIdf 
{
    
    public double tfCalculator(String[] totalterms, String termToCheck) 
    {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) 
        {
            if (s.equalsIgnoreCase(termToCheck)) 
            {
                count++;
                
            }
        }
       // System.out.println(count);
        return count / totalterms.length;
    }

    
    public double idfCalculator(List<String[]> allTerms, String termToCheck) 
    {
        double count = 0;
        for (String[] ss : allTerms) 
        {
            for (String s : ss) 
            {
                if (s.equalsIgnoreCase(termToCheck)) 
                {
                    count++;
                    break;
                }
            }
        }
        double value=0;
        if(count >0) {
        	//System.out.println(1+ Math.log(allTerms.size() / count));
        	value = 1+ Math.log(allTerms.size() / count);
        }
                return value;
       
    }
}