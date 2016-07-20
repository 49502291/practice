package HW7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HMM {
	
	private String trainingdata = "";
	private double tranMatrix[][];
	private double emisMatrix[][];
	private double pai[];		
	double alpha_table [][];
	double beta_table [][];
	List<Double> scaling_alpha;
	List<Double> scaling_beta;
	private double gamma_table[][][];
	private double gamma_i[][];
	private String testdata ="";
	
	
	
	public void viterbi(String observes)
	{
		double alpha [][] = new double[2][observes.length()];		
		 double C0 =0;
		    
		 System.out.println(observes.length());
		    for(int i =0; i<2; i++)
		    {
		    	char O1 = observes.charAt(0);
		    	
		    	if(O1 != ' ')
		    	  alpha[i][0] = 1 * emisMatrix[i][O1-'A'];
		    	else
		    	  alpha[i][0] = 0 * emisMatrix[i][26];
			    	
		    	C0 += alpha[i][0];
		    }
		    
		    //scale a1(i)
		    
		    C0 = 1.0/ C0;
		    for(int i = 0; i<2 ;i++)
		    {
		    	alpha[i][0] = C0 *alpha[i][0 ];
		    }
		    
		    
		    for(int t = 1; t<alpha[0].length; t++){
		    	double c_t = 0;
				char Ot = observes.charAt(t);

		    	for(int s =0 ;s<2; s++){

		    		alpha[s][t] = 0;

		    		double d1 = alpha[0][t-1] * tranMatrix[0][s];
		    		double d2 = alpha[1][t-1] * tranMatrix[1][s];
		    		
		    		alpha[s][t] = Math.max(d1, d2);
		    		
		    		if(Ot != ' ')
		    		    alpha[s][t] =alpha[s][t] * emisMatrix[s][Ot-'A'];	
		    		else
		    			alpha[s][t] =alpha[s][t] * emisMatrix[s][26];
		    			    		
		    		c_t += alpha[s][t];
		    	}
		    	
		    	//scale at(i)
		    	
		    	c_t = 1/c_t;
		    	for(int s =0 ;s<2;s++){
		    		alpha[s][t] = c_t * alpha[s][t];
		    	}
		    	
		    }
		    
		    List<Integer> states = new ArrayList<Integer>();
		    
		    for(int i =0; i<alpha[0].length;i++){
		    	
		    	if(alpha[0][i] > alpha[1][i])
		    		states.add(0);
		    	else
		    		states.add(1);
		    }
		    
		    for(int aa :states){
		    	System.out.print(aa);
		    }

		
		
	}
	
	public void readtest(String filename) throws Exception{
        
		String line = null;
		
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	    	    
	    while ((line = bufferedReader.readLine()) != null)
	   {
	       
		  testdata += line;
	
	   }
	    
	    bufferedReader.close();
	    
	    String temp = this.testdata.toUpperCase();
		temp = temp.replaceAll("[^A-Z\\s]", "");

//		return temp;
		
		alpha_table = new double[2][temp.length()];
		Forward(temp);
		
	//	for(int i =0 ; i<scaling_alpha.size();i++)
		//	System.out.println(i + " " + scaling_alpha.get(i));
	    System.out.println("test: " +calQ2(scaling_alpha));
		
	}
	
	public void init(String observe)
	{
		tranMatrix = new double[2][2];
		emisMatrix = new double[2][27];
		pai  = new double[2];
		alpha_table = new double[2][observe.length()];
		beta_table = new double[2][observe.length()];
		scaling_alpha = new ArrayList<Double>();
		scaling_beta = new ArrayList<Double>();
		gamma_table = new double[observe.length()][2][2];
		gamma_i = new double[observe.length()][2];
		
		pai[1] =1;
		pai[0] =0;
		
		tranMatrix[0][0]= 0.4;
		tranMatrix[0][1] = 0.6;
		tranMatrix[1][0] =0.6;
		tranMatrix[1][1] =0.4;
		
		for(int i = 0;i< 27;i++)
		{
			if(i == 0 || i==4 || i==8 ||i ==14|| i==20){
				emisMatrix[0][i] = 0.1666;
				emisMatrix[1][i] = 0;

			}
			else{
				emisMatrix[0][i] =0;
				emisMatrix[1][i] =0.043;

			}		
		}
		
		emisMatrix[0][26] = 1-0.1666*5;
		emisMatrix[1][26] =1-21*0.043;

	}
	
	
	public void randomini(String observe){
		
		tranMatrix = new double[2][2];
		emisMatrix = new double[2][27];
		pai  = new double[2];
		alpha_table = new double[2][observe.length()];
		beta_table = new double[2][observe.length()];
		scaling_alpha = new ArrayList<Double>();
		scaling_beta = new ArrayList<Double>();
		gamma_table = new double[observe.length()][2][2];
		gamma_i = new double[observe.length()][2];
		
		pai[1] =0;
		pai[0] =1;
		
		tranMatrix[0][0] = Math.random();
		tranMatrix[0][1] = 1- tranMatrix[0][0];
		tranMatrix[1][0] = Math.random();
		tranMatrix[1][1] = 1 - tranMatrix[1][0];
		
		for(int s =0 ;s<2; s++){
			
		double sum = 0;	
		double [] arr = new double[27];
		for(int i = 0 ; i< 26; i++){
			
			arr[i] = Math.random();
			//System.out.println(arr[i]);
		}
		
		arr[26] = 1;
		Arrays.sort(arr);
			
		emisMatrix[s][0] = arr[0];
		
		for(int j =1 ; j<27 ;j++){
			
			emisMatrix[s][j] = arr[j] -arr[j-1];
			sum += emisMatrix[s][j];
		}
		
		//System.out.println(sum);
		
		}
		
		
	}
	
	public void ReadFile(String filename) throws Exception
	{
		String line = null;
		
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	    	    
	    while ((line = bufferedReader.readLine()) != null)
	   {
	       
		 trainingdata += line;
	
	   }
	    
	    bufferedReader.close();
	}
	

	public String clean ()
	{
		String temp = this.trainingdata.toUpperCase();
		temp = temp.replaceAll("[^A-Z\\s]", "");
		
		return temp;
	}
		
	public void Forward(String oberves)
	{
		scaling_alpha.clear();
		
	    double C0 =0;
	    
	    for(int i =0; i<2; i++)
	    {
	    	char O1 = oberves.charAt(0);
	    	
	    	if(O1 != ' ')
	    	  alpha_table[i][0] = pai[i] * emisMatrix[i][O1-'A'];
	    	else
	    	  alpha_table[i][0] = pai[i] * emisMatrix[i][26];
	    	
	//    	System.out.println(  alpha_table[i][0]);
	    	
	    	C0 += alpha_table[i][0];
	    }
		
	    
	    //scale a1(i)
	    
	    C0 = 1.0/ C0;
	    for(int i = 0; i<2 ;i++)
	    {
	    	alpha_table[i][0] = C0 *alpha_table[i][0 ];
	    }
	    scaling_alpha.add(C0);
	    
	    for(int t = 1; t<alpha_table[0].length; t++){
	    	double c_t = 0;
			char Ot = oberves.charAt(t);
			


	    	for(int s =0 ;s<2; s++){

	    		alpha_table[s][t] = 0;
	    		for(int j =0; j<2; j++){
	    			
	    			alpha_table[s][t] += alpha_table[j][t-1] * tranMatrix[j][s];
	   	//		  System.out.println(alpha_table[j][t-1] + " " + tranMatrix[j][s]+ " "+ alpha_table[s][t] );
	    		}
	    		
	    		
	    		
	    		if(Ot != ' ')
	    		{  alpha_table[s][t] =alpha_table[s][t] * emisMatrix[s][Ot-'A'];	
	    	//	  System.out.println(alpha_table[s][t] + " " + emisMatrix[s][Ot-'A']);
	    		}

	    		else
	    			{alpha_table[s][t] =alpha_table[s][t] * emisMatrix[s][26];
	    	//	System.out.println(alpha_table[s][t] + " " + emisMatrix[s][26]);
	    			}

	    		
	    		
	    		c_t += alpha_table[s][t];
	    	}
	    	
	    	//scale at(i)
	    	
	    	c_t = 1/c_t;
	    	for(int s =0 ;s<2;s++){
	    		alpha_table[s][t] = c_t * alpha_table[s][t];
	    	}
	    	
	    	scaling_alpha.add(c_t);
	    }
	    	
	}
	
	public void Backward(String oberves){
		
		scaling_beta.clear();
		  double C0 =0;
		    
		    for(int i =0; i<2; i++)
		    {
		    	char Ot = oberves.charAt(oberves.length()-1);
		    	
		    	if(Ot != ' ')
		    		beta_table[i][beta_table[0].length-1] = pai[i] * emisMatrix[i][Ot-'A'];
		    	else
		    		beta_table[i][beta_table[0].length-1] = pai[i] * emisMatrix[i][26];
		    	
		    	C0 += beta_table[i][beta_table[0].length-1];
		    }
		
		    //scale betaT(i)
		    
		    C0 = 1.0/ C0;
		    for(int i = 0; i<2 ;i++)
		    {
		    	beta_table[i][beta_table[0].length-1] = C0 *beta_table[i][beta_table[0].length-1];
		    }
		    
		    scaling_beta.add(C0);
		    
		    for( int t = beta_table[0].length-2; t >=0; t--){
		     	double c_t = 0;
				char Ot = oberves.charAt(t+1);
		    	
		    	for(int s = 0; s<2 ;s++){
		    		
		    		beta_table[s][t] = 0;
		    		for(int j =0; j<2; j++){
		    			
		    			if(Ot != ' ')
		    			beta_table[s][t] += tranMatrix[s][j] *emisMatrix[j][Ot-'A']*beta_table[j][t+1];
		    			else
			    	    beta_table[s][t] += tranMatrix[s][j] *emisMatrix[j][26]*beta_table[j][t+1];

		    		}
		    		
		    		c_t += beta_table[s][t];
		    	}
		    	
		    	//scale beta t(t)
		      	c_t = 1/c_t;
		    	for(int s =0 ;s<2;s++){
		    		beta_table[s][t] = c_t * beta_table[s][t];
		    	}
		    	
		    	scaling_beta.add(c_t);
		    }
		  	
	}
	
	 public double calQ2(List<Double> scaling){
		 
		 double loglikelihood =0;
		 
		 for(double d : scaling){
			 
			 loglikelihood -= Math.log(d);
		 }
		 
		 return loglikelihood/scaling.size();
	 }
	 
	 
	 public void ForwardBackward(String observes) {
		 
		 
		 // Compute gamma table
		 for(int t = 0; t<observes.length()-1; t++){
			 
			 double scale = 0;
			 int index = 0;
			 
			 char Ot = observes.charAt(t+1);
 			 if(Ot != ' ')
 				 index = Ot- 'A';
 			 else
 				 index =26;
			 
			 for(int i = 0; i<2 ;i++){
				 
				 for(int j =0; j<2 ; j++){
					 
					 scale += alpha_table[i][t] * tranMatrix[i][j] * emisMatrix[j][index]* beta_table[j][t+1];
				 }
			 }
			 
			 for(int i =0;i<2;i++){
				 gamma_i[t][i] = 0;
				 
				 for(int j = 0; j<2; j++){
					 gamma_table[t][i][j]  = alpha_table[i][t] * tranMatrix[i][j] * emisMatrix[j][index]* beta_table[j][t+1] /scale;
					 gamma_i[t][i]  += gamma_table[t][i][j];
				 }
			 }
			 
		 }
		 
		 
		 //Re-estimate A,B
		 for(int i = 0; i <2 ; i++){
			 for(int j = 0; j<2 ; j++){
				 double numerator = 0.0d;
				 double denominator = 0.0d;
				 
				 for(int t =0; t<observes.length()-1; t++){
					 numerator += gamma_table[t][i][j];	
					 denominator += gamma_i[t][i];
				 }
				 
				 tranMatrix[i][j] = numerator/denominator;
				 
			 }
		 }
		 

		 //Re-estimate bi(j)
		 for(int i = 0; i<2; i++){
			 for(int j =0; j<27; j++){
				 double numerator = 0.0d;
				 double denominator = 0.0d;
				 int index = 0;
				 
				 for(int t =0; t<observes.length()-1 ;t++){
					 
					 char Ot = observes.charAt(t);
		 			 if(Ot != ' ')
		 				 index = Ot- 'A';
		 			 else
		 				 index =26;
					 
					 if(index == j)
						 numerator += gamma_i[t][i];
					 
					 denominator +=gamma_i[t][i];
				 }
				 
				 emisMatrix[i][j] = numerator /denominator;
			 }
			 
		 }
		 
	
		 		 
	 }
	 
	 public void Iterate(String observes){
		 
		 Forward(observes);
		 Backward(observes);
		 ForwardBackward(observes);
		 
		 double oldloglh = calQ2(scaling_alpha);
		 int it = 0;
		 
		 while(true){
			 
			 it++;
			 
			 System.out.println( oldloglh);

			 
			 Forward(observes);
			 Backward(observes);
			 ForwardBackward(observes);
			 
			 double loglh = calQ2(scaling_alpha);
			 
			 if(Math.abs(oldloglh - loglh) < 0.0001){
//				  for(int i = 0; i <2 ; i++){
//					  double[] temp = (double[])emisMatrix[i].clone();
//					  Arrays.sort(temp);
//					  System.out.println(i + ": ");
//					  for(int j =0; j<27;j++)
//					  {
//						 System.out.println(temp[j]);
//						 for(int k = 0; k<27; k++){
//							 if(temp[j] == emisMatrix[i][k])
//								 System.out.println(k);
//						 }
//					  }
//				  }
				  break;
			 }
			 else
				 oldloglh = loglh;
			 
			 
		 }
		 
		 
	 }
	
	 public static void main(String[] args) throws Exception {
		 
		 HMM test = new HMM();
		 test.ReadFile("./src/HW7/training");
		 String result = test.clean();
//		 test.init(result);
//		 
//		 
//		 test.Forward(result);
//		 System.out.println(test.calQ2(test.scaling_alpha));
//
//		 test.Backward(result);
//		 System.out.println(test.calQ2(test.scaling_beta));
		 
		 test.randomini(result);
		 test.Iterate(result);
		 
//		 String testdata = test.readtest("./src/HW7/Q9");
//		 test.viterbi(testdata);
		 
//		 test.readtest("./src/HW7/Q14");
	 }
	
}
