package reparator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import processors.ClassProcessor;
import spoon.Launcher;

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
		sourcesPath = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\checksum";
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
                        //calling spoon processor
                        final Launcher spoon = new Launcher();
            			spoon.addProcessor(new ClassProcessor());
            			spoon.run(new String[]{"-i",listFile[i].getPath(),"-x"});
                        break;
                    }                	
                } 
            }
        }
	}
}
