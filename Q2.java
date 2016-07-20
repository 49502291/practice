package HW6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q2 {

	Map<String, Integer> dictionary;
	Map<String, Integer> testdata;
	int size = 0; 
	int testsize = 0;
	String[] attributes = {"CC","CD","DT", "EX","IN","JJ","JJR","JJS","MD","NN","NNS","NNP","NNPS","POS","PRP","PRP$","RB",
			"RBR","RP","TO","VB","VBD","VBG","VBN","VBP","VBZ","WDT","WP","WRB"};
	
	private List<List<String>> training;
	private List<List<String>> testing;
	
	public void ReadFile(String filename) throws Exception
	{
		String line = null;
		
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	    
	    dictionary = new HashMap<String ,Integer>();
	    training = new ArrayList<List<String>>();
	    
	    
	    while ((line = bufferedReader.readLine()) != null)
	   {
	       String[] S = line.split("\\s+");
	       List<String> row = new ArrayList<String>();
	       
	       for(String s: S)
	       {
	    	   row.add(s);
	    	   
	    	   if(dictionary.containsKey(s))
	    		   dictionary.put(s, dictionary.get(s)+1);
	    	   else
	    		   dictionary.put(s, 1);
	    	   
	    	   size++;
	       }
	       
	       training.add(row);
	
	   }
	    
	    bufferedReader.close();
	}
	
	
	public void ReadTestFile(String filename) throws Exception
	{
		String line = null;
		
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	    
	    testdata = new HashMap<String, Integer>();
	    testing = new ArrayList<List<String>>();
	    
	    while ((line = bufferedReader.readLine()) != null)
	   {
	       String[] S = line.split("\\s+");
	       List<String> row = new ArrayList<String>();

	       
	       for(String s: S)
	       {
	    	   row.add(s);

	    	   if(testdata.containsKey(s))
	    		   testdata.put(s, testdata.get(s)+1);
	    	   else
	    		   testdata.put(s, 1);
	    	   
	    	   testsize++;
	       }
	       
	       testing.add(row);
	
	   }
	    
	    bufferedReader.close();
			
	}
	
	public double calLikelihood(Map<String,Integer> map, int count)
	{
		
		double result = 0.0;
		
		for(Map.Entry<String, Integer> entry : map.entrySet())
		{
			double p = (double)entry.getValue() / count; 
			
			result += -1 * p * (Math.log(p) / Math.log(2)) ;
		}
		
			
		return result;
		
	}
	
	
	
	public void calCategort1()
	{
		double H = calLikelihood(dictionary, size);
		double d = 0;
		
		List<Vector3<String,String,Double>> list = new ArrayList<Vector3<String,String,Double>>();

		for(String attr : attributes)
		{
			d += H - helper1(attr);
			
			Vector3 v3 = new Vector3<String,String,Double>(attr," ",  H - helper1(attr));
			list.add(v3);
		}
		
		Collections.sort(list, new Comparator<Vector3<String,String,Double>>(){
			
			 @Override
			    public int compare(Vector3<String,String,Double> obj1, Vector3<String,String,Double> obj2) {
			        double p1 = obj1.getV3();
			        double p2 = obj2.getV3();

			        if (p1 < p2) {
			            return 1;
			        } else if (p1 > p2){
			            return -1;
			        } else {
			            return 0;
			        }
			     }	
		});
		
		for(Vector3<String,String,Double> v : list)
		{
			//System.out.println(v.getV1() + " " + v.getV2()  + v.getV3());
			//System.out.println(v.getV1());
			System.out.println(v.getV3());
		}
		
		System.out.println("AVG: " + d/attributes.length);
		
	}
	
	public double helper1(String attr)
	{
		
		Map<String,Integer> yes = new HashMap<String,Integer>();
		Map<String,Integer> no = new HashMap<String,Integer>();
		
		int sizey =0;
		int sizen =0;
		
		for(int i = 0; i<training.size(); i++)
		{
			List<String> row = training.get(i);
			
			for(int j = 1; j<row.size();j++)
			{
				String key = row.get(j);
				
				if(row.get(j-1).equals(attr))
				{
					 if(yes.containsKey(key))
			    		   yes.put(key, yes.get(key)+1);
			    	   else
			    		   yes.put(key, 1);
					 
					 sizey++;
				}
				
				else
				{
					 if(no.containsKey(key))
			    		   no.put(key, no.get(key)+1);
			    	   else
			    		   no.put(key, 1);
					 
					 sizen++;
				}
			}
		}
		
		
		double H1 =  calLikelihood(yes,sizey);
		double H2 =  calLikelihood(no, sizen);
		
		return (double)sizey/(sizey+sizen) * H1 + (double)sizen /(sizey+sizen) * H2;
			
	}
	
	public void calCategort3()
	{
		double H = calLikelihood(dictionary, size);
		List<Vector3<String,String,Double>> list = new ArrayList<Vector3<String,String,Double>>();
		double d =0;
		
		for(int i = 0 ; i< attributes.length; i++)
		{
			for(int j = 0; j<attributes.length; j++)
			{
				double result = H - helper3(attributes[i], attributes[j]);				
				Vector3 v3 = new Vector3<String,String,Double>(attributes[i],attributes[j],result);
				list.add(v3);
				d += result;
			}
		}
		
		Collections.sort(list, new Comparator<Vector3<String,String,Double>>(){
			
			 @Override
			    public int compare(Vector3<String,String,Double> obj1, Vector3<String,String,Double> obj2) {
			        double p1 = obj1.getV3();
			        double p2 = obj2.getV3();

			        if (p1 < p2) {
			            return 1;
			        } else if (p1 > p2){
			            return -1;
			        } else {
			            return 0;
			        }
			     }	
		});
		
		for(Vector3<String,String,Double> v : list)
		{
			System.out.println(v.getV1() + " " + v.getV2() + " " + v.getV3());
			//System.out.println(v.getV1() + " " + v.getV2());
			//System.out.println(v.getV3());
		}
		System.out.println("AVG: " + d/list.size());
		
		
	}
	
	public double helper3(String s1, String s2)
	{
		Map<String,Integer> yes = new HashMap<String,Integer>();
		Map<String,Integer> no = new HashMap<String,Integer>();
		
		int sizey =0;
		int sizen =0;
		
		for(int i = 0; i<training.size(); i++)
		{
			
            List<String> row = training.get(i);
			
			for(int j = 2; j<row.size();j++)
			{
				String key = row.get(j);
				
				if(row.get(j-2).equals(s1) && row.get(j-1).equals(s2))
				{
					 if(yes.containsKey(key))
			    		   yes.put(key, yes.get(key)+1);
			    	   else
			    		   yes.put(key, 1);
					 
					 sizey++;
				}
				
				else
				{
					 if(no.containsKey(key))
			    		   no.put(key, no.get(key)+1);
			    	   else
			    		   no.put(key, 1);
					 
					 sizen++;
				}
				
			}	
		}
		
		double H1 =  calLikelihood(yes,sizey);
		double H2 =  calLikelihood(no, sizen);
		
		return (double)sizey/(sizey+sizen) * H1 + (double)sizen /(sizey+sizen) * H2;
	}
	
	
	
	public void calCategort4()
	{
		double H = calLikelihood(dictionary, size);
		double d =0;
		
        List<Vector3<String,String,Double>> list = new ArrayList<Vector3<String,String,Double>>();
		
		for(int i = 0 ; i< attributes.length; i++)
		{
			for(int j = i+1; j<attributes.length; j++)
			{
				double result = H - helper4(attributes[i], attributes[j]);				
				Vector3 v3 = new Vector3<String,String,Double>(attributes[i],attributes[j],result);
				list.add(v3);
				d+=result;
			}
		}
		
		Collections.sort(list, new Comparator<Vector3<String,String,Double>>(){
			
			 @Override
			    public int compare(Vector3<String,String,Double> obj1, Vector3<String,String,Double> obj2) {
			        double p1 = obj1.getV3();
			        double p2 = obj2.getV3();

			        if (p1 < p2) {
			            return 1;
			        } else if (p1 > p2){
			            return -1;
			        } else {
			            return 0;
			        }
			     }	
		});
		
		for(Vector3<String,String,Double> v : list)
		{
			System.out.println(v.getV1() + " " + v.getV2() + " " + v.getV3());
			//System.out.println(v.getV1() +" "+ v.getV2());
			//System.out.println(v.getV3());
		}
		System.out.println("AVG: " + d/list.size());
		
	}
	
	
	public double helper4(String s1, String s2)
	{
		Map<String,Integer> yes = new HashMap<String,Integer>();
		Map<String,Integer> no = new HashMap<String,Integer>();
		
		int sizey =0;
		int sizen =0;
		
		for(int i = 0; i<training.size(); i++)
		{
			
            List<String> row = training.get(i);
			
			for(int j = 1; j<row.size();j++)
			{
				String key = row.get(j);
				
				if(row.get(j-1).equals(s1) || row.get(j-1).equals(s2))
				{
					 if(yes.containsKey(key))
			    		   yes.put(key, yes.get(key)+1);
			    	   else
			    		   yes.put(key, 1);
					 
					 sizey++;
				}
				
				else
				{
					 if(no.containsKey(key))
			    		   no.put(key, no.get(key)+1);
			    	   else
			    		   no.put(key, 1);
					 
					 sizen++;
				}
			}			
			
		}
		
		double H1 =  calLikelihood(yes,sizey);
		double H2 =  calLikelihood(no, sizen);
		
		return (double)sizey/(sizey+sizen) * H1 + (double)sizen /(sizey+sizen) * H2;
	}
	
	
	
	public void calQ4()
	{
		String s1 = "DT";
		String s2 = "JJ";
		
		Map<String,Integer> yestraining = new HashMap<String,Integer>();
		Map<String,Integer> notraining = new HashMap<String,Integer>();
		
		int sizey =0;
		int sizen =0;
		
		for(int i = 0; i<training.size(); i++)
		{
			
            List<String> row = training.get(i);
			
			for(int j = 1; j<row.size();j++)
			{
				String key = row.get(j);
				
				if(row.get(j-1).equals(s1) || row.get(j-1).equals(s2))
				{
					 if(yestraining.containsKey(key))
						 yestraining.put(key, yestraining.get(key)+1);
			    	   else
			    		   yestraining.put(key, 1);
					 
					 sizey++;
				}
				
				else
				{
					 if(notraining.containsKey(key))
						 notraining.put(key, notraining.get(key)+1);
			    	   else
			    		   notraining.put(key, 1);
					 
					 sizen++;
				}
			}			
			
		}
		
		double H1 =  calLikelihood(yestraining,sizey);
		double H2 =  calLikelihood(notraining, sizen);
		double sizet =sizey+sizen;
		double CHtraining =  (double)sizey/sizet * H1 + (double)sizen /sizet * H2;
		
		System.out.println("Training: " + CHtraining);
		System.out.println("Training PP: " + Math.pow(2, CHtraining));
		
		
		

		Map<String,Integer> yestesting = new HashMap<String,Integer>();
		Map<String,Integer> notesting = new HashMap<String,Integer>();
		
		int sizeyt =0;
		int sizent =0;
		
		for(int i = 0; i<testing.size(); i++)
		{
			
            List<String> row = testing.get(i);
			
			for(int j = 1; j<row.size();j++)
			{
				String key = row.get(j);
				
				if(row.get(j-1).equals(s1) || row.get(j-1).equals(s2))
				{
					 if(yestesting.containsKey(key))
						 yestesting.put(key, yestesting.get(key)+1);
			    	   else
			    		   yestesting.put(key, 1);
					 
					 sizeyt++;
				}
				
				else
				{
					 if(notesting.containsKey(key))
						 notesting.put(key, notesting.get(key)+1);
			    	   else
			    		   notesting.put(key, 1);
					 
					 sizent++;
				}
			}			
			
		}
		
		double d1 = calTestLL(yestesting, yestraining, sizeyt, sizey);
		double d2 = calTestLL(notesting, notraining, sizent, sizen);
	//	double sizech = sizeyt+sizent;
		double CHtesting = (double)sizeyt/testsize * d1 + (double)sizent/testsize *d2;
		
		System.out.println("Testing: " + CHtesting);
		System.out.println("Testing PP: " + Math.pow(2, CHtesting) );
		
		
		
	}
	
	
	public double calTestLL(Map<String, Integer> test, Map<String,Integer> model, int sizetest, int sizemodel)
	{
		double result = 0.0;
		
		for(Map.Entry<String, Integer> entry : test.entrySet())
		{
			double pt = (double)entry.getValue() / sizetest;
			double pm = 0.0;
			
			if(model.containsKey(entry.getKey()))
				pm = (double)model.get(entry.getKey()) / sizemodel;
			
			if(pm == 0)
				continue;
			
			result += -1 * pt * (Math.log(pm) / Math.log(2)) ;			
			
		}
		
		return result;
		
	}
	
	
	
	public void calQ5()
	{
				
		Map<String,Integer> yesLayer1 = new HashMap<String,Integer>();
		Map<String,Integer> noLayer1 = new HashMap<String,Integer>();
		List<String> yLayer1his = new ArrayList<String>();
		List<String> nLayer1his = new ArrayList<String>();
		
		int sizeyl1 =0;
		int sizenl1 =0;
		
		for(int i = 0; i<training.size(); i++)
		{
			
            List<String> row = training.get(i);
			
			for(int j = 2; j<row.size();j++)
			{
				String key = row.get(j);
				
				if(row.get(j-1).equals("DT") || row.get(j-1).equals("JJ"))
				{
					 yLayer1his.add(row.get(j-2) + " "+ row.get(j-1) + " " + key);
					 
					 if(yesLayer1.containsKey(key))
						 yesLayer1.put(key, yesLayer1.get(key)+1);
			    	   else
			    		   yesLayer1.put(key, 1);
					 
					 sizeyl1++;
				}
				
				else
				{
					nLayer1his.add(row.get(j-2) + " "+ row.get(j-1) + " " + key);
					
					 if(noLayer1.containsKey(key))
						 noLayer1.put(key, noLayer1.get(key)+1);
			    	   else
			    		   noLayer1.put(key, 1);
					 
					 sizenl1++;
				}
				
			}	
		}
		
		double H1layer1 =  calLikelihood(yesLayer1,sizeyl1);
		double H2layer2 =  calLikelihood(noLayer1, sizenl1);
		
		
		//Layer2
		
		
        double d1 =0;
		
        List<Vector3<String,String,Double>> list1 = new ArrayList<Vector3<String,String,Double>>();
        String [] attr2 = {"DT", "JJ"};
		
//		for(int i = 0 ; i< attributes.length; i++)
//		{
//			for(String attr : attr2)
//			{				
//				double result = H1layer1 - helperQ5(attributes[i], attr, yLayer1his);				
//				Vector3 v3 = new Vector3<String,String,Double>(attributes[i],attr,result);
//				list1.add(v3);
//				d1+=result;
//			}
//		}
		
		for(int i = 0 ; i< attributes.length; i++)
		{
			for(int j = i+1; j<attributes.length; j++)
			{				
				double result = H1layer1 - helperQ51(attributes[i], attributes[j], yLayer1his);				
				Vector3 v3 = new Vector3<String,String,Double>(attributes[i],attributes[j],result);
				list1.add(v3);
				d1+=result;
			}
		}
		
		
		Collections.sort(list1, new Comparator<Vector3<String,String,Double>>(){
			
			 @Override
			    public int compare(Vector3<String,String,Double> obj1, Vector3<String,String,Double> obj2) {
			        double p1 = obj1.getV3();
			        double p2 = obj2.getV3();

			        if (p1 < p2) {
			            return 1;
			        } else if (p1 > p2){
			            return -1;
			        } else {
			            return 0;
			        }
			     }	
		});
		
		for(Vector3<String,String,Double> v : list1)
		{
			//System.out.println(v.getV1() + " " + v.getV2() + " " + v.getV3());
			//System.out.println(v.getV1() + " " + v.getV2());
			System.out.println(v.getV3());
		}
		System.out.println("AVG: " + d1/list1.size());
		
//		
//		
//		    double d2 =0;
//			
//	        List<Vector3<String,String,Double>> list2 = new ArrayList<Vector3<String,String,Double>>();
//			
//			for(int i = 0 ; i< attributes.length; i++)
//			{
//				for(int j = i+1; j<attributes.length; j++)
//				{				
//					double result = H2layer2 - helperQ51(attributes[i], attributes[j], nLayer1his);				
//					Vector3 v3 = new Vector3<String,String,Double>(attributes[i],attributes[j],result);
//					list2.add(v3);
//					d2+=result;
//				}
//			}
//			
//			
//			Collections.sort(list2, new Comparator<Vector3<String,String,Double>>(){
//				
//				 @Override
//				    public int compare(Vector3<String,String,Double> obj1, Vector3<String,String,Double> obj2) {
//				        double p1 = obj1.getV3();
//				        double p2 = obj2.getV3();
//
//				        if (p1 < p2) {
//				            return 1;
//				        } else if (p1 > p2){
//				            return -1;
//				        } else {
//				            return 0;
//				        }
//				     }	
//			});
//			
//			for(Vector3<String,String,Double> v : list2)
//			{
//				//System.out.println(v.getV1() + " " + v.getV2() + " " + v.getV3());
//				//System.out.println(v.getV1() + " " + v.getV2());
//				System.out.println(v.getV3());
//			}
//			System.out.println("AVG: " + d2/list2.size());
		
	}
	
	
	
	public double helperQ5(String s1, String s2, List<String> list)
	{
		Map<String,Integer> yes = new HashMap<String,Integer>();
		Map<String,Integer> no = new HashMap<String,Integer>();
		
		int sizey =0;
		int sizen =0;
		
	
			for(int j = 0; j<list.size();j++)
			{
				String[] temp = list.get(j).split("\\s+");
				
				if(temp[0].equals(s1) && temp[1].equals(s2))
				{
					 if(yes.containsKey(temp[2]))
			    		   yes.put(temp[2], yes.get(temp[2])+1);
			    	   else
			    		   yes.put(temp[2], 1);
					 
					 sizey++;
				}
				
				else
				{
					 if(no.containsKey(temp[2]))
			    		   no.put(temp[2], no.get(temp[2])+1);
			    	   else
			    		   no.put(temp[2], 1);
					 
					 sizen++;
				}
				
			}	
		
		
		double H1 =  calLikelihood(yes,sizey);
		double H2 =  calLikelihood(no, sizen);
		
		return (double)sizey/list.size() * H1 + (double)sizen /list.size() * H2;
	}
	
	
	public double helperQ51(String s1, String s2, List<String> list)
	{
		Map<String,Integer> yes = new HashMap<String,Integer>();
		Map<String,Integer> no = new HashMap<String,Integer>();
		
		int sizey =0;
		int sizen =0;
		
	
			for(int j = 0; j<list.size();j++)
			{
				String[] temp = list.get(j).split("\\s+");
				
				if(temp[0].equals(s1) || temp[0].equals(s2))
				{
					 if(yes.containsKey(temp[2]))
			    		   yes.put(temp[2], yes.get(temp[2])+1);
			    	   else
			    		   yes.put(temp[2], 1);
					 
					 sizey++;
				}
				
				else
				{
					 if(no.containsKey(temp[2]))
			    		   no.put(temp[2], no.get(temp[2])+1);
			    	   else
			    		   no.put(temp[2], 1);
					 
					 sizen++;
				}
				
			}	
		
		
		double H1 =  calLikelihood(yes,sizey);
		double H2 =  calLikelihood(no, sizen);
		
		return (double)sizey/list.size() * H1 + (double)sizen /list.size() * H2;
	}

	
	
	
	public void LlPPQ5()
	{
		//layer1 
		List<String> yLayer1his = new ArrayList<String>();
		List<String> nLayer1his = new ArrayList<String>();
		

		
		for(int i = 0; i<training.size(); i++)
		{
			
            List<String> row = training.get(i);
			
			for(int j = 2; j<row.size();j++)
			{
				String key = row.get(j);
				
				if(row.get(j-1).equals("DT") || row.get(j-1).equals("JJ"))
				{
					 yLayer1his.add(row.get(j-2) + " "+ row.get(j-1) + " " + key);
										 
				}
				
				else
				{
					nLayer1his.add(row.get(j-2) + " "+ row.get(j-1) + " " + key);
					
				}
				
			}	
		}
		
		//layer2
		Map<String,Integer> yes1 = new HashMap<String,Integer>();
		Map<String,Integer> no1 = new HashMap<String,Integer>();
		
		int sizey1 =0;
		int sizen1 =0;
		
	
			for(int j = 0; j<nLayer1his.size();j++)
			{
				String[] temp = nLayer1his.get(j).split("\\s+");
				
				if(temp[1].equals("NN") || temp[1].equals("NNS"))
				{
					 if(yes1.containsKey(temp[2]))
			    		   yes1.put(temp[2], yes1.get(temp[2])+1);
			    	   else
			    		   yes1.put(temp[2], 1);
					 
					 sizey1++;
				}
				
				else
				{
					 if(no1.containsKey(temp[2]))
			    		   no1.put(temp[2], no1.get(temp[2])+1);
			    	   else
			    		   no1.put(temp[2], 1);
					 
					 sizen1++;
				}
				
			}	
		
		
		double H1 =  calLikelihood(yes1,sizey1);
		double H2 =  calLikelihood(no1, sizen1);
		
		
		
		Map<String,Integer> yes2 = new HashMap<String,Integer>();
		Map<String,Integer> no2 = new HashMap<String,Integer>();
		
		int sizey2 =0;
		int sizen2 =0;
		
	
			for(int j = 0; j<yLayer1his.size();j++)
			{
				String[] temp = yLayer1his.get(j).split("\\s+");
				
				if(temp[0].equals("IN") && temp[1].equals("DT"))
				{
					 if(yes2.containsKey(temp[2]))
			    		   yes2.put(temp[2], yes2.get(temp[2])+1);
			    	   else
			    		   yes2.put(temp[2], 1);
					 
					 sizey2++;
				}
				
				else
				{
					 if(no2.containsKey(temp[2]))
			    		   no2.put(temp[2], no2.get(temp[2])+1);
			    	   else
			    		   no2.put(temp[2], 1);
					 
					 sizen2++;
				}
				
			}	
		
		
		double H3 =  calLikelihood(yes2,sizey2);
		double H4 =  calLikelihood(no2, sizen2);
		
		double totalsize = sizey1 + sizen1 + sizey2+ sizen2;
		double ll = (double)sizey1/totalsize * H1 + (double)sizen1 /totalsize * H2 + (double)sizey2/totalsize * H3 + (double)sizen2 /totalsize* H4;
		
		System.out.println("Training: " + ll );
		System.out.println("Training PP: " + Math.pow(2, ll));
		
		
		
		//layer1
		List<String> y1testing = new ArrayList<String>();
		List<String> n1testing = new ArrayList<String>();

		
		for(int i = 0; i<testing.size(); i++)
		{
			
            List<String> row = testing.get(i);
			
			for(int j = 2; j<row.size();j++)
			{
				String key = row.get(j);
				

				if(row.get(j-1).equals("DT") || row.get(j-1).equals("JJ"))
				{
					y1testing.add(row.get(j-2) + " "+ row.get(j-1) + " " + key);
										 
				}
				
				else
				{
					n1testing.add(row.get(j-2) + " "+ row.get(j-1) + " " + key);
					
				}
			}			
			
		}
		
		
		//layer2
		Map<String,Integer> testingyes1 = new HashMap<String,Integer>();
		Map<String,Integer> testingno1 = new HashMap<String,Integer>();
		
		int ty1 =0;
		int tn1 =0;
		
	
			for(int j = 0; j<n1testing.size();j++)
			{
				String[] temp = n1testing.get(j).split("\\s+");
				
				if(temp[1].equals("NN") || temp[1].equals("NNS"))
				{
					 if(testingyes1.containsKey(temp[2]))
						 testingyes1.put(temp[2], testingyes1.get(temp[2])+1);
			    	   else
			    		   testingyes1.put(temp[2], 1);
					 
					 ty1++;
				}
				
				else
				{
					 if(testingno1.containsKey(temp[2]))
						 testingno1.put(temp[2], testingno1.get(temp[2])+1);
			    	   else
			    		   testingno1.put(temp[2], 1);
					 
					 tn1++;
				}
				
			}	
		
		
			double d1 = calTestLL(testingyes1, yes1, ty1, sizey1);
			double d2 = calTestLL(testingno1, no1, tn1, sizen1);
		
			
			Map<String,Integer> testingyes2 = new HashMap<String,Integer>();
			Map<String,Integer> testingno2 = new HashMap<String,Integer>();
			
			int ty2 =0;
			int tn2 =0;
			
		
				for(int j = 0; j<y1testing.size();j++)
				{
					String[] temp = y1testing.get(j).split("\\s+");
					
					if(temp[0].equals("IN") && temp[1].equals("DT"))
					{
						 if(testingyes2.containsKey(temp[2]))
							 testingyes2.put(temp[2], testingyes2.get(temp[2])+1);
				    	   else
				    		   testingyes2.put(temp[2], 1);
						 
						 ty2++;
					}
					
					else
					{
						 if(testingno2.containsKey(temp[2]))
							 testingno2.put(temp[2], testingno2.get(temp[2])+1);
				    	   else
				    		   testingno2.put(temp[2], 1);
						 
						 tn2++;
					}
					
				}	
			
			
				double d3 = calTestLL(testingyes2, yes2, ty2, sizey2);
				double d4 = calTestLL(testingno2, no2, tn2, sizen2);
				
				double testsize = ty1+ty2+tn1+tn2;
				
				double CHtesting = (double)ty1/testsize * d1 + (double)tn1/testsize *d2+ (double)ty2/testsize * d3 + (double)tn2/testsize *d4;
				
				System.out.println("Testing: " + CHtesting);
				System.out.println("Testing PP: " + Math.pow(2, CHtesting) );
	}
	
	
	 public static void main(String[] args) throws Exception {

		 	Q2 test= new Q2();
		 	
		 	test.ReadFile("./src/HW6/training");
		 	
		 	double trainLH = test.calLikelihood(test.dictionary, test.size);
		 	
		 	System.out.println(trainLH);
		 	
		 	double trainPP =  Math.pow(2, trainLH);
		 	
		 	System.out.println(trainPP);
		 	
		 	
		 	test.calCategort1(); 	
		 // test.calCategort3();
		 	//test.calCategort4();
		//	test.calQ5();
		 	
		//	test.ReadTestFile("./src/HW6/testing");
		//		test.calQ4();
		 
		 	
		// 	test.LlPPQ5();
	 }
	
}


class Vector3<T1, T2, T3>{
    protected T1 v1; // attribute1
    protected T2 v2; // attribute2
    protected T3 v3; // H

    public Vector3() {

    }

    public Vector3(T1 v1, T2 v2, T3 v3) {
            this.v1 = v1;
            this.v2 = v2;
            this.v3 =v3;
    }

    public T1 getV1() {
            return v1;
    }

    public T2 getV2() {
            return v2;
    }
    
    public T3 getV3() {
        return v3;
    }
    
   

}