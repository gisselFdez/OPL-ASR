package example;

import static example.Example.getMessage;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class ExampleTest {

  public static final String REP_NULL = "Value NULL";
  public static final String REP_POS = "Value POSITIVE";
  public static final String REP_NEG = "Value NEGATIVE";

  @Test
  public void test1() throws Exception {
    Assertions.assertThat(getMessage(null)).isEqualTo(REP_NULL);
  }

  @Test
  public void test2() throws Exception {
    Assertions.assertThat(getMessage(1)).isEqualTo(REP_POS);
  }

  @Test
  public void test3() throws Exception {
    Assertions.assertThat(getMessage(0)).isEqualTo(REP_NEG);
  }

  @Test
  public void test4() throws Exception {
    Assertions.assertThat(getMessage(-10)).isEqualTo(REP_NEG);
  }
}
