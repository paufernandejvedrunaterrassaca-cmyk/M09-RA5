import java.util.ArrayList;
import java.util.Collections;

public class Monoalfabetic {
    
    public final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static void main(String[] args) {
        Monoalfabetic mono = new Monoalfabetic();
    }

    public char[] permutaAlfabet(String alfabet) {
        ArrayList<Character> llista = new ArrayList<>();

        for (char c : alfabet.toCharArray()) {
            llista.add(c);
        }

        Collections.shuffle(llista);
        char[] resultat = new char[llista.size()]

        for (int i = 0; i < llista.size(); i++) {
            resultat[i] = llista.get(i);
        }
        
        return resultat;

    }

    public String xifraMonoAlfa(String cadena) {
        String resultat = "";

        
        return resultat;
    }

    public String desxifraMonoAlfa(String cadena) {
        return "";
    }  
    
}
