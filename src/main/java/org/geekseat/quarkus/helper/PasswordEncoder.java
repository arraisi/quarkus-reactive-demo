package org.geekseat.quarkus.helper;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.RequestScoped;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@RequestScoped
public class PasswordEncoder {
    private final String SECRET = "geekseat";
    private final Integer ITERATION = 30;
    private final Integer KEY_LENGTH = 255;
    private final String INSTANCE_KEY = "PBKDF2WithHmacSHA512";

    public String encode(CharSequence cs) {
        try {
            byte[] result = SecretKeyFactory.getInstance(INSTANCE_KEY)
                    .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), SECRET.getBytes(), ITERATION, KEY_LENGTH))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }
}
