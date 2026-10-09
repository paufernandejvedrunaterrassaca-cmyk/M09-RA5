import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "AquestaEsLaMevaClau";

    public static void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet",
                         "Hola Andrés cómo está tu cuñado",
                         "Àgora ïlla Ôtto"};

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];

            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: "
                        + e.getLocalizedMessage());
            }
            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }

    public static byte[] xifraAES(String msg, String clau) throws Exception {
        byte[] bMsg = msg.getBytes("UTF-8");

        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = md.digest(clau.getBytes("UTF-8"));
        SecretKeySpec key = new SecretKeySpec(hash, ALGORISME_XIFRAT);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] xifrat = cipher.doFinal(bMsg);

        byte[] resultat = new byte[MIDA_IV + xifrat.length];
        System.arraycopy(iv, 0, resultat, 0, MIDA_IV);
        System.arraycopy(xifrat, 0, resultat, MIDA_IV, xifrat.length);
        return resultat;
    }

    public static String desxifraAES(byte[] bIvMsgXifrat, String clau) throws Exception {
        byte[] ivRebut = new byte[MIDA_IV];
        System.arraycopy(bIvMsgXifrat, 0, ivRebut, 0, MIDA_IV);
        IvParameterSpec ivSpec = new IvParameterSpec(ivRebut);

        byte[] xifrat = new byte[bIvMsgXifrat.length - MIDA_IV];
        System.arraycopy(bIvMsgXifrat, MIDA_IV, xifrat, 0, xifrat.length);

        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = md.digest(clau.getBytes("UTF-8"));
        SecretKeySpec key = new SecretKeySpec(hash, ALGORISME_XIFRAT);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] bMsg = cipher.doFinal(xifrat);

        return new String(bMsg, "UTF-8");
    }
}