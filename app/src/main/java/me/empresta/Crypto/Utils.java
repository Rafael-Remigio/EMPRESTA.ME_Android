package me.empresta.Crypto;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;


public class Utils {

    public static final String algorithm = "SHA256withECDSA";
    static org.bouncycastle.jce.spec.ECNamedCurveParameterSpec SPEC = org.bouncycastle.jce.ECNamedCurveTable.getParameterSpec("secp256r1");

    public static boolean verifySignature(PublicKey publicKey,byte[] challenge,byte[] response) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        Signature sign = Signature.getInstance(algorithm);
        sign.initVerify(publicKey);
        sign.update(challenge);

        return sign.verify(response);
    }
    private static final byte UNCOMPRESSED_POINT_INDICATOR = 0x04;


    public static ECPublicKey fromUncompressedPoint(
            final byte[] uncompressedPoint)
            throws Exception {

        AlgorithmParameters parameters = AlgorithmParameters.getInstance("EC");
        parameters.init(new ECGenParameterSpec("secp256r1"));
        ECParameterSpec params = parameters.getParameterSpec(ECParameterSpec.class);
        
        int offset = 0;
        if (uncompressedPoint[offset++] != UNCOMPRESSED_POINT_INDICATOR) {
            throw new IllegalArgumentException(
                    "Invalid uncompressedPoint encoding, no uncompressed point indicator");
        }

        int keySizeBytes = (params.getOrder().bitLength() + Byte.SIZE - 1)
                / Byte.SIZE;

        if (uncompressedPoint.length != 1 + 2 * keySizeBytes) {
            throw new IllegalArgumentException(
                    "Invalid uncompressedPoint encoding, not the correct size");
        }

        final BigInteger x = new BigInteger(1, Arrays.copyOfRange(
                uncompressedPoint, offset, offset + keySizeBytes));
        offset += keySizeBytes;
        final BigInteger y = new BigInteger(1, Arrays.copyOfRange(
                uncompressedPoint, offset, offset + keySizeBytes));
        final java.security.spec.ECPoint w = new java.security.spec.ECPoint(x, y);
        final ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(w, params);
        final KeyFactory keyFactory = KeyFactory.getInstance("EC");
        ECPublicKey pubkey = (ECPublicKey) keyFactory.generatePublic(ecPublicKeySpec);
        return pubkey;
    }

    public static byte[] toUncompressedPoint(final ECPublicKey publicKey) {

        int keySizeBytes = (publicKey.getParams().getOrder().bitLength() + Byte.SIZE - 1)
                / Byte.SIZE;

        final byte[] uncompressedPoint = new byte[1 + 2 * keySizeBytes];
        int offset = 0;
        uncompressedPoint[offset++] = 0x04;

        final byte[] x = publicKey.getW().getAffineX().toByteArray();
        if (x.length <= keySizeBytes) {
            System.arraycopy(x, 0, uncompressedPoint, offset + keySizeBytes
                    - x.length, x.length);
        } else if (x.length == keySizeBytes + 1 && x[0] == 0) {
            System.arraycopy(x, 1, uncompressedPoint, offset, keySizeBytes);
        } else {
            throw new IllegalStateException("x value is too large");
        }
        offset += keySizeBytes;

        final byte[] y = publicKey.getW().getAffineY().toByteArray();
        if (y.length <= keySizeBytes) {
            System.arraycopy(y, 0, uncompressedPoint, offset + keySizeBytes
                    - y.length, y.length);
        } else if (y.length == keySizeBytes + 1 && y[0] == 0) {
            System.arraycopy(y, 1, uncompressedPoint, offset, keySizeBytes);
        } else {
            throw new IllegalStateException("y value is too large");
        }
        return uncompressedPoint;
    }





    static byte[] concat(byte[]...arrays)
    {
        // Determine the length of the result array
        int totalLength = 0;
        for (int i = 0; i < arrays.length; i++)
        {
            totalLength += arrays[i].length;
        }
    
        // create the result array
        byte[] result = new byte[totalLength];
    
        // copy the source arrays into the result array
        int currentIndex = 0;
        for (int i = 0; i < arrays.length; i++)
        {
            System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
            currentIndex += arrays[i].length;
        }
    
        return result;
    }



    public static byte[] uncompressedToCompressed(byte[] uncompKey){
        org.bouncycastle.math.ec.ECPoint point = SPEC.getCurve().decodePoint(uncompKey);
    
    
        return point.getEncoded(true);
    
    
    }
    
     public static byte[] compressedToUncompressed(byte[] compKey) {
         org.bouncycastle.math.ec.ECPoint point = SPEC.getCurve().decodePoint(compKey);
         byte[] x = point.getXCoord().getEncoded();
         byte[] y = point.getYCoord().getEncoded();
         // concat 0x04, x, and y, make sure x and y has 32-bytes:
         return concat( concat(new byte[] {0x04},x), y);
     }




}
