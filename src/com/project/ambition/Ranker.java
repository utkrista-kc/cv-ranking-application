package com.project.ambition;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Ranker {
			public static ArrayList<Double> getQualificationRank(Integer j_id) throws SQLException
			{
				Double plustwo=2.0;
				Double bachelor = 3.0;
				Double master = 4.0;
				Double count =0.0;
				ArrayList<Double> count_array = new ArrayList<Double>();
				Connection con = DatabaseConnection.getDB();
			 	String query = "SELECT * FROM  applicant_desc where job_id="+j_id;
			 	String query_next = "SELECT * FROM  jobdescription where job_id="+j_id;
			 	Statement st = con.createStatement();
			 	Statement stm = con.createStatement();
			 	ResultSet rs = st.executeQuery(query);
			 	ResultSet rsm = stm.executeQuery(query_next);
			 	while(rsm.next())
			 	{
			 		String qual_req = rsm.getString("qual_req");
			 		 while(rs.next())
			 		 {
			 			String applicant_qual = rs.getString("qualification");
				    	// System.out.println(applicant_qual);
				    	 if(!qual_req.equals(applicant_qual))
				    	 {
				    		 if(applicant_qual.equals("Bachelor"))
				    		 {
					    		 count = bachelor;
					    		 count_array.add(count);
					    	 }
					    	 else if(applicant_qual.equals("+2"))
					    	 {
					    		 count = plustwo;
					    		 count_array.add(count);
					    	 }
					    	 else if(applicant_qual.equals("Master"))
					    	 {
					    		 count = master;
					    		 count_array.add(count);
					    	 }
					    	 else
					    	 {
					    		 count =0.0;
					    		 count_array.add(count);
					    	 }
				    	 }
				    	 else
				    	 {
				    		 count = master;
				    		 count_array.add(count);
				    	 }
			 		 }
			 	}
			    
			      {
			    	
		     }
			     return count_array;
			}
			public static ArrayList<Double> getExperienceValue(Integer j_id) throws SQLException
			{
				Connection con = DatabaseConnection.getDB();
			 	String query = "SELECT * FROM  applicant_desc where job_id="+j_id;
			 	Statement st = con.createStatement();
			 	ArrayList<Double> exper_array = new ArrayList<Double>();
			 	ResultSet rs = st.executeQuery(query);
			 	while(rs.next())
			 	{
			 		Double norm_exp_val =0.0;
			 		Integer experience_val = rs.getInt("experience");
			 		norm_exp_val = (experience_val/5.0);
			 		exper_array.add(norm_exp_val);
			 		
			 	}
				return exper_array;
			 	
			}
			public static void getRank(Integer j_id) throws SQLException
			{
				ArrayList<Double> qualification = new ArrayList<Double>();
				qualification.addAll(getQualificationRank(3));
				 Double[] qualArr = qualification.toArray(new Double[qualification.size()]);
//				 for(int i=0;i<qualArr.length;i++)
//				 {
//					 System.out.println(qualArr[i]);
//				 }
				//Integer qualification [] = new Integer[getQualificationRank(3)];
				ArrayList<Double> experience = new ArrayList<Double>();
				experience.addAll(getExperienceValue(3));
				Double[] expArr = experience.toArray(new Double[experience.size()]);
//				 for(int i=0;i<expArr.length;i++)
//				 {
//					 System.out.println(expArr[i]);
//				 }
				Connection con = DatabaseConnection.getDB();
			 	String query = "SELECT * FROM  applicant_desc where job_id="+j_id;
			 	Statement st = con.createStatement();
			 	ResultSet rs = st.executeQuery(query);
			 	double[] rank = new double[qualArr.length];
			 
			 		for(int i=0;i<(qualArr.length);i++)
			 		{
			 			
			 			while(rs.next())
					 	{
					 		Double cosine = rs.getDouble("cosine_value");
					 		Integer applicant_id = rs.getInt("a_id");
//					 		System.out.println(applicant_id);
					 		rank[i] = 25* qualArr[i] + 45* cosine + 30* expArr[i];
			 				System.out.println("rank of a_id"+applicant_id+"=" + rank[i]);
			 				String update_rank = "update applicant_desc set rank_value ="+rank[i]+ "where a_id="+applicant_id;
		    				Statement stm = con.createStatement();
			    			stm.executeUpdate(update_rank);
			 				break;
					 	}
				 			
			 		}		 				
		 				
			 			
			 		}
			public static void view_Rankedresume() throws SQLException
			{
				Connection con = DatabaseConnection.getDB();
				System.out.println("\n\n\nRanked CVs are:");
			    String sql = "SELECT a_id, job_id from applicant_desc" +
			                   " ORDER BY rank_value DESC";
			    Statement stmt = con.createStatement();
			     ResultSet rs = stmt.executeQuery(sql);
			      while(rs.next()){
			         int id  = rs.getInt("a_id");
			         int j_id = rs.getInt("job_id");
			     
			         System.out.print("Applicant ID: "+id);
			         System.out.println("\n");
			      }
			}
			 		
}
			

