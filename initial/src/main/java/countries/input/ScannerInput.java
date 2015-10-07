package countries.input;

import java.util.Scanner;

public class ScannerInput implements IInput{

	@Override
	public String getCountryName() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o nome do país");
		String countryName = scanner.nextLine();
		
		scanner.close();
		return countryName;
	}

}
