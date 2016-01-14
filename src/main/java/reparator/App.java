package reparator;

import java.io.File;
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
  //public static final String SOURCES_PATH = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\OPL-ASR";
  // public static final String SOURCES_PATH = "C:\\Users\\Pauline\\Documents\\M2IAGL\\OPL\\IntroClassJava\\dataset\\checksum\\6\\003";
  //public static final String SOURCES_PATH = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset";
  public static final String CHECKSUM_06_PATH = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\checksum\\1\\003";
  public static final String SOURCES_PRINT = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\prettyPrint";
  public static final List<String> STATIC_CLASSES_LIST = initStaticList();
  public static Launcher launcher = new Launcher();
  public static ClasspathClassLoader clsLoader;


	public static void main(String[] args) {
		//To call only ONE project		
		/*String sourcesPath = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\checksum\\1\\003\\src";		
		//calling spoon processor
		final Launcher spoon = new Launcher();
		spoon.addProcessor(new ClassProcessor());
		spoon.run(new String[]{"-i",sourcesPath,"-x"});*/
		
		//To call EVERY project on path		
		App reparator = new App();
		int failures=reparator.getAllProjects(new File(CHECKSUM_06_PATH));
		//Summary
		printSummary(failures);
	}

	
	/**
	 * Launch the spoon processor for every project found in the sourcesPath
	 * @param sourcesPath
	 */
	private int getAllProjects(File sourcesPath){	
		int testFailures = 0;
		File listFile[] = sourcesPath.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                	if(!listFile[i].toString().contains("src"))
                		getAllProjects(listFile[i]);
                	else {
                		if(listFile[i].toString().contains("spooned"))
                			break;
                		else{
                			//FOR EACH PROJECT
                            System.out.println("File:"+listFile[i].getPath());
                            //build project
                            Compiler compiler = new Compiler();                        
                            HashSet<URL> classLoaderUrls = compiler.compileProject(listFile[i].getPath());//listFile[i].getPath()
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
                            
                            // Call the spoon processor
                            System.out.println(">> Launch the ClassProcessor.");
                            //String spoonPath= listFile[i].getPath().replace("\\src", "");
                            launcher.addProcessor(new ClassProcessor(new File(listFile[i].getPath()), classes));
                            launcher.run(new String[] {"-i", listFile[i].getPath(), "-x"});
                		}                		
                        break;
                    }                	
                } 
            }             
         }   
        return testFailures;        
	}
  
	private static void printSummary(int failures){		
        System.out.println("\n-----------SUMMARY-----------");
        System.out.println("Initial failed tests: "+failures);
        System.out.println("Repaired tests: "+Summary.getRepairedTests());
	}
	
  /* ******************************************* BUILDS ******************************************* */

  private static List<String> initStaticList() {
    List<String> classes = new ArrayList<String>();
    classes.add("Example");
    return classes;
  }

}
