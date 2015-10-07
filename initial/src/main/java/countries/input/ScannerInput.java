package countries.input;

import java.util.Scanner;

import countries.scanner.DefaultScanner;

public class ScannerInput implements IInput{

	@Override
	public String getCountryName() {
		Scanner scanner = DefaultScanner.getInstance();
		System.out.println("Digite o nome ou parte do nome de um país");
		System.out.print("> ");
		String countryName = scanner.nextLine();
		
		return countryName;
	}

}
