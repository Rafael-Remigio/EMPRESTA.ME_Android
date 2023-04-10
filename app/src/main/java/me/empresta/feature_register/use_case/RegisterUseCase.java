package me.empresta.feature_register.use_case;

import android.os.AsyncTask;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.inject.Inject;

import me.empresta.Crypto.KeyGen;
import me.empresta.DAO.Account;
import me.empresta.DAO.AccountDao;

public class RegisterUseCase {
     @Inject
     AccountDao accountDao;
    @Inject
    public RegisterUseCase(AccountDao dao){
        this.accountDao = dao;
    }
    public boolean Register(
        String nickName,
        String description,
        String contact
    ) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {

        // Store data to the Persistent Database

        String custumization = "";

        KeyPair pair = KeyGen.CreateKeyPair();

        Certificate publicKey = KeyGen.getPubCert();

        Account account = new Account(publicKey.getEncoded(),nickName,description,contact,"");


        AsyncTask.execute(() -> {
            //Insert Data
            accountDao.insertAccount(account);
        });

        return true;
    }

}
