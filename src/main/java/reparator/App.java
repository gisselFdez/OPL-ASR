package reparator;

import java.io.File;
import processors.ClassProcessor;
import spoon.Launcher;
import engine.Compiler;

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
		sourcesPath = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\checksum\\6\\003";
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
                        System.out.println("File:"+listFile[i].getPath());
                        //build
                        Compiler compiler = new Compiler();                        
                        compiler.compileProject(listFile[i].getPath());
                        //calling spoon processor
                        /*final Launcher spoon = new Launcher();
            			spoon.addProcessor(new ClassProcessor());
            			spoon.run(new String[]{"-i",listFile[i].getPath(),"-x"});*/
                        break;
                    }                	
                } 
            }
        }
	}
	
	
}
