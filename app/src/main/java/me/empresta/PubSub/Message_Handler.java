package me.empresta.PubSub;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import me.empresta.DAO.Friend;
import me.empresta.DAO.InfoRequest;
import me.empresta.DAO.ItemAnnouncement;
import me.empresta.DAO.ItemRequest;
import me.empresta.DAO.Vouch;
import me.empresta.DI.Repository;

public class Message_Handler {

    static HashMap<String,Integer> connectionMap = new HashMap<String,Integer>();

    @Inject
    Repository repository;

    PubSub pubSub;


    @Inject
    public Message_Handler(Repository repo){
        this.repository = repo;
    }

    public void setPubSub(PubSub pubSub) {this.pubSub = pubSub;}



    public void Handle(String message, String exchange) throws Exception {



        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String header = jsonObject.get("header").getAsString();

        switch (header) {
            case "VOUCH":
                Handle_Vouch(Vouch_Message.fromJson(message), exchange);
                break;
            case "ITEM_ANNOUNCEMENT":
                Handle_Item_Announcement(Item_Announcement_Message.fromJson(message), exchange);
                break;
            case "ITEM_REQUEST":
                Handle_Item_Request(Item_Request_Message.fromJson(message), exchange);
                break;
            case "ASK_INFO":
                Handle_Ask_Info(Ask_Info_Message.fromJson(message),exchange);
                break;
            default:
                System.out.println("Header is not VOUCH, ITEM_ANNOUNCEMENT, ITEM_REQUEST, or ASK_INFO");
                break;
        }


    }
    public void Handle_Vouch(Vouch_Message message, String exchange)  {

        System.out.println(message);

        /*
        // Validations
        if(!message.check_nonce())
            return;

        if(!message.check_sender(exchange))
            return;

        if(!message.che1ture())
            return;

         */

        // Add this vouch to the list of saved vouches so that the matrix can be created later when needed
        repository.insertVouch(new Vouch(message.getSender() + message.getReceiver(), message.getSender(), message.getReceiver(), message.getSender_community(), message.getReceiver_community(), Integer.parseInt(message.getState()), message.getMessage()));

        try{
        if (repository.countFriends( message.getReceiver()) == 0) {
            this.pubSub.start_listening(message.getReceiver(), message.getReceiver_community().split(" ")[0]);
        }
        }  catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        repository.insertFriend(new Friend(message.getReceiver(), message.getReceiver_community().split(" ")[0]));

        System.out.println("\n\n\n");
        System.out.println(repository.getAllFriends());

    }
    public  void Handle_Item_Announcement(Item_Announcement_Message message, String exchange){

        // Validations
        /*if(!message.check_nonce())
            return;

        if(!message.check_sender(exchange))
            return;

        if(!message.check_signature())
            return;*/

        // Add this Item Announcement to the list of saved Items
        repository.insertItemAnnouncement(new ItemAnnouncement((message.getSender()+message.getName()+message.getDescription()).hashCode(), message.getSender(), message.getName(), message.getDescription(), message.getImage()));
        System.out.println("\n----\n\n");
        System.out.println(repository.getAllItemAnnouncements());
        System.out.println("\n----\n\n");
    }

    public  void Handle_Item_Request(Item_Request_Message message, String exchange){

        /*Validations
        if(!message.check_nonce())
            return;

        if(!message.check_sender(exchange))
            return;

        if(!message.check_signature())
            return;*/

        // Add this Item Request to the list of saved Items
        repository.insertItemRequest(new ItemRequest((message.getSender()+message.getName()+message.getDescription()).hashCode(), message.getSender(), message.getName(), message.getDescription()));
        System.out.println("\n----\n\n");
        System.out.println(repository.getAllItemRequests());
        System.out.println("\n----\n\n");
    }

    public  void Handle_Ask_Info(Ask_Info_Message message, String exchange){

        String my_pblk = "my_pub_key"; //TODO: get the actual public key

        if(!message.check_receiver(my_pblk))
            return;

        /*Validations
        if(!message.check_nonce())-2+3
            return;

        if(!message.check_sender(exchange))
            return;

        if(!message.check_signature())
            return;*/

        System.out.println(message);
        // Add this Ask_Info to the list of Info requests
        repository.insertInfoRequest(new InfoRequest(message.getSender(), message.getMessage()));
        System.out.println("[From DB]" + repository.getAllInfoRequests());
    }

}
