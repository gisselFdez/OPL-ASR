package processors;

import spoon.processing.AbstractManualProcessor;

/**
 * This processor is in charge to replace conditions (in source code) which make fail the tests.
 * @author Pauline
 *
 */
public class ConditionProcessor extends AbstractManualProcessor {

  @Override
  public void process() {
    System.out.println(">> ConditionProcessor start.");
  }

  public void replaceCondition() {

  }

}
