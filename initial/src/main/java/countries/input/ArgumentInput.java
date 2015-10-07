package countries.input;

public class ArgumentInput implements IInput{

	private String[] args;
	
	public ArgumentInput(String[] args){
		this.args = args;
	}
	
	@Override
	public String getCountryName() {
		if(args.length <= 0){
    		System.out.println("Você deve informar um país");
    		System.exit(1);
    	}
    	
    	
    	return args[0];
	}

}
