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
import java.security.interfaces.ECPublicKey;

import javax.inject.Inject;

import me.empresta.Crypto.Base58;
import me.empresta.Crypto.KeyGen;
import me.empresta.Crypto.Utils;
import me.empresta.DAO.Account;
import me.empresta.DAO.AccountDao;
import me.empresta.DI.Repository;

public class RegisterUseCase {
     @Inject
     Repository repository;
    @Inject
    public RegisterUseCase(Repository repo){
        this.repository = repo;
    }
    public boolean Register(
        String nickName,
        String description,
        String contact
    ) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {

        // Store data to the Persistent Database

        String custumization = "";

        KeyPair pair = KeyGen.CreateKeyPair();


        byte[] keybytes = Utils.uncompressedToCompressed(Utils.toUncompressedPoint((ECPublicKey) KeyGen.getPubKey()));
        String keyBase58 = Base58.encode(keybytes);

        Account account = new Account(keyBase58,nickName,description,contact,"");


        AsyncTask.execute(() -> {
            repository.deleteAccounts();
            //Insert Data
            repository.insertAccount(account);
        });

        return true;
    }

}
