
package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    private String transforma(String msg, int desplacament) {
        StringBuilder resultat = new StringBuilder();

        for (char c : msg.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                resultat.append((char) ('A' +
                        Math.floorMod(c - 'A' + desplacament, 26)));
            } else if (c >= 'a' && c <= 'z') {
                resultat.append((char) ('a' +
                        Math.floorMod(c - 'a' + desplacament, 26)));
            } else {
                resultat.append(c);
            }
        }

        return resultat.toString();
    }

    private int obtenirClau(String clau) throws ClauNoSuportada {
        try {
            return Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau ROT-X ha de ser un enter.");
        }
    }

    @Override
    public TextXifrat xifra(String msg, String clau)
            throws ClauNoSuportada {
        if (msg == null || clau == null) {
            throw new ClauNoSuportada("El missatge i la clau no poden ser null.");
        }

        return new TextXifrat(
                transforma(msg, obtenirClau(clau))
                        .getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau)
            throws ClauNoSuportada {
        if (xifrat == null || clau == null) {
            throw new ClauNoSuportada("El text xifrat i la clau no poden ser null.");
        }

        return transforma(
                new String(xifrat.getBytes(),
                        java.nio.charset.StandardCharsets.UTF_8),
                -Math.floorMod(obtenirClau(clau), 26));
    }
}
