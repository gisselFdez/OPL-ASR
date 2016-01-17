package reparator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import processors.ClassProcessor;
import spoon.Launcher;
import util.ClasspathClassLoader;
import util.Summary;
import engine.Comparator;
import engine.Compiler;
import engine.TestAnalyser;
import java.util.ArrayList;
import java.util.List;
import processors.ClassProcessor;
import spoon.Launcher;

public class App {

  //public static final String SOURCES_PATH = "C:\\Users\\Pauline\\Documents\\M2IAGL\\OPL\\OPL-ASR";
  public static String SOURCES_PATH = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\buggedProject";
  // public static final String SOURCES_PATH = "C:\\Users\\Pauline\\Documents\\M2IAGL\\OPL\\IntroClassJava\\dataset\\checksum\\6\\003";
  //public static String SOURCES_PATH = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\digits";
  //public static final String CHECKSUM_06_PATH = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\checksum\\1\\003";
  //public static final String SOURCES_PRINT = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\prettyPrint";
  public static final List<String> STATIC_CLASSES_LIST = initStaticList();
  public static Launcher launcher = new Launcher();
  public static ClasspathClassLoader clsLoader;
  private static int testFailures=0;
  private static String output=System.getProperty("user.dir");

	public static void main(String[] args) {
		if(args.length ==2){
			SOURCES_PATH = args[0];
			createOutputFile(args[1]);
		}
		else
			createOutputFile(output);
		
		//To call EVERY project on path		
		App reparator = new App();
		reparator.getAllProjects(new File(SOURCES_PATH));
		//Summary
		printSummary();
	}

	
	/**
	 * Launch the spoon processor for every project found in the sourcesPath
	 * @param sourcesPath
	 */
	private void getAllProjects(File sourcesPath){	
		File listFile[] = sourcesPath.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                	if(!listFile[i].toString().contains("src"))
                		getAllProjects(listFile[i]);
                	else {
                		if(listFile[i].toString().contains("spooned") || listFile[i].toString().contains("prettyPrint")
                				|| listFile[i].toString().contains("repairedCode"))
                			break;
                		else{
                			//FOR EACH PROJECT
                            System.out.println("File:"+listFile[i].getPath());
                            //build project
                            Compiler compiler = new Compiler();                        
                            HashSet<URL> classLoaderUrls = compiler.compileProject(listFile[i].getPath());//listFile[i].getPath()
                            if(classLoaderUrls!= null){
                            	//Create classpath
                                clsLoader = new ClasspathClassLoader(classLoaderUrls.toArray(new URL[0]));
                                
                                //Analyse tests  
                                System.out.println(">> Launch the Test Analysis.");
                                TestAnalyser analyser = new TestAnalyser(clsLoader);
                                analyser.analyseWhiteBoxTests();                        
                                //System.out.println("	Results:");
                                List<String> classes = analyser.getNoTestClasses();
                                HashMap<String,List<String>> failures = analyser.getTestClassFailed();
                                testFailures = testFailures+analyser.getTestFailures();
                                
                                if(analyser.getTestFailures()>0 && !classes.isEmpty()){
                                	// Call the spoon processor
                                    System.out.println(">> Launch the ClassProcessor.");
                                    //String spoonPath= listFile[i].getPath().replace("\\src", "");
                                    launcher.addProcessor(new ClassProcessor(new File(listFile[i].getPath()), classes, failures));
                                    launcher.run(new String[] {"-i", listFile[i].getPath(), "-x"});
                                }                                
                            }                            
                		}                		
                        break;
                    }                	
                } 
            }             
         }          
	}
  
	private static void printSummary(){		
        System.out.println("\n-----------SUMMARY-----------");
        System.out.println("Initial failed tests: "+testFailures);
        System.out.println("Repaired tests: "+Summary.getRepairedTests());
        Summary.writeToOutputFile("-----------SUMMARY-----------");
		Summary.writeToOutputFile("Initial failed tests: "+testFailures);
		Summary.writeToOutputFile("Repaired tests: "+Summary.getRepairedTests());
	}
	
	private static void createOutputFile(String path){
		Summary.setOutputFile(path+"\\output.txt");
		try (
				Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(Summary.getOutputFile()), "utf-8"))) {
	   writer.write("");
	   writer.close();
	   Summary.writeToOutputFile("-----------Results-----------");
	} catch ( IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
  /* ******************************************* BUILDS ******************************************* */

  private static List<String> initStaticList() {
    List<String> classes = new ArrayList<String>();
    classes.add("Example");
    return classes;
  }

}
