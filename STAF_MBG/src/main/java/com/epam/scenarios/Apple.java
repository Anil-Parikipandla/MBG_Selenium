package com.epam.scenarios;


public class Apple {
	static int iCounter;
	
	Apple()
	{
		
		System.out.println("Apple");
		iCounter = iCounter + 1;
		if(iCounter< 5)
		{
			 System.out.println("Counter :" +iCounter );
			
		}
		else 
			return ;
		//System.gc();
	}
	
	Apple a =new Apple();
	public static void main(String args[])
	{
		 Apple newA =new Apple();
		 newA.isplay();
	}
	
	public void isplay()
	{
		System.out.println("Display");
	}

}
