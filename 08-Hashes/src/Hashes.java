
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {

    private static final String CHARSET =
            "abcdefABCDEF1234567890!";

    private static final int ITERATIONS = 845;
    private static final int KEY_LENGTH = 128;
    private static final int SALT_LENGTH = 16;

    public long npass = 0;

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";

        Hashes h = new Hashes();

        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};

        for(int i=0; i< aHashes.length; i++){
            System.out.printf("============================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n",aHashes[i]);
            System.out.printf("------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("------------------------\n\n");
        }
    }

    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] bytes = md.digest(
                    (salt + pw).getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(bytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPBKDF2AmbSalt(String pw, String salt) {
        PBEKeySpec spec = null;

        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

            spec = new PBEKeySpec(
                    pw.toCharArray(),
                    salt.getBytes(StandardCharsets.UTF_8),
                    ITERATIONS,
                    KEY_LENGTH
            );

            byte[] bytes = factory.generateSecret(spec).getEncoded();

            return HexFormat.of().formatHex(bytes);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } finally {
            if (spec != null) {
                spec.clearPassword();
            }
        }
    }

    public String forcaBruta(String alg, String hash, String salt) {
        npass = 0;

        // La contrasenya de l'exercici té 6 caràcters.
        char[] password = new char[6];

        return provarCombinacions(
                alg, hash, salt, password, 0
        );
    }

    private String provarCombinacions(
            String alg,
            String hash,
            String salt,
            char[] password,
            int posicio) {

        if (posicio == password.length) {
            npass++;

            String candidata = new String(password);
            String hashCandidat;

            if (alg.equalsIgnoreCase("SHA-512")) {
                hashCandidat = getSHA512AmbSalt(candidata, salt);
            } else if (alg.equalsIgnoreCase("PBKDF2")) {
                hashCandidat = getPBKDF2AmbSalt(candidata, salt);
            } else {
                throw new IllegalArgumentException(
                        "Algorisme no suportat: " + alg
                );
            }

            if (hashCandidat.equalsIgnoreCase(hash)) {
                return candidata;
            }

            return null;
        }

        for (int i = 0; i < CHARSET.length(); i++) {
            password[posicio] = CHARSET.charAt(i);

            String resultat = provarCombinacions(
                    alg, hash, salt, password, posicio + 1
            );

            if (resultat != null) {
                return resultat;
            }
        }

        return null;
    }

    public String getInterval(long t1, long t2) {
        long diferencia = t2 - t1;

        long millis = diferencia % 1000;
        diferencia /= 1000;

        long segons = diferencia % 60;
        diferencia /= 60;

        long minuts = diferencia % 60;
        diferencia /= 60;

        long hores = diferencia % 24;
        diferencia /= 24;

        long dies = diferencia;

        return String.format(
                "%d dies / %d hores / %d minuts / %d segons / %d millis",
                dies, hores, minuts, segons, millis
        );
    }

    public String generarSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);

        return HexFormat.of().formatHex(salt);
    }
}
