package util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class Summary {
	private static int repairedTests=0;
	private static String outputFile="";
	
		
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
	
	public static String getOutputFile(){
		return Summary.outputFile;
	}
	public static void setOutputFile(String file){
		Summary.outputFile = file;
	}
	
	public static void writeToOutputFile(String text){
		try(PrintWriter output = new PrintWriter(new FileWriter(Summary.getOutputFile(),true))) 
		{
		    output.printf("%s\r\n", text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
