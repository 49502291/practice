
public class AddBinary {

 public String addBinary(String a, String b) {
       
	    int carry = 0;
        String result="";
              
        
        if(a.length()>b.length())
        {
        	String temp =a;
        	a = b;
        	b = temp;
        }
        
        int i = a.length()-1;
        int j = b.length()-1;
        
        
        while(i>=0)
        {
            int ai = a.charAt(i)-'0';
            int bi = b.charAt(j)-'0';
            
            result = (ai+bi+carry)%2 + result;
            carry = (ai + bi + carry)/2;
            
            i--;
            j--;
        }

        while(j>=0)
        {
            int bi = b.charAt(j)-'0';
            
            result = (bi+carry)%2 + result;
            carry = (bi + carry)/2;
            j--;
        }
        
        if(carry ==1)
         result = 1 + result;	
        
        return result;
   }
 
 
 public static void main(String[] args) throws Exception {
	 AddBinary a = new AddBinary();
	 a.addBinary("0101", "11");
	 
 }
}
