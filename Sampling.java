package HW3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sampling {

	private Map<String, Double> dictionary;
	private List<String> key; 
	private Map<String,Integer> S1,S2;
	private int freqDoubleton =0;
	private int freqSingleton = 0;
	private double trueproSingleton = 0;
	private double trueproZeroton = 0;
	
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
	
		BufferedWriter writer = new BufferedWriter (new FileWriter(filename));	
		S1= new HashMap<String, Integer>();
	    
	    for(int i =0 ; i< 10000 ; i++)
	    {
	    	String s = SelectWord();
	    	
	    	if(!S1.containsKey(s))
	    		S1.put(s, 1);
	    	else
	    		S1.put(s, S1.get(s)+1);
	    	
	    	writer.write(s);
	    	writer.write("\n");
	    }
		  
	    writer.flush();
		writer.close();
		  
	}
	
	public void CalculateMLE()
	{	
	   int event1 = 0;
	   
	   if(S1.containsKey("of"))
		   event1 = S1.get("of");
			   
	   for(Map.Entry<String, Integer> entry : S1.entrySet())
	   {
		   if(entry.getValue() ==1)
			   freqSingleton ++;
	   }
	   
	   System.out.println("MLE of P(E1) is: "+ (double)event1/10000 );
	   System.out.println("MLE of P(E2) is: "+ (double)freqSingleton/10000);
	   System.out.println("MLE of P(E3) is: 0");
	  	   
	}
	
	public void CalculateGT()
	{
		
		for(Map.Entry<String, Integer> entry: S1.entrySet())
		{
			if(entry.getValue() == 2)
				freqDoubleton ++;
		}
		
		System.out.println("GT of P(E2) is: "+ 2*(double)freqDoubleton/10000);
		System.out.println("GT of P(E3) is: "+ (double)freqSingleton/10000);
	}
	
	public void CalculateTruth()
	{
		
		
		for(Map.Entry<String, Integer> entry: S1.entrySet())
		{
			if(entry.getValue() ==1)
				trueproSingleton += dictionary.get(entry.getKey());
			
			trueproZeroton +=dictionary.get(entry.getKey());
		}
		trueproZeroton = 1-trueproZeroton;
		
		 System.out.println("true probability of E1 is: 0.023375192059322" );
		 System.out.println("true probability of E2 is: "+ trueproSingleton);
		 System.out.println("true probability of E3 is: "+ trueproZeroton);
	}
	
	public void GenerateS2()
	{
	    S2= new HashMap<String, Integer>();
	    
	    for(int i =0 ; i< 10000 ; i++)
	    {
	    	String s = SelectWord();
	    	
	    	if(!S2.containsKey(s))
	    		S2.put(s, 1);
	    	else
	    		S2.put(s, S2.get(s)+1);
	    	
	    }
	    
	    int event1 =0;
	    
	    if(S2.containsKey("of"))
	    	event1 = S2.get("of");
	    
	    int event2=0,event3 =0;
	    
	    for(Map.Entry<String, Integer> entry: S2.entrySet())
		{
	    	if(S1.containsKey(entry.getKey()) && S1.get(entry.getKey()) == 1)
	    		event2 +=S2.get(entry.getKey());
	    	if(!S1.containsKey(entry.getKey()))
	    		event3 +=S2.get(entry.getKey());
		}
	    
	    System.out.println("the number of Event1 is: " + event1);
	    System.out.println("the number of Event2 is: " + event2);
	    System.out.println("the number of Event3 is: " + event3);
	    
	    double expectationE1 = 10000 * 0.023375192059322;
	    double expectationE2 = 10000 * trueproSingleton;
	    double expectationE3 = 10000 * trueproZeroton;
	    
	    System.out.println("the expectation of Event1 is: " + expectationE1);
	    System.out.println("the expextation of Event2 is: " + expectationE2);
	    System.out.println("the expectation of Event3 is: " + expectationE3);
	    
	    
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Sampling sample = new Sampling();
		
		sample.ReadFile("Corpus");
		sample.GenerateS1("S1");
		sample.CalculateMLE();
		sample.CalculateGT();
		sample.CalculateTruth();
		sample.GenerateS2();
		
	}
		
	
}
