package util;

import spoon.reflect.code.BinaryOperatorKind;

public enum Operators {
  EQUAL("=="), // Comparison operators
  NOT_EQUAL("!="),
  GREATER(">"),
  GREATER_EQUAL(">="),
  LESS("<"),
  LESS_EQUAL("<="),
  AND("&&"), // Logical operators
  OR("||");

  private String code;
  
  Operators(String op) {
    this.code = op;
  }
  
  public String get() {
    return this.code;
  }
  
  public static Operators getByIndex(Integer index) {
    return Operators.values()[index];
  }
  
  public static BinaryOperatorKind getBinaryOp(Operators op) {
    switch(op) {
      case EQUAL         : return BinaryOperatorKind.EQ;
      case NOT_EQUAL     : return BinaryOperatorKind.NE;
      case GREATER       : return BinaryOperatorKind.GT;
      case GREATER_EQUAL : return BinaryOperatorKind.GE;
      case LESS          : return BinaryOperatorKind.LT;
      case LESS_EQUAL    : return BinaryOperatorKind.LE;
      default            : return null;
    }
  }
}
