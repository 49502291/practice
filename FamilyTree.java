import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class FamilyTree {
	
	 public static void main(String args[] ) throws Exception {
	        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
		 
		   List<List<String>> family = new ArrayList<List<String>>();
		   Scanner sc = new Scanner(System.in);
	        int n = sc.nextInt();
	        for(int t = 0; t < n; t++) {
	    	     
	        	List<String> line = new ArrayList<String>();

	        	String temp = sc.next();
	     
	        	String ss[] = temp.split(",");
	        	for(String s: ss){
	        		line.add(s);
	        	}
	        	family.add(line);
	        	   	
	        }
	        
	        String lastline = sc.next();
	        String ss[] = lastline.split(",");
	        String p = ss[0];
	        String q = ss[1];
	        
	        if(p.equals(q))
	        {
	        	System.out.println(p);
	        	return;
	        }
	    
	        
	        HashMap<String,HashSet<String>> myMap = new HashMap<String,HashSet<String>>();
	        
	        for(List<String> list: family){
	        	
        		HashSet<String> set = new HashSet<String>();

	        	for(int i =1 ;i<list.size() ;i++){
	        		
	        	    set.add(list.get(i));
	        	}
	        	
	        	myMap.put(list.get(0), set);
	        }
	        List<String> path1 = new ArrayList<String>();
	        List<String> path2 = new ArrayList<String>();
	        String root ="";
	        
	      	 for(Map.Entry<String, HashSet<String>> entry : myMap.entrySet()){
	      		 
	      		String firstname = entry.getKey();
	 	        boolean flag = true;

	      		 for(Map.Entry<String, HashSet<String>> entry1 : myMap.entrySet()){
	      			 
	      			 HashSet set = entry1.getValue();
	      			
	      			 if(set.contains(firstname))
	      			 {
	      				 flag = false;
	      				 break;
	      			 }
	      			 	      			 
	      		 }
	      		 
	      		 if(true == flag)
	      	     {
	      			 root = firstname;
	      			 break;
	      		 } 
	      
	      	 }
	      	 
	      	 
	      	 findpath(root,p,myMap,path1);
	      	 findpath(root,q,myMap,path2);
	      	 
	      	 
	      	 
	      	 for(int i = 0; i<path1.size() || i<path2.size() ; i++){
	      		 
	      		 if(path1.get(i).equals(path2.get(i)))
	      		 {
	      			 System.out.println(path1.get(i));
	      			 break;
	      		 }
	      	 }
	      	 
	       
	      	 
	    }
   
	 
	 public static boolean  findpath(String root, String target,  HashMap<String,HashSet<String>> map, List<String> path){
		
		 if(root.equals(target)){
			 return true;
		 }
		 
		 for(Map.Entry<String, HashSet<String>> entry : map.entrySet()){
			 
			 boolean flag = false;
			 HashSet set = entry.getValue();
			 if(set.contains(target))
			 {
				 path.add(entry.getKey());
				 flag = findpath(root,entry.getKey(),map,path);
			 }
			 
			 if(flag == true)
				 break;
		 }
		 
		 return true;
	 
  }



}
