package me.empresta.PubSub;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Vouch_Message{
    private String header;
    private String state;
    private String clock;
    private String signature;
    private String sender;
    private String sender_community;
    private String receiver;
    private String receiver_community;
    private String message;
    private String nonce;
    private String hash;

    @Override
    public String toString() {
        return "Vouch_Message{" +
                "header='" + header + '\'' +
                ", state='" + state + '\'' +
                ", clock='" + clock + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message='" + message + '\'' +
                ", nonce='" + nonce + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSender_community() {
        return sender_community;
    }

    public void setSender_community(String sender_community) {
        this.sender_community = sender_community;
    }

    public String getReceiver_community() {
        return receiver_community;
    }

    public void setReceiver_community(String receiver_community) {
        this.receiver_community = receiver_community;
    }

    public Vouch_Message(String header, String state, String clock, String sender, String receiver, String message, String nonce, String hash) {
        this.header = header;
        this.state = state;
        this.clock = clock;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.nonce = nonce;
        this.hash = hash;
    }

    public static Vouch_Message fromJson(String json) {
        Gson gson = new Gson();
        Vouch_Message vouchMessage = gson.fromJson(json, Vouch_Message.class);
        return vouchMessage;
    }

    public static String toJson(Vouch_Message vouchMessage) {
        Gson gson = new Gson();
        String json = gson.toJson(vouchMessage);
        return json;
    }


    public boolean check_signature(){
        //TODO: Implement hash test
        return true;
    }
    public boolean check_sender(String exchange){
        return exchange==this.sender || exchange==this.receiver;
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

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getClock() {
        return clock;
    }
    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
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

}
