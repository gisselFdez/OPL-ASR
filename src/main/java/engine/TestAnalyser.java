package engine;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import util.ClasspathClassLoader;

public class TestAnalyser {
<<<<<<< HEAD
	
	ClasspathClassLoader clsLoader;
	String actualPath;
	private HashMap<String, List<String>> testClassFailed = new HashMap<String, List<String>>();
	private List<String> noTestClasses = new ArrayList<String>();
	
	public List<String> getNoTestClasses(){
		return noTestClasses;
	}
	
	public HashMap<String, List<String>> getTestClassFailed(){
		return testClassFailed;
	}
	
	public TestAnalyser(ClasspathClassLoader clsLoader){
		this.clsLoader = clsLoader;
	}

	/**
	 * Look for every test class in classpath and return every bugged method from every class
	 */
	public void analyseWhiteBoxTests(){
		//Get all the folders/jars added to classpath
		URL[] urls = this.clsLoader.getURLs();
		for(URL url: urls){
			//for every folder run testClasses
			actualPath = url.getFile();
			runAllWhiteBoxTestClasses(new File (actualPath));
		}
	}
	
	/**
	 * 
	 * @param className
	 */
	public void runTest(Class<?> className){	
		List<String> testsFailed = new ArrayList<String>();		
		
		for (Method m : className.getDeclaredMethods()) {
			if(m.getAnnotations().length!=0)
				if (m.getAnnotations()[0].annotationType().getName().equals("org.junit.Test")) {					
					Request request = Request.method(className,m.getName());
					Result result = new JUnitCore().run(request);
					List<Failure> failures = result.getFailures();
					
					if(failures.size() != 0){
						System.out.println("Test failed: "+m.getName());						
						testsFailed.add(m.getName());	
					}					
				}
		}		
		
		//if any test of the class failed
		if(testsFailed.size()!=0)
			testClassFailed.put(className.getName(), testsFailed);		
	}
	
	public void runAllWhiteBoxTestClasses(File path){
		File listFile[] = path.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory())                 	 
                	runAllWhiteBoxTestClasses(listFile[i]);                
	        	else 
	        		if(listFile[i].toString().contains(".class") && listFile[i].toString().contains("Whitebox")){
						runTest(this.clsLoader.findOrLoadClass(getNameClass(actualPath,listFile[i].toString())));					
	        		}
	        		else
	        			isNotTestClass(this.clsLoader.findOrLoadClass(getNameClass(actualPath,listFile[i].toString())));
            }
        }
	}
	
	private void isNotTestClass(Class<?> className){
		Boolean hasTest = false;
		
		for (Method m : className.getDeclaredMethods()) {
			if(m.getAnnotations().length!=0)
				if (m.getAnnotations()[0].annotationType().getName().equals("org.junit.Test")) {
					hasTest =true;
				}
		}
		if(!hasTest)
			noTestClasses.add(className.getName());
	}
	
	private String getNameClass(String path, String file){
		//windows
		String name;
		if(file.contains("\\"))			
			name=file.replace(path.substring(1).replace("/", "\\"), "").replace("\\", ".").replace(" ", "").replace(".class", "");
		else
			//linux
			name=file.replace(path.substring(1), "").replace("/", ".").replace(" ", "").replace(".class", "");
		System.out.println(name);
		return name;
	}		
=======

  public static final String JUNIT_ANNOTATION = "org.junit.Test";

  ClasspathClassLoader clsLoader;
  String actualPath;

  public TestAnalyser() {
  }

  public TestAnalyser(ClasspathClassLoader clsLoader) {
    this.clsLoader = clsLoader;
  }

  /**
   * Look for every test class in classpath and return every bugged method from every class
   */
  public void analyseTests() {
    // Get all the folders/jars added to classpath
    URL[] urls = this.clsLoader.getURLs();
    for (URL url : urls) {
      // for every folder run testClasses
      actualPath = url.getFile();
      runAllTestClasses(new File(actualPath));
    }
  }

  /**
   * Runs all test classes found in the path
   * @param path the classes path
   */
  public void runAllTestClasses(File path) {
    File listFile[] = path.listFiles();
    if (listFile != null) {
      for (int i = 0; i < listFile.length; i++) {
        if (listFile[i].isDirectory())
          runAllTestClasses(listFile[i]);
        else if (listFile[i].toString().contains(".class")) { // For each test class, runTest() method is called
          // System.out.println(listFile[i]);
          // runTest(this.clsLoader.loadClass(listFile[i].toString()));
          this.clsLoader.findOrLoadClass(getNameClass(actualPath, listFile[i].toString()));
        }
      }
    }
  }

  /**
   * Runs a test 
   * @param className the test classname 
   */
  public void runTest(Class<?> className) {
    for (Method m : className.getDeclaredMethods()) {
      // For each test method
      if (m.getAnnotations()[0].annotationType().getName().equals(JUNIT_ANNOTATION)) {
        Request request = Request.method(className, m.getName());
        Result result = new JUnitCore().run(request); // Runs the test
        result.getFailures();
        System.out.println("Test runned");
      }
    }
  }

  /**
   * Get a clean path name
   * @param path
   * @param file
   * @return
   */
  private String getNameClass(String path, String file) {
    String name = file.replace(path.substring(1).replace("/", "\\"), "").replace("\\", ".").replace(" ", "").replace(".class", "");
    System.out.println(name);
    return name;
  }

>>>>>>> 53d819f034e536f618cc2a4d20050691fabc29fc
}
