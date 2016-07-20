package HW5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Entropy {

	private List<Double> labels;
	private Map<String, Double> dictionary;
	private List<String> key; 
	private Map<String,Integer> S1,S2;
	private Set<Integer> set;

	Map<String, Double> mymap;


	public void ReadFile(String filename) throws Exception
	{
		String line = null;
		
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	    
	    dictionary = new HashMap<String,Double>();
	    key = new ArrayList<String>();
	    
	    while ((line = bufferedReader.readLine()) != null)
	   {
	       String[] s = line.split("\\s+");
	       dictionary.put(s[0], Double.parseDouble(s[1]));
	       key.add(s[0]);
	   }
	    
	    bufferedReader.close();
	}
	
	public String SelectWord()
	{
		double random = Math.random();
		String s = "";
		
		for(int i =0; i<key.size();i++)
		{
			double probability = dictionary.get(key.get(i));
			random = random -probability;
			
			if(random <= 0)
			{
				s= key.get(i);
				break;
			}
				
		}
		
		return s;		
	}
	
	public void GenerateS1(String filename) throws Exception
	{
	
		//BufferedWriter writer = new BufferedWriter (new FileWriter(filename));	
		S1= new HashMap<String, Integer>();
	    
	    for(int i =0 ; i< 1000000 ; i++)
	    {
	    	String s = SelectWord();
	    	
	    	if(!S1.containsKey(s))
	    		S1.put(s, 1);
	    	else
	    		S1.put(s, S1.get(s)+1);
	    	
	   // 	writer.write(s);
	   // 	writer.write("\n");
	    }
		  
	  //  writer.flush();
		//writer.close();
		  
	}
	
	
	public void Cal()
	{
	    double aaa =0;
		 
	    set= new HashSet<Integer>();
	    
	    for(Map.Entry<String, Integer> entry : S1.entrySet())
		{
	    	double Pt =dictionary.get(entry.getKey());
	    	int i = entry.getValue();
	    	double pm = (double)entry.getValue()/1000000;
	    	double a = -(Math.log(pm) / Math.log(2));
	    	aaa += -1 *(Pt *  (Math.log(pm) / Math.log(2)) );
	    	set.add(entry.getValue());
	    	
		}
		
	    System.out.println(aaa);
	    
	    int v = set.size() +1;
	    v = 1000000 + v;
	    double bbb = 0;
	    
	    for(Map.Entry<String, Integer> entry : S1.entrySet())
	    {
	    	double Pt =dictionary.get(entry.getKey());
	    	int i = entry.getValue();
	    	double pm = (double) (i+1) /v;
	    	
	    	bbb+= -1 *(Pt *  (Math.log(pm) / Math.log(2)) );
	    	
	    }
	    
	    System.out.println(bbb);
	    
	    
	    double ccc = 0;
	    
	    int size = S1.size() +1;
	    
	    double p = 1/(double)size;
	    
	    
	    for(Map.Entry<String, Integer> entry : S1.entrySet())
	    {
	    	double Pt =dictionary.get(entry.getKey());	    	
	    	ccc += -1 *(Pt *  (Math.log(p) / Math.log(2)) );
	    	
	    }
	    
	    System.out.println(ccc);
		
	}
	
	
	 public static void main(String[] args) throws Exception {
		 
		 Entropy test = new Entropy();
		 
		 
		 test.ReadFile("./src/HW5/DATA.txt");
		 
		 test.GenerateS1("./src/HW5/S1.txt");	
		 test.Cal();
	 }
	
	
}
