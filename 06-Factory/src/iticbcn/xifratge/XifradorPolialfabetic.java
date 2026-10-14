
package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    private long obtenirClau(String clau) throws ClauNoSuportada {
        try {
            if (clau == null) {
                throw new NumberFormatException();
            }

            return Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada(
                    "La clau polialfabètica ha de ser un nombre enter llarg.");
        }
    }

    private String transforma(String msg, long clau, boolean xifra) {
        Random random = new Random(clau);
        StringBuilder resultat = new StringBuilder();

        for (char c : msg.toCharArray()) {
            int desplacament = random.nextInt(26);

            if (c >= 'A' && c <= 'Z') {
                int valor = xifra
                        ? c - 'A' + desplacament
                        : c - 'A' - desplacament;

                resultat.append((char) ('A' + Math.floorMod(valor, 26)));
            } else if (c >= 'a' && c <= 'z') {
                int valor = xifra
                        ? c - 'a' + desplacament
                        : c - 'a' - desplacament;

                resultat.append((char) ('a' + Math.floorMod(valor, 26)));
            } else {
                resultat.append(c);
            }
        }

        return resultat.toString();
    }

    @Override
    public TextXifrat xifra(String msg, String clau)
            throws ClauNoSuportada {
        if (msg == null) {
            throw new ClauNoSuportada("El missatge no pot ser null.");
        }

        String resultat = transforma(msg, obtenirClau(clau), true);

        return new TextXifrat(resultat.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau)
            throws ClauNoSuportada {
        if (xifrat == null) {
            throw new ClauNoSuportada("El text xifrat no pot ser null.");
        }

        String msg = new String(xifrat.getBytes(), StandardCharsets.UTF_8);

        return transforma(msg, obtenirClau(clau), false);
    }
}
