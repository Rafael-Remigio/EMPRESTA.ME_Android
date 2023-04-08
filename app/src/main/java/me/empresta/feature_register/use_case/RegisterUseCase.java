package me.empresta.feature_register.use_case;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import me.empresta.DAO.Account;
import me.empresta.DAO.AccountDao;

public class RegisterUseCase {

    static AccountDao dao;
    public RegisterUseCase(AccountDao dao){
        this.dao = dao;
    }
    public static boolean Register(
        String nickName,
        String description,
        String contact
    ){
        // Generate Key Pairs
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator
                    .getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        // getting key pairs
        // using genKeyPair() method
        KeyPair kp = kpg.genKeyPair();

        // Store data to the Persistent Database

        String custumization = "";
        Account account = new Account("Account",kp.getPrivate().getEncoded(),kp.getPublic().getEncoded(),nickName,description,contact,custumization);

        dao.insertAccount(account);

        return true;
    }

}
