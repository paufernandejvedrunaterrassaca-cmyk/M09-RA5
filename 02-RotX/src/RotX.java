public class RotX {

    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";

    public static final char[] minuscules = alfabet.toCharArray();
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();
public static void main(String[] args) {

    String[] msgs = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};

    String[] msgsXifrats = new String[msgs.length];

    int[] desplacaments = {0, 2, 4, 6};

    System.out.println("\nXifrat");
    System.out.println("------");

    for (int i = 0; i < msgs.length; i++) {
        msgsXifrats[i] = xifraRotX(msgs[i], desplacaments[i]);

        System.out.printf("(%d)-%-25s => %s%n", desplacaments[i], msgs[i], msgsXifrats[i]);
    }

    System.out.println("\nDesxifrat");
    System.out.println("---------");

    for (int i = 0; i < msgs.length; i++) {

        System.out.printf("(%d)-%-25s => %s%n", desplacaments[i], msgsXifrats[i], desxifraRotX(msgsXifrats[i], desplacaments[i]));
    }

    System.out.println();
    System.out.println("Força Bruta");
    System.out.println("-----------");

    System.out.println("Missatge xifrat: " + msgsXifrats[3]);
    System.out.println();

    forcaBrutaRotX(msgsXifrats[3]);
    
}

    public static String xifraRotX(String cadena, int desplaçament) {
        String textXifrat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            boolean trobat = false;

            for (int j = 0; j < minuscules.length; j++) {
                if (c == minuscules[j]) {
                    textXifrat += minuscules[(j + desplaçament) % minuscules.length];
                    trobat = true;
                    break;
                }
            }

            if (!trobat) {
                for (int j = 0; j < majuscules.length; j++) {
                    if (c == majuscules[j]) {
                        textXifrat += majuscules[(j + desplaçament) % majuscules.length];
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

    public static String desxifraRotX(String cadena, int desplaçament) {
        String resultat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            boolean trobat = false;

            for (int j = 0; j < minuscules.length; j++) {
                if (c == minuscules[j]) {
                    resultat += minuscules[(j - desplaçament + minuscules.length) % minuscules.length];
                    trobat = true;
                    break;
                }
            }

            if (!trobat) {
                for (int j = 0; j < majuscules.length; j++) {
                    if (c == majuscules[j]) {
                        resultat += majuscules[(j - desplaçament + majuscules.length) % majuscules.length];
                        trobat = true;
                        break;
                    }
                }
            }

            if (!trobat) {
                resultat += c;
            }
        }

        return resultat;
    }

    public static String forcaBrutaRotX(String cadenaXifrada) {

        int desplaçament = 0;
        String resultat = "";

        for (int i = 0; i < minuscules.length; i++) {
            desplaçament = i;

            String textDesxifrat = desxifraRotX(cadenaXifrada, desplaçament);
            
            System.out.printf("(%d)-%s%n", desplaçament, textDesxifrat);
    }

    return resultat;
    
    }
}