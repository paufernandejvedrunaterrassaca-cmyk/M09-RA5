
package iticbcn.xifratge;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    private SecretKeySpec obtenirClau(String clau)
            throws GeneralSecurityException {
        byte[] bytes = MessageDigest.getInstance("SHA-256")
                .digest(clau.getBytes(StandardCharsets.UTF_8));

        return new SecretKeySpec(bytes, "AES");
    }

    private void errorFatal(GeneralSecurityException e) {
        System.err.println("Error de xifratge AES: " + e.getMessage());
        System.exit(1);
    }

    @Override
    public TextXifrat xifra(String msg, String clau)
            throws ClauNoSuportada {
        if (msg == null || clau == null) {
            throw new ClauNoSuportada(
                    "El missatge i la clau no poden ser null.");
        }

        try {
            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, obtenirClau(clau),
                    new GCMParameterSpec(TAG_LENGTH, iv));

            byte[] dades = cipher.doFinal(
                    msg.getBytes(StandardCharsets.UTF_8));

            ByteBuffer buffer = ByteBuffer.allocate(iv.length + dades.length);
            buffer.put(iv);
            buffer.put(dades);

            return new TextXifrat(buffer.array());
        } catch (GeneralSecurityException e) {
            errorFatal(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau)
            throws ClauNoSuportada {
        if (xifrat == null || clau == null) {
            throw new ClauNoSuportada(
                    "El text xifrat i la clau no poden ser null.");
        }

        byte[] dades = xifrat.getBytes();

        if (dades.length < IV_LENGTH + 16) {
            System.err.println("Error de xifratge AES: dades no vàlides.");
            System.exit(1);
            throw new IllegalStateException("Dades no vàlides");
        }

        try {
            ByteBuffer buffer = ByteBuffer.wrap(dades);
            byte[] iv = new byte[IV_LENGTH];
            buffer.get(iv);

            byte[] contingut = new byte[buffer.remaining()];
            buffer.get(contingut);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, obtenirClau(clau),
                    new GCMParameterSpec(TAG_LENGTH, iv));

            return new String(cipher.doFinal(contingut),
                    StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            errorFatal(e);
            throw new IllegalStateException(e);
        }
    }
}
