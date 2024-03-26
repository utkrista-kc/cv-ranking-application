package com.project.ambition;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	 private List<String> dictionary = new ArrayList<String>(); 
	  private List<String[]> termsInSkillReq = new ArrayList<String[]>();
	  private List<String[]> termsInApplicantSkill = new ArrayList<String[]>();
	  private List<double[]> tfidf_jobskillVector = new ArrayList<double[]>();
	  private List<double[]> tfidf_skillVector = new ArrayList<double[]>();
	  String[] words = {"java", "php", "c++",
			"ruby","perl","wordpress","full stack developer",
			"python","javascript","sql","c#","asp.net"
			};
	 int i=0;{
 	for(String word  :words)
 	{ 
 		
 		dictionary.add(words[i]);
 		i++;
 		//System.out.println(dictionary);
 	}

	}
	 public void parsejobrequirement(Integer j_id) throws IOException, SQLException
	    {
		 Connection con = DatabaseConnection.getDB();
		 String query = "SELECT * FROM  jobdescription where job_id="+j_id;
		 Statement st = con.createStatement();
		 ResultSet rs = st.executeQuery(query);
		      while(rs.next())
		      {
		    	  	String skillReq = rs.getString("skill");
		    	  	String text = skillReq;
		        	String[] jobskillReq;
		        	StopWordsRemoval stp = new StopWordsRemoval();
		        	text = stp.removeStopWords(text);
		        	Tokenizer tk = new Tokenizer();
		        	jobskillReq= tk.tokenize(text);
		        	for (int i=0;i<jobskillReq.length;i++)
		        	{
		        		
		        		if(!dictionary.contains(jobskillReq[i]))
			        		{
			        			dictionary.add(jobskillReq[i]);
			        			
			        		}
		        	}
		        	termsInSkillReq.add(jobskillReq); 
		      }
//		      for (String[] arr : termsInSkillReq) {
//		            System.out.println(Arrays.toString(arr));
//		        }
	    	
	        
	    }
	 public void parseresumeskill(Integer j_id) throws IOException, SQLException
	 {
		 Connection con = DatabaseConnection.getDB();
		 String query = "SELECT * FROM  applicant_desc where job_id="+j_id;
		 Statement st = con.createStatement();
		 ResultSet rs = st.executeQuery(query);
		     while(rs.next())
		      {
		    	  	String applicant_skill = rs.getString("skill_desc");
		    	  	String text = applicant_skill;
		        	String[] tokenizedapplicant_Skill;
		        	StopWordsRemoval stp = new StopWordsRemoval();
		        	text = stp.removeStopWords(text);
		        	Tokenizer tk = new Tokenizer();
		        	tokenizedapplicant_Skill= tk.tokenize(text);
		        	
		        	for (int i=0;i<tokenizedapplicant_Skill.length;i++)
		        	{
		        		if(!dictionary.contains(tokenizedapplicant_Skill[i]))
			        		{
			        			dictionary.add(tokenizedapplicant_Skill[i]);
			        			
			        		}
		        		
		        	}
		        	
		        	termsInApplicantSkill.add(tokenizedapplicant_Skill);
		      }
//		     for (String[] arr : termsInApplicantSkill) {
//		            System.out.println(Arrays.toString(arr));
//		        }
		   
	 }
	 public void jobtfIdfCalculator() 
	    {
	        double tf; //term frequency
	        double idf; //inverse document frequency
	        double tfidf; //term frequency inverse document frequency        
	        for (String[] docTermsArray : termsInSkillReq) 
	        {
	            double[] tfidfvectors = new double[dictionary.size()];
	            int count = 0;
	            for (String terms : dictionary) 
	            {
	                tf = new TfIdf().tfCalculator(docTermsArray, terms);
	             //   System.out.println(tf);
	                idf = new TfIdf().idfCalculator(termsInSkillReq, terms);
	             //   System.out.println(idf);
	                tfidf = tf * idf;
	               // System.out.println(tfidf);
	                tfidfvectors[count] = tfidf;
	                count++;
	            }
	            tfidf_jobskillVector.add(tfidfvectors);  
	           
	        }
//	        for (double[] arr : tfidf_jobskillVector) {
//	            System.out.println(Arrays.toString(arr));
//	        }
	    }
	 public void resumetfIdfCalculator() 
	    {
	        double tf; //term frequency
	        double idf; //inverse document frequency
	        double tfidf; //term frequency inverse document frequency        
	        for (String[] docTermsArray : termsInApplicantSkill) 
	        {
	            double[] tfidfvectors = new double[dictionary.size()];
	            int count = 0;
	            for (String terms : dictionary) 
	            {
	                tf = new TfIdf().tfCalculator(docTermsArray, terms);
	               // System.out.println(tf);
	                idf = new TfIdf().idfCalculator(termsInApplicantSkill, terms);
	               // System.out.println(idf);
	                tfidf = tf * idf;
	                tfidfvectors[count] = tfidf;
	                count++;
	            }
	            tfidf_skillVector.add(tfidfvectors);  
	           
	        }
//	        for (double[] arr : tfidf_skillVector) {
//	            System.out.println(Arrays.toString(arr));
//	        }
	    }
	 public void getCosineSimilarity(Integer j_id) throws IOException, SQLException 
	 
	    {
		 	Connection con = DatabaseConnection.getDB();
		 	String query = "SELECT * FROM  applicant_desc where job_id="+j_id;
		 	Statement st = con.createStatement();
		 	ResultSet rs = st.executeQuery(query);
		    
		    	 for (int i = 0; i < tfidf_skillVector.size(); i++) 
	        {
		    		 while(rs.next())
				      {
				    	 Integer applicant_id = rs.getInt("a_id");
				    	 Integer jo_id = rs.getInt("job_id");
				    	 System.out.println(applicant_id);
				    	 double [] measures = new double[tfidf_skillVector.size()];
	    				measures[i] = new CosineSimilarity().cosineSimilarity (tfidf_jobskillVector.get(0),  tfidf_skillVector.get(i));
	    				System.out.println(measures[i]);
	    				String update_query = "update applicant_desc set cosine_value="+measures[i]+ "where a_id="+applicant_id;
	    				Statement stm = con.createStatement();
	    				stm.executeUpdate(update_query);
		                System.out.println("between job id "+ jo_id + " skill requirement and resume skill of applicant id" + applicant_id + " =  "+ new CosineSimilarity().cosineSimilarity (tfidf_jobskillVector.get(0),  tfidf_skillVector.get(i)));
		                break;
				      }
	    	
	    }
	    }
	 public static void main(String[] args) throws IOException, SQLException
	 {
		 Main dp = new Main();
		 dp.parsejobrequirement(3);
		 dp.parseresumeskill(3);
		 dp.jobtfIdfCalculator();
		 dp.resumetfIdfCalculator(); 
		 dp.getCosineSimilarity(3);
		 Ranker rk = new Ranker();
		 rk.getRank(3);
		 rk.view_Rankedresume();
	 }
}
