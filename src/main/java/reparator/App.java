package reparator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.Compiler;
import processors.ClassProcessor;
import spoon.Launcher;

public class App {

  public static final String SOURCES_PATH = "C:\\Users\\Pauline\\Documents\\M2IAGL\\OPL\\OPL-ASR";
  // public static final String SOURCES_PATH = "C:\\Users\\Pauline\\Documents\\M2IAGL\\OPL\\IntroClassJava\\dataset\\checksum\\6\\003";
  // public static final String SOURCES_PATH = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset";
  public static final String CHECKSUM_06_PATH = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\checksum\\6\\003";
  public static final Map<String, List<String>> MAP_STATIQUE = initMapStatique();

  
  public static void main(String[] args) {
    System.out.println(">> Start the App.");

    // To call only ONE project
    /*
     * String sourcesPath = "C:\\Users\\AnaGissel\\Documents\\MASTER\\OPL\\Project3\\IntroClassJava\\dataset\\checksum\\1\\003\\src";
     * //calling spoon processor
     * final Launcher spoon = new Launcher();
     * spoon.addProcessor(new ClassProcessor());
     * spoon.run(new String[]{"-i",sourcesPath,"-x"});
     */

    // To call EVERY project on path
    App reparator = new App();
    reparator.getAllProjects(new File(SOURCES_PATH));
  }

  /**
   * Launch the spoon processor for every project found in the sourcesPath
   * @param sourcesPath
   */
  private void getAllProjects(File sourcesPath) {
    File listFile[] = sourcesPath.listFiles();
    if (listFile != null) {
      for (int i = 0; i < listFile.length; i++) {
        if (listFile[i].isDirectory()) {
          if (!listFile[i].toString().contains("src")) {
            getAllProjects(listFile[i]);
          } else {
            System.out.println("File: " + listFile[i].getPath());
            // Launch the compilation step of the project
            Compiler compiler = new Compiler();
            compiler.compileProject(listFile[i].getPath());

            // Call the spoon processor
            System.out.println(">> Launch the ClassProcessor.");
            final Launcher spoon = new Launcher();
            spoon.addProcessor(new ClassProcessor(new File(SOURCES_PATH), MAP_STATIQUE));
            spoon.run(new String[] {"-i", listFile[i].getPath(), "-x"});

            break;
          }
        }
      }
    }
  }

  /* ******************************************* BUILDS ******************************************* */

  private static Map<String, List<String>> initMapStatique() {
    Map<String, List<String>> map = new HashMap<>();

    List<String> methods = new ArrayList<String>();
    methods.add("getMessage");

    map.put("example.Example", methods);
    return map;
  }

}
