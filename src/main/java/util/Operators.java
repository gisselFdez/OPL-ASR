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

  Operators(String op) {
  }
}
