package introclassJava;


public class checksum_2c155667_003BlackboxTest {
    @org.junit.Test(timeout = 1000)
    public void test1() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is -";
        mainClass.scanner = new java.util.Scanner("1234567890");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test2() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is I";
        mainClass.scanner = new java.util.Scanner("abcefghi");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test3() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is K";
        mainClass.scanner = new java.util.Scanner(")(*&^%$#");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test4() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is E";
        mainClass.scanner = new java.util.Scanner("abc 123 %^&");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test5() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is 2";
        mainClass.scanner = new java.util.Scanner("~+{\"s1213skane");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test6() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is '";
        mainClass.scanner = new java.util.Scanner("ASDF_1234");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }
}

