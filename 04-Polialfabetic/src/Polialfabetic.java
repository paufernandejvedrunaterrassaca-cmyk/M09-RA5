import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Polialfabetic {

    public static final String lletres = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    public static String clauSecreta = "LaMevaClau";
    public static char[] alfabetPermutat = lletres.toCharArray();
    public static Random random = new Random();

    public static void main(String[] args) {

        String msgs[] = { "Test 01 àrbitre, coixí, Perimetre",
                "Test 02 Taüll, DÍA, año",
                "Test 03 Peça, Òrrius, Bòvila" };
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }

    public static void permutaAlfabet() {

        ArrayList<Character> llista = new ArrayList<>();

        for (char c : lletres.toCharArray()) {
            llista.add(c);
        }

        Collections.shuffle(llista, random);

        for (int i = 0; i < llista.size(); i++) {
            alfabetPermutat[i] = llista.get(i);
        }
    }

    public static void initRandom(String clau) {
        long seed = 0;

        for (int i = 0; i < clau.length(); i++) {
            seed = seed * 43 + clau.charAt(i);
        }
        random.setSeed(seed);
    }

    public static String xifraPoliAlfa(String msg) {
        StringBuilder resultat = new StringBuilder();

        for (char c : msg.toCharArray()) {

            char maj = Character.toUpperCase(c);
            int index = lletres.indexOf(maj);

            if (index != -1) {
                permutaAlfabet();

                char charPermutat = alfabetPermutat[index];

                if (Character.isLowerCase(c)) {
                    resultat.append(Character.toLowerCase(charPermutat));
                } else {
                    resultat.append(charPermutat);
                }
            } else {
                resultat.append(c);
            }
        }

        return resultat.toString();
    }

    public static String desxifraPoliAlfa(String msgXifrat) {
        StringBuilder resultat = new StringBuilder();

        for (char c : msgXifrat.toCharArray()) {

            char maj = Character.toUpperCase(c);
            int index = lletres.indexOf(maj);

            if (index != -1) {

                permutaAlfabet();

                int index2 = -1;
                for (int i = 0; i < alfabetPermutat.length; i++) {
                    if (alfabetPermutat[i] == maj) {
                        index2 = i;
                        break;
                    }
                }

                if (index2 != -1) {

                    char charNormal = lletres.charAt(index2);

                    if (Character.isLowerCase(c)) {
                        resultat.append(Character.toLowerCase(charNormal));
                    } else {
                        resultat.append(charNormal);
                    }
                }
            } else {
                resultat.append(c);
            }
        }
        return resultat.toString();
    }

}
