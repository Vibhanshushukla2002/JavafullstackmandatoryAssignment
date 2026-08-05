package arr;

public class array {
	
	public static void main(String[] args) {
		
		 int array[]= {10,5,20,8,15,23,44,13};
		 
		 Largest(array);
		 SecondLargest(array);
		 
		 
		 
		
		 
		
			
		 }
	
	

	private static void SecondLargest(int[] array) {
		//int max=array[0];
		int  largest=Integer.MIN_VALUE;
		int second=Integer.MIN_VALUE;
		
		for(int num : array) {
			if(num>largest) {
				second=largest;
				largest=num;
			}else if (num>second && num!=largest) {
				second=num;
			}
		}
		
		
		
		System.out.println("Second largest element is "+second);
		
	}



	private static void Largest(int[] array) {
		// TODO Auto-generated method stub
 int max=array[0];
		 
		 for(int i= 0;i<array.length;i++) {
			 if(array[i]>max) {
				 max=array[i];
				 
				 
			 }
		 }
		 
		 System.out.println("Largest element is "+max);
		
	}
	
	
}


