package HW6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Q1 {

	private Map<String, Integer> dictionary;
	private Map<String, Integer> testdata;
	int size = 0; 
	int testsize = 0;
	
	public void ReadFile(String filename) throws Exception
	{
		String line = null;
		
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	    
	    dictionary = new HashMap<String ,Integer>();
	    
	    
	    while ((line = bufferedReader.readLine()) != null)
	   {
	       String[] S = line.split("\\s+");
	       
	       for(String s: S)
	       {
	    	   if(dictionary.containsKey(s))
	    		   dictionary.put(s, dictionary.get(s)+1);
	    	   else
	    		   dictionary.put(s, 1);
	    	   
	    	   size++;
	       }
	
	   }
	    
	    bufferedReader.close();
	}
	
	public double calLikelihood()
	{
		
		double result = 0.0;
		
		for(Map.Entry<String, Integer> entry : dictionary.entrySet())
		{
			double p = (double)entry.getValue() / size; 
			
			result += -1 * p * (Math.log(p) / Math.log(2)) ;
		}
		
			
		return result;
		
	}
	
	public void ReadTestFile(String filename) throws Exception
	{
		String line = null;
		
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	    
	    testdata = new HashMap<String, Integer>();
	    
	    while ((line = bufferedReader.readLine()) != null)
	   {
	       String[] S = line.split("\\s+");
	       
	       for(String s: S)
	       {
	    	   if(testdata.containsKey(s))
	    		   testdata.put(s, testdata.get(s)+1);
	    	   else
	    		   testdata.put(s, 1);
	    	   
	    	   testsize++;
	       }
	
	   }
	    
	    bufferedReader.close();
			
	}
	
	public double calTestLL()
	{
		double result = 0.0;
		
		for(Map.Entry<String, Integer> entry : testdata.entrySet())
		{
			double pt = (double)entry.getValue() / testsize;
			double pm = 0.0;
			
			if(dictionary.containsKey(entry.getKey()))
				pm = (double)dictionary.get(entry.getKey()) / size;
			
			if(pm == 0)
				continue;
			
			result += -1 * pt * (Math.log(pm) / Math.log(2)) ;			
			
		}
		
		return result;
		
	}
	
	
	 public static void main(String[] args) throws Exception {

		 	Q1 test= new Q1();
		 	
		 	test.ReadFile("./src/HW6/training");
		 	
		 	double trainLH = test.calLikelihood();
		 	
		 	System.out.println(trainLH);
		 	
		 	double trainPP =  Math.pow(2, trainLH);
		 	
		 	System.out.println(trainPP);
		 	
		 	
		 	test.ReadTestFile("./src/HW6/testing");
		 	
		 	double testLH =test.calTestLL();
		 	
		 	System.out.println(testLH);
		 	
		 	double testPP = Math.pow(2, testLH);
		 	
		 	System.out.println(testPP);
		 	
		 
	 }
	 
	
	
	
}
