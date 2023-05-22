package me.empresta.PubSub;

import com.google.gson.Gson;

public class Item_Request_Message {

    private String header;
    private String clock;
    private String nonce;
    private String hash;
    private String signature;
    private String sender;
    private String description;
    private String name;


    @Override
    public String toString() {
        return "Item_Announcement_Message{" +
                "header='" + header + '\'' +
                ", clock='" + clock + '\'' +
                ", nonce='" + nonce + '\'' +
                ", hash='" + hash + '\'' +
                ", signature='" + signature + '\'' +
                ", sender='" + sender + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static Item_Request_Message fromJson(String json) {
        Gson gson = new Gson();
        Item_Request_Message vouchMessage = gson.fromJson(json, Item_Request_Message.class);
        return vouchMessage;
    }

    public static String toJson(Item_Request_Message vouchMessage) {
        Gson gson = new Gson();
        String json = gson.toJson(vouchMessage);
        return json;
    }


    public boolean check_signature(){
        //TODO: Implement hash test
        return true;
    }
    public boolean check_sender(String exchange){
        return exchange==this.sender;
    }
    public boolean check_nonce(){
        //TODO: Implement Nonce Check where we test for the first x digits of the hash
        return true;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

