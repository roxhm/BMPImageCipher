// Lógica de cifrado/descifrado con DES

package crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ImageEncryptor {
    public static byte[] cifrar(byte[] textoPlano, String llave, String modo, String vectorInicializacion) throws Exception {
        byte[] vi = vectorInicializacion.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("DES/" + modo + "/NoPadding");
        SecretKeySpec key = new SecretKeySpec(llave.getBytes("UTF-8"), "DES");

        if (modo.equals("ECB")) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } else
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(vectorInicializacion.getBytes("UTF-8")));
        return cipher.doFinal(textoPlano);
    }

    public static byte[] descifrar(byte[] textoCifrado, String llave, String modo, String vectorInicializacion) throws Exception {
        byte[] vi = vectorInicializacion.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("DES/" + modo + "/NoPadding");
        SecretKeySpec key = new SecretKeySpec(llave.getBytes("UTF-8"), "DES");

        if (modo.equals("ECB")) {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } else
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(vectorInicializacion.getBytes("UTF-8")));
        return cipher.doFinal(textoCifrado);
    }
}
