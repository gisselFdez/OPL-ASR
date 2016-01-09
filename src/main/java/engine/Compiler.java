package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class Compiler {

	HashSet<File> filesList;
	
	public Compiler(){
		filesList = new HashSet<File>();
	}
	
	public void compileProject(String path){
		List<String> args = new ArrayList<String>();
        args.add("javac");
        
        StringWriter compileCommand = new StringWriter();
        compileCommand.append("-g -nowarn -d ");
        args.add("-g");
        args.add("-nowarn");
        args.add("-d");
        args.add(createTmpFolder(path));
        args.add("-cp"); //-cp
        args.add(getClasspath());        
        
        getAllClasses(new File(path));
        for (File file : filesList) {
        	//System.out.println("test cls: "+ file.toString());
            args.add(file.getAbsolutePath());
        }
        
		try {
			ProcessBuilder p = new ProcessBuilder(args);
			Process process = p.start();
			process.waitFor();
			
			if(process.exitValue() != 0){
				System.out.println("Error while compiling project ");	        	
	        }
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
