package introclassJava;


public class checksum_2c155667_003 {
    public java.util.Scanner scanner;

    public java.lang.String output = "";

    public static void main(java.lang.String[] args) throws java.lang.Exception {
        introclassJava.checksum_2c155667_003 mainClass = new introclassJava.checksum_2c155667_003();
        java.lang.String output;
        if ((args.length) > 0) {
            mainClass.scanner = new java.util.Scanner(args[0]);
        } else {
            mainClass.scanner = new java.util.Scanner(java.lang.System.in);
        }
        mainClass.exec();
        java.lang.System.out.println(mainClass.output);
    }

    public void exec() throws java.lang.Exception {
        introclassJava.CharObj character = new introclassJava.CharObj();
        introclassJava.CharObj remainder = new introclassJava.CharObj();
        introclassJava.IntObj sum = new introclassJava.IntObj();
        output += java.lang.String.format("Enter an abitrarily long string, ending with carriage return > ");
        sum.value = 0;
        while ((character.value) != '\n') {
            try {
                character.value = scanner.findInLine(".").charAt(0);
            } catch (java.lang.NullPointerException e) {
                character.value = '\n';
            }
            sum.value = (sum.value) + ((int)(character.value));
        }
        remainder.value = ((char)(((sum.value) % 64) + 22));
        output += java.lang.String.format("Check sum is %c\n", remainder.value);
        if (true)
            return ;
        
    }
}

