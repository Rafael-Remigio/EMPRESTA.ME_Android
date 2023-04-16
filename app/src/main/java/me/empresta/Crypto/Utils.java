package me.empresta.Crypto;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;


public class Utils {

    public static final String algorithm = "SHA256withECDSA";

    public static boolean verifySignature(byte[] publicKeyBytes,byte[] challenge,byte[] response) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        Signature sign = Signature.getInstance(algorithm);
        sign.initVerify(publicKey);
        sign.update(challenge);

        return sign.verify(response);
    }
    private static ECParameterSpec params;
    private static final byte UNCOMPRESSED_POINT_INDICATOR = 0x04;




}
