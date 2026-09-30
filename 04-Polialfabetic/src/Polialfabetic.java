import java.util.ArrayList;
import java.util.Collections;

public class Polialfabetic {

    public static void main(String[] args) {
        String msgs[] = {"Test 01 àrbritre, coixí, Perímetre",
                            "Test 02 Taüll, DÍA, año",
                            "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifrage:\n--------");
        for (int i = 0; msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifrage:\n--------");
        for (int i = 0; msgs.length; i++) {
            initRandom(clauSecreta);
           String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }
    }

    public static String xifraPoliAlfa(String msg) {
        return "";
    }

    public static String desxifraPoliAlfa(String msgXifrat) {
        return "";
    }
    
}
