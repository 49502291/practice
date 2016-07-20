import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Anagram {

	  public List<String> anagrams(String[] strs) {
	      
		  Map<String, List<String>> map = new HashMap<String,List<String>>();
		  
		  for(String s : strs)
		  {
			  char [] arr =s.toCharArray();
			  Arrays.sort(arr);
			  
			  String key = new String(arr);
			  
			  if(!map.containsKey(key))
				  map.put(key, new ArrayList<String>());
			  
			  map.get(key).add(s);
		  }
		  
		  List<String> list = new ArrayList<String>();
		  
		  for(Map.Entry<String, List<String>> entry : map.entrySet())
		  {
			  if(entry.getValue().size()>1)
				  list.addAll(entry.getValue());
			  
		  }
		  
		  return list;
		  
		  
		  
	  }
}
