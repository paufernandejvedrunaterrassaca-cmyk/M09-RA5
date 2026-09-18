public class Rot13 {

    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";

    public static final char[] minuscules = alfabet.toCharArray();
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();

    public static void main(String[] args) {

        String[] msgs = {"ABC", "XYZ", "Hola Mr. calçot", "Perdó, per tu què és?"};

        String[] msgsXifrats = new String[msgs.length];

        System.out.println("\nXifrat\n---------");

        for (int i = 0; i < msgs.length; i++) {
            msgsXifrats[i] = xifraRot13(msgs[i]);
            System.out.printf("%-23s => %s%n", msgs[i] + msgsXifrats[i]);
        }

        System.out.println("\nDesXifrat\n---------");

        for(String msg: msgsXifrats) {
            System.out.printf("%-23s => %s%n", msg + desxifraRot13(msg));
        }
    }

    public static String xifraRot13(String cadena) {
        String resultat = "";

        for (int i = 0; i < cadena.length(); i++) {
            char cadena2 = cadena.charAt(i);

            for (int j = 0; j < minuscules.length(); j++) {
                if (cadena2 == minuscules[j]) {
                    resultat += minuscules[(j + 13)];
                }
            }
        }
        

        return resultat;

    }

    public static String desxifraRot13(String cadena) {
        return "";
    }
}