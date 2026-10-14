
package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class XifradorMonoalfabetic implements Xifrador {
    private final char[] alfabet = new char[26];
    private final char[] permutacio = new char[26];

    public XifradorMonoalfabetic() {
        for (int i = 0; i < 26; i++) {
            alfabet[i] = (char) ('A' + i);
            permutacio[i] = alfabet[i];
        }

        List<Character> llista = new ArrayList<>();

        for (char c : alfabet) {
            llista.add(c);
        }

        Collections.shuffle(llista, new Random());

        for (int i = 0; i < 26; i++) {
            permutacio[i] = llista.get(i);
        }
    }

    @Override
    public TextXifrat xifra(String msg, String clau)
            throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("La clau no pot ser null.");
        }

        if (msg == null) {
            throw new ClauNoSuportada("El missatge no pot ser null.");
        }

        StringBuilder resultat = new StringBuilder();

        for (char c : msg.toCharArray()) {
            char majuscula = Character.toUpperCase(c);

            if (majuscula >= 'A' && majuscula <= 'Z') {
                char xifrat = permutacio[majuscula - 'A'];

                resultat.append(Character.isLowerCase(c)
                        ? Character.toLowerCase(xifrat) : xifrat);
            } else {
                resultat.append(c);
            }
        }

        return new TextXifrat(
                resultat.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau)
            throws ClauNoSuportada {
        if (clau == null) {
            throw new ClauNoSuportada("La clau no pot ser null.");
        }

        if (xifrat == null) {
            throw new ClauNoSuportada("El text xifrat no pot ser null.");
        }

        String msg = new String(xifrat.getBytes(), StandardCharsets.UTF_8);
        StringBuilder resultat = new StringBuilder();

        for (char c : msg.toCharArray()) {
            char majuscula = Character.toUpperCase(c);

            if (majuscula >= 'A' && majuscula <= 'Z') {
                int posicio = -1;

                for (int i = 0; i < 26; i++) {
                    if (permutacio[i] == majuscula) {
                        posicio = i;
                        break;
                    }
                }

                char original = alfabet[posicio];

                resultat.append(Character.isLowerCase(c)
                        ? Character.toLowerCase(original) : original);
            } else {
                resultat.append(c);
            }
        }

        return resultat.toString();
    }
}
