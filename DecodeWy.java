
public class DecodeWy {

	
	 public int numDecodings(String s) {
	        
		   if (s==null || s.length() ==0)
	            return 0;
	        if(s.length() ==1)
	        {
	        	if (s.charAt(0) =='0')
	        		return 0;
	        	else 
	        		return 1;
	        }
	        
	            
	        
	        int[] count = new int[s.length()];
	        
	        count[0] = 1;
	        
	        int twodigits = (s.charAt(0) -'0')*10 + s.charAt(1)-'0';
	       
	        if( twodigits <=26 && twodigits >10)
	          count[1] = 2;
	        else
	          count[1] =1;
	          
	          
	        for(int i = 2; i<s.length(); i++)
	        {
	            
	            if(s.charAt(i-1) == '0' && s.charAt(i) =='0')
	                return 0;
	            
	            twodigits = (s.charAt(i-1) -'0')*10 + s.charAt(i)-'0';
	            
	            if(twodigits <=26 && twodigits >=10)
	            {
	             	if(s.charAt(i) =='0')
	                    count[i] = count[i-2];
	             	else
	             		count[i] = count[i-1] + count[i-2];
	            }
	            else
	                count[i] = count[i-1];
	        }
	        
	        return count[s.length()-1];
	        	
	       
	        
	        
	    }
	 
	
	 
	 public static void main(String[] args) throws Exception {
		 DecodeWy FUCK =  new DecodeWy();
		 String str= "102";
		 int count = FUCK.numDecodings(str);
	 }
}
