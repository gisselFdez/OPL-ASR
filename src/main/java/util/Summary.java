package util;

import java.util.HashMap;
import java.util.List;

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
	
	public static void addRepairedTest(int count) {
		Summary.repairedTests = Summary.repairedTests+count;
	}
	
	public static void resetRepairedTests(){
		Summary.repairedTests =0;
	}
}
