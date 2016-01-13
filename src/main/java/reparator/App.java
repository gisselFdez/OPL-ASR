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
import engine.Comparator;
import engine.Compiler;
import engine.TestAnalyser;

public class App {


	public static void main(String[] args) {
		//To call only ONE project		
		/*String sourcesPath = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\checksum\\1\\003\\src";		
		//calling spoon processor
		final Launcher spoon = new Launcher();
		spoon.addProcessor(new ClassProcessor());
		spoon.run(new String[]{"-i",sourcesPath,"-x"});*/
		
		//To call EVERY project on path
		String sourcesPath = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset";
		sourcesPath = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\checksum\\1\\003";
		App reparator = new App();
		reparator.getAllProjects(new File(sourcesPath));
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
                		//FOR EACH PROJECT
                        System.out.println("File:"+listFile[i].getPath());
                        //build project
                        Compiler compiler = new Compiler();                        
                        HashSet<URL> classLoaderUrls = compiler.compileProject(listFile[i].getPath());
                        //Create classpath
                        ClasspathClassLoader clsLoader = new ClasspathClassLoader(classLoaderUrls.toArray(new URL[0]));
                        
                        //Analyse tests                       
                        TestAnalyser analyser = new TestAnalyser(clsLoader);
                        analyser.analyseWhiteBoxTests();
                        
                        System.out.println("---------ANALYSIS RESULTS------");
                        List<String> classes = analyser.getNoTestClasses();
                        /*for(String cls:classes){
                        	System.out.println("cls: "+cls);
                        }*/
                        HashMap<String,List<String>> failures = analyser.getTestClassFailed();
                        /*Iterator it = failures.entrySet().iterator();
                        while (it.hasNext()) {
                        	Map.Entry e = (Map.Entry)it.next();
                        	System.out.println(e.getKey() + " " + e.getValue());
                        }*/
                        break;
                    }                	
                } 
            }
            //return HashMap<ClassName,List<methodsName>>
            
            //calling spoon processor
            /*final Launcher spoon = new Launcher();
			spoon.addProcessor(new ClassProcessor());
			spoon.run(new String[]{"-i",listFile[i].getPath(),"-x"});*/
        }
	}
	
	
}
