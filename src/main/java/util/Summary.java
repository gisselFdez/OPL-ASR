package util;

public class Summary {
	private static int buggedTests=0;
	private static int repairedTests=0;
	
	public static int getBuggedTests() {
		return buggedTests;
	}
	public static void addBuggedTest() {
		Summary.buggedTests ++;
	}
	public static int getRepairedTests() {
		return repairedTests;
	}
	public static void addRepairedTest() {
		Summary.repairedTests++;
	}
	
}
