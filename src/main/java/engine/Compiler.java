package engine;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class Compiler {

	HashSet<File> filesList;
	List<String> compiledClasses = new ArrayList<String>();
	
	public Compiler(){
		filesList = new HashSet<File>();
	}
	
	public HashSet<URL> compileProject(String path){
		try {
			List<String> args = new ArrayList<String>();
			String tmpFolder = createTmpFolder(path);
	        args.add("javac");
	        
	        StringWriter compileCommand = new StringWriter();
	        compileCommand.append("-g -nowarn -d ");
	        args.add("-g");
	        args.add("-nowarn");
	        args.add("-d");
	        args.add(tmpFolder);
	        args.add("-cp"); //-cp
	        args.add(getClasspath());        
	        
	        getAllClasses(new File(path));
	        for (File file : filesList) {
	            args.add(file.getAbsolutePath());
	        }        
		
			ProcessBuilder p = new ProcessBuilder(args);
			Process process = p.start();
			process.waitFor();
			
			if(process.exitValue() != 0){
				System.out.println("Error while compiling project ");
				return null;
	        }
			else{			
				HashSet<URL> cpurls = new HashSet<URL>();
				cpurls.add(new File(tmpFolder).toURI().toURL());
				return cpurls;		
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}             
	}
	
	private void getAllClasses(File path){
		File listFile[] = path.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory())                 	
            		getAllClasses(listFile[i]);                
	        	else 
	        		if(listFile[i].toString().contains(".java"))
	        			filesList.add(listFile[i]);               
            }
        }
	}
	
	private String createTmpFolder(String path){
		File file = new File(path+"\\tmpClasses");
		if (!file.exists()) {
			if (file.mkdir()) 
				return file.toString();
			else
				return null;			
		}
		else
			return file.toString();
	}
	
	private String getClasspath(){
		String path = new File("junit/junit-4.8.jar").getAbsolutePath();
		return path;
	}
	
	private void buildProjectMaven(String path){
		path = path.replace("src", "pom.xml");
		
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile( new File( path) );
		request.setGoals( Arrays.asList( "test-compile","clean", "install" ) );

		Invoker invoker = new DefaultInvoker();
		try {
			InvocationResult inv = invoker.execute( request );
			inv.getExecutionException();
		} catch (MavenInvocationException e) {
			e.printStackTrace();
		}		
	}
}
