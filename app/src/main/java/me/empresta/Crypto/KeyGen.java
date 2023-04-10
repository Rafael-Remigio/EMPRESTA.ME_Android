package me.empresta.Crypto;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.ECGenParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class KeyGen {

    public static KeyPair CreateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyPairGenerator.initialize(
                    new KeyGenParameterSpec.Builder(
                            "key1",
                            KeyProperties.PURPOSE_SIGN)
                            .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                            .setDigests(KeyProperties.DIGEST_SHA256,
                                    KeyProperties.DIGEST_SHA384,
                                    KeyProperties.DIGEST_SHA512)
                            // Only permit the private key to be used if the user authenticated
                            // within the last five minutes.
                            .setUserAuthenticationRequired(true)
                            .setUserAuthenticationValidityDurationSeconds(5 * 60)
                            .build());
        }
        else {
            keyPairGenerator.initialize(1024);
        }


        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }


    public static Certificate getPubCert() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        return keyStore.getCertificate("key1");
    }


    public static PublicKey getPubKey() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        return keyStore.getCertificate("key1").getPublicKey();
    }

    public PrivateKey getPrivKey() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        return (PrivateKey) keyStore.getKey("key1", null);
    }

    public String encrypt(String data) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        PublicKey publicKey = getPubKey();
        Cipher cipher = Cipher.getInstance("EC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] byteArray = data.getBytes();
        byte[] bytes = cipher.doFinal(byteArray);
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public String decrypt(String data) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnrecoverableKeyException {
        PrivateKey prvKey = getPrivKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, prvKey);
        byte[] encryptedData = Base64.decode(data, Base64.DEFAULT);
        byte[] decodedData = cipher.doFinal(encryptedData);
        return new String(decodedData);
    }





}