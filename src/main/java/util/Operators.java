package util;

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
}
