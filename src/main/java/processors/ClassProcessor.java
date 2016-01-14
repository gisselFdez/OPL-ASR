package processors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import engine.TestAnalyser;
import reparator.App;
import spoon.Launcher;
import spoon.processing.AbstractManualProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtCodeElement;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtBinaryOperatorImpl;
import spoon.support.reflect.code.CtIfImpl;
import util.Operators;

public class ClassProcessor extends AbstractManualProcessor {

  private List<String> classesToModify; // list containing classes where we have to change conditional statements
  private File sourcesPath;
  private List<CtClass<?>> allClasses;

  private static final Integer TENTATIVES = 5; // There are 6 comparison operators (0 to 5)

  /**
   * Constructor of the ClassProcessor
   * @param classesToModify the list of classes where conditional statements have to be changed
   * @param sourcesPath the sources path
   */
  public ClassProcessor(File sourcesPath, List<String> classesToModify) {
    super();
    this.classesToModify = classesToModify;
    this.sourcesPath = sourcesPath;
    this.allClasses = new ArrayList<CtClass<?>>();
  }

  public void setAllClasses(List<CtClass<?>> allClasses) {
    this.allClasses = allClasses;
  }

  @Override
  public void process() {
    // Get all classes
    this.setAllClasses(getFactory().Package().getRootPackage().getElements(new TypeFilter(CtClass.class)));

    if (this.allClasses.isEmpty()) {
      System.out.println(">> Any classes found.");
    }
        
    // Run test for each class
    // this.allClasses.stream().forEach(c -> runTest(c));
    // Run analyze on the sources
    runAnalyze(this.sourcesPath, this.classesToModify);
  }

  /**
   * Run the test class
   * @param className the test classname
   */
  private void runTest(CtClass<?> className) {
    /*
     * TestAnalyser test = new TestAnalyser();
     * test.runTest(AppTest.class);
     * 
     * System.out.println(className.getSimpleName().toString());
     */
  }

  /*
   * **********************************************************************************************
   * ************************************** ANALYZE METHODS ***************************************
   **********************************************************************************************/

  /**
   * Starts the analyze of code lines to find method to repair
   * @param sourcePath the source code path
   * @param classes the list of classes to modify
   */
  public void runAnalyze(File sourcePath, List<String> classes) {
    // For each class, we fetch the methods to repair
    for (String classname : classes) {

      // Get the good CtClass
      for (CtClass<?> cls : this.allClasses) {
        if (cls.getQualifiedName().equals(classname)) {
          System.out.println(">> runAnalyze() found the class " + classname);
          analyzeClass(cls);
        }
      }
    }
  }

  /**
   * Analyze the class to identify which methods have to be changed
   * @param classToRepair the class to be analyzed
   */
  public void analyzeClass(CtClass<?> classToRepair) {
    for (CtMethod<?> method : classToRepair.getMethods()) {
      System.out.println(">> analyzeClass() found the method " + method.getSignature());
      analyzeMethod(method);
    }
  }

  /**
   * Analyze the method to identify conditional statements
   * @param methodToRepair the method to be analyzed
   */
  public void analyzeMethod(CtMethod<?> methodToRepair) {
    System.out.println(">> Method to repair : " + methodToRepair.getSimpleName());

    // Identfy statements which contains at least an operator and contains a if condition
    for (CtStatement statement : methodToRepair.getBody().getStatements()) {
      if (containsOperator(statement) && isACondition(statement)) {
        System.out.println(">> Save the current model.");
        saveModel();
        System.out.println(">> analyzeMethod() found the statement with conditional structure.");
        replaceIfStatement(statement);
      }
    }
  }

  /*
   * **********************************************************************************************
   * ************************************** REPLACE METHODS ***************************************
   **********************************************************************************************/

  /**
   * Replace a if statement modifying the comparison operator
   * @param ifStatement the statement to change
   */
  public void replaceIfStatement(CtCodeElement statement) {
    // Get the condition
    CtIfImpl ifStatement = (CtIfImpl) statement;

    // The operator index (to be reinitialized for each new condition)
    for (int opIndex = 0; opIndex <= TENTATIVES; opIndex++) {
      // Get the new operator
      BinaryOperatorKind newOp = Operators.getBinaryOp(Operators.getByIndex(opIndex));

      // Replace the operation of the condition
      ((CtBinaryOperatorImpl<?>) ifStatement.getCondition()).setKind(newOp);

      // Rerun the tests
      TestAnalyser analyser = new TestAnalyser(App.clsLoader);
      analyser.analyseWhiteBoxTests();

      // Compare the results and get the code
      // TODO : call the Comparator
      Integer codeResult = 1;

      switch (codeResult) {
        case -1: // regression
        case 0: // no changes
          restoreModel();
          // Is there any other operator ? No : go to the next condition if there is
          if (opIndex == TENTATIVES) {
            if (ifStatement.getElseStatement() instanceof CtIfImpl) { // else if (condition)
              replaceIfStatement(ifStatement.getElseStatement());
            }
          } // Yes : go to the next operator
          break;
        case 1: // repaired tests
          saveModel();
          // Go to the next condition
          if (ifStatement.getElseStatement() instanceof CtIfImpl) { // else if (condition)
            replaceIfStatement(ifStatement.getElseStatement());
          }
          break;
        default:
          restoreModel(); // In case of emergency
          break;
      }
    }
  }

  /*
   * **********************************************************************************************
   ****************************************** SAVE METHODS ****************************************
   **********************************************************************************************/

  public void saveModel() {
    String savePath = this.sourcesPath.getPath() + "\\prettyPrint";
    File savePathFile = new File(savePath);
    if (!(savePathFile.exists())) {
      if (!savePathFile.mkdir()) {
        System.out.println(">> !!! Error while creating the save directory. ");
        return;
      }
    }
    // prettyPrint directory created
    App.launcher.setSourceOutputDirectory(savePath);
    App.launcher.prettyprint();
  }

  public void restoreModel() {
    // TODO : How to restore the old model ?
  }

  /*
   * **********************************************************************************************
   * *************************************** CHECK METHODS ****************************************
   **********************************************************************************************/

  /**
   * Check if the statement contains a comparison or logical operator
   * @param statement the statement to check
   * @return <code>true</code> if contains at least an operator, <code>false</code> else
   */
  private Boolean containsOperator(CtCodeElement codeElement) {
    if (codeElement instanceof CtIfImpl) {
      CtIfImpl ifStatement = (CtIfImpl) codeElement;
      if (ifStatement.getCondition() != null) {
        // Pattern construction
        String p = Operators.EQUAL.get() + "|" + Operators.NOT_EQUAL.get()
          + "|" + Operators.GREATER.get() + "|" + Operators.GREATER_EQUAL.get()
          + "|" + Operators.LESS.get() + "|" + Operators.LESS_EQUAL.get();

        Pattern pattern = Pattern.compile(p);
        return pattern.matcher(ifStatement.getCondition().toString()).find();
      }
    }
    return false;
  }

  /**
   * Check if the statement is a <code>if</code> conditional structure
   * @param statement the statement to check
   * @return <code>true</code> if the statement is a conditional structure, <code>false</code> else
   */
  private Boolean isACondition(CtCodeElement codeElement) {
    return (codeElement instanceof CtIfImpl);
  }
}
