package countries.scanner;

import java.util.Scanner;

public class DefaultScanner {
	
	private static Scanner scanner;
	
	public static Scanner getInstance(){
		if(DefaultScanner.scanner == null){
			DefaultScanner.scanner = new Scanner(System.in);
		}
		
		return DefaultScanner.scanner;
	}
	
	public static void closeScanner(){
		if(DefaultScanner.scanner != null){
			DefaultScanner.scanner.close();
		}
	}
	
}
