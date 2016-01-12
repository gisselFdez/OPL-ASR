package processors;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import engine.TestAnalyser;
import reparator.AppTest;
import spoon.processing.AbstractManualProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;
import util.Operators;

public class ClassProcessor extends AbstractManualProcessor {

  private ConditionProcessor conditionProcessor; // Processor which make the statement changes
  private Map<String, List<String>> map; // map containing bugged methods with their classname
  private File sourcesPath;
  private List<CtClass<?>> allClasses;

  public static final String IF_CONDITION = "if";

  /**
   * Constructor of the ClassProcessor
   * @param map the map containing bugged methods with their classname
   * @param sourcesPath the sources path
   */
  public ClassProcessor(File sourcesPath, Map<String, List<String>> map) {
    super();
    this.map = map;
    this.sourcesPath = sourcesPath;
    this.conditionProcessor = new ConditionProcessor();
    this.allClasses = getFactory().Package().getRootPackage().getElements(new TypeFilter(CtClass.class));
  }

  @Override
  public void process() {
    if (this.allClasses.isEmpty()) {
      System.out.println(">> Any classes found.");
    }

    // Run test for each class
    this.allClasses.stream().forEach(c -> runTest(c));
    // Run analyze on the sources
    runAnalyze(this.sourcesPath, this.map);
  }

  /**
   * Run the test class
   * @param className the test classname
   */
  private void runTest(CtClass<?> className) {
    TestAnalyser test = new TestAnalyser();
    test.runTest(AppTest.class);

    System.out.println(className.getSimpleName().toString());
  }

  /**
   * Starts the analyze of code lines to find method to repair
   * @param sourcePath the source code path
   * @param map the map containing methods to repair with their classname
   */
  public void runAnalyze(File sourcePath, Map<String, List<String>> map) {
    // For each class, we fetch the methods to repair
    for (String classname : map.keySet()) {

      // Get the good CtClass
      for (CtClass<?> cls : this.allClasses) {
        if (cls.getSimpleName().equals(classname)) {
          System.out.println(">> runAnalyze() found the class " + classname);
          analyzeMethods(cls);
        }
      }
    }
  }

  /**
   * Analyze the methods to change into the class
   * @param classToRepair the class to be analyzed
   */
  public void analyzeMethods(CtClass<?> classToRepair) {
    // For each method to repair into the class
    for (String methodName : map.get(classToRepair.getSimpleName())) {
      for (CtMethod<?> method : classToRepair.getMethods()) {
        if (method.getSimpleName().equals(methodName)) { // we found a CtMethod to repair
          System.out.println(">> analyzeMethod() found the method " + methodName);
          analyzeStatements(method);
        }
      }
    }
  }

  /**
   * Analyze the statements to change in the method
   * @param methodToRepair the method to be analyzed
   */
  public void analyzeStatements(CtMethod<?> methodToRepair) {
    System.out.println(">> Method to repair : " + methodToRepair.getSimpleName());

    // Identfy statements which contains at least an operator and contains a if condition
    for (CtStatement statement : methodToRepair.getBody().getStatements()) {
      if (containsOperator(statement) && isACondition(statement)) {
        System.out.println(">> analyzeStatements() found the statement " + statement.getSignature());
        // Call to ConditionProcessor to replace the conditional statement
        conditionProcessor.process();
      }
    }
  }

  /**
   * Check if the statement contains a comparison or logical operator
   * @param statement the statement to check
   * @return <code>true</code> if contains at least an operator, <code>false</code> else
   */
  private Boolean containsOperator(CtStatement statement) {
    String st = statement.getLabel().toLowerCase();

    // Pattern construction
    String p = Operators.EQUAL.name() + "|" + Operators.NOT_EQUAL.name()
      + "|" + Operators.GREATER.name() + "|" + Operators.GREATER_EQUAL.name()
      + "|" + Operators.LESS.name() + "|" + Operators.LESS_EQUAL.name();

    Pattern pattern = Pattern.compile(p);
    return pattern.matcher(st).find();
  }

  /**
   * Check if the statement is a <code>if</code> conditional structure
   * @param statement the statement to check
   * @return <code>true</code> if the statement is a conditional structure, <code>false</code> else
   */
  private Boolean isACondition(CtStatement statement) {
    String st = statement.getLabel().toLowerCase();
    return st.contains(IF_CONDITION);
  }
}
