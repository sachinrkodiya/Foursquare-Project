
package Sample;

public class hello {
  	public String sayHello() {
		return "Hello";
	}
	public static void main(String[] args) {  
	    //defining a variable 
    	hello obj = new hello();
	    System.out.println(obj.sayHello());	
	    int number=13;  
	    //Check if the number is divisible by 2 or not  
	    if(number%2==0){  
	        System.out.println("even number");  
	    }else{  
	        System.out.println("odd number");  
	    }  
	}

}
