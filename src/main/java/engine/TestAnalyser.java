package engine;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

import util.ClasspathClassLoader;

public class TestAnalyser {
	
	ClasspathClassLoader clsLoader;
	String actualPath;
	
	public TestAnalyser(){
		
	}
	
	public TestAnalyser(ClasspathClassLoader clsLoader){
		this.clsLoader = clsLoader;
	}

	/**
	 * Look for every test class in classpath and return every bugged method from every class
	 */
	public void analyseTests(){
		//Get all the folders/jars added to classpath
		URL[] urls = this.clsLoader.getURLs();
		for(URL url: urls){
			//for every folder run testClasses
			actualPath = url.getFile();
			runAllTestClasses(new File (actualPath));
		}
	}
	
	/**
	 * 
	 * @param className
	 */
	public void runTest(Class<?> className){		
		for (Method m : className.getDeclaredMethods()) {
			if (m.getAnnotations()[0].annotationType().getName().equals("org.junit.Test")) {
				
				Request request = Request.method(className,m.getName());
				Result result = new JUnitCore().run(request);
				result.getFailures();
				System.out.println("Test runned");
			}
		}
	}
	
	public void runAllTestClasses(File path){
		File listFile[] = path.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory())                 	
                	runAllTestClasses(listFile[i]);                
	        	else 
	        		if(listFile[i].toString().contains(".class")){
	        			//System.out.println(listFile[i]);
						//runTest(this.clsLoader.loadClass(listFile[i].toString()));
        				this.clsLoader.findOrLoadClass(getNameClass(actualPath,listFile[i].toString()));						
	        		}    
            }
        }
	}
	
	private String getNameClass(String path, String file){
		String name=file.replace(path.substring(1).replace("/", "\\"), "").replace("\\", ".").replace(" ", "").replace(".class", "");
		System.out.println(name);
		return name;
	}	
	
}
