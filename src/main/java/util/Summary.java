package util;

public class Summary {
	private static int repairedTests=0;
	
		
	public static int getRepairedTests() {
		return repairedTests;
	}
	
	public static void addRepairedTest() {
		Summary.repairedTests++;
	}
	
	public static void addRepairedTest(int count) {
		Summary.repairedTests = Summary.repairedTests+count;
	}
	
	public static void resetRepairedTests(){
		Summary.repairedTests =0;
	}
}
