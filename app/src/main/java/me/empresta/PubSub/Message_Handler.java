package me.empresta.PubSub;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import me.empresta.DAO.Vouch;
import me.empresta.DI.Repository;

public class Message_Handler {

    static int[][] matrix;

    @Inject
    Repository repository;

    @Inject
    public Message_Handler(Repository repo){
        this.repository = repo;
    }

    public void Handle(String message, String exchange){
        System.out.println(message);


        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String header = jsonObject.get("header").getAsString();

        switch (header) {
            case "VOUCH":
                Handle_Vouch(Vouch_Message.fromJson(message), exchange);
                break;
            case "A":
                System.out.println("Header is A");
                break;
            case "B":
                System.out.println("Header is B");
                break;
            case "C":
                System.out.println("Header is C");
                break;
            default:
                System.out.println("Header is not VOUCH, A, B, or C");
                break;
        }


    }
    public  void Handle_Vouch(Vouch_Message message, String exchange){

        // Validations
        if(message.check_nonce())
            return;

        if(message.check_sender(exchange))
            return;

        if(message.check_signature())
            return;

        //Reconstruction
            // Add this vouch to the list of saved vouches so that the matrix can be created later when needed



        System.out.println();

    }

}
