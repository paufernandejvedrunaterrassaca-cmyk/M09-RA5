public class RotX {

    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";

    public static final char[] minuscules = alfabet.toCharArray();
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();
public static void main(String[] args) {

    String[] msgs = {"ABC", "XYZ", "Hola Mr. calçot", "Perdó, per tu què és?"};

    String[] msgsXifrats = new String[msgs.length];

    System.out.println("\nXifrat");
    System.out.println("---------");

    for (int i = 0; i < msgs.length; i++) {
        msgsXifrats[i] = xifraRotX(msgs[i]);
        System.out.printf("%-23s => %s%n", msgs[i], msgsXifrats[i]);
    }

    System.out.println("\nDesXifrat");
    System.out.println("---------");

    for (int i = 0; i < msgsXifrats.length; i++) {
        System.out.printf("%-23s => %s%n", msgsXifrats[i], desxifraRotX(msgsXifrats[i]));
    }
}

    public static String xifraRotX(String cadena) {
        String textXifrat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            boolean trobat = false;

            for (int j = 0; j < minuscules.length; j++) {
                if (c == minuscules[j]) {
                    textXifrat += minuscules[(j + 13) % minuscules.length];
                    trobat = true;
                    break;
                }
            }

            if (!trobat) {
                for (int j = 0; j < majuscules.length; j++) {
                    if (c == majuscules[j]) {
                        textXifrat += majuscules[(j + 13) % majuscules.length];
                        trobat = true;
                        break;
                    }
                }
            }

            if (!trobat) {
                textXifrat += c;
            }
        }
        return textXifrat;
    }

    public static String desxifraRotX(String cadena) {
       return "";
    }

    public static String forcaBrutaRotX(String cadenaXifrada) {
        return "";
    }
}