package introclassJava;


public class checksum_2c155667_003WhiteboxTest {
    @org.junit.Test(timeout = 1000)
    public void test1() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is ]";
        mainClass.scanner = new java.util.Scanner("hello world!");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test10() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is ,";
        mainClass.scanner = new java.util.Scanner("We the people...");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test2() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is 7";
        mainClass.scanner = new java.util.Scanner("qwertyuiopasdfghjkl");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test3() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is K";
        mainClass.scanner = new java.util.Scanner("A*");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test4() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is F";
        mainClass.scanner = new java.util.Scanner("O Brother Where Art Thou?");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test5() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is 4";
        mainClass.scanner = new java.util.Scanner("~!@#$%^&*()_+");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test6() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is @";
        mainClass.scanner = new java.util.Scanner("100 Degrees and sunny");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test7() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is V";
        mainClass.scanner = new java.util.Scanner("?? water the plants !!");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test8() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is X";
        mainClass.scanner = new java.util.Scanner("12894.389239");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }

    @org.junit.Test(timeout = 1000)
    public void test9() throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String expected = "Enter an abitrarily long string, ending with carriage return > Check sum is #";
        mainClass.scanner = new java.util.Scanner("! word 12 :)");
        mainClass.exec();
        java.lang.String out = mainClass.output.replace("\n", " ").trim();
        org.junit.Assert.assertEquals(expected.replace(" ", ""), out.replace(" ", ""));
    }
}

