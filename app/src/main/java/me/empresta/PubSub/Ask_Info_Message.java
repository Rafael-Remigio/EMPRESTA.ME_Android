package me.empresta.PubSub;

import com.google.gson.Gson;

public class Ask_Info_Message {

    private String header;
    private String clock;
    private String nonce;
    private String hash;
    private String signature;
    private String sender;
    private String receiver;
    private String message;


    @Override
    public String toString() {
        return "Ask_Info_Message{" +
                "header='" + header + '\'' +
                ", clock='" + clock + '\'' +
                ", nonce='" + nonce + '\'' +
                ", hash='" + hash + '\'' +
                ", signature='" + signature + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static Ask_Info_Message fromJson(String json) {
        Gson gson = new Gson();
        Ask_Info_Message vouchMessage = gson.fromJson(json, Ask_Info_Message.class);
        return vouchMessage;
    }

    public static String toJson(Ask_Info_Message vouchMessage) {
        Gson gson = new Gson();
        String json = gson.toJson(vouchMessage);
        return json;
    }


    public boolean check_signature(){
        //TODO: Implement hash test
        return true;
    }
    public boolean check_sender(String exchange){
        return exchange.equals( this.sender);
    }
    public boolean check_receiver(String my_pbk){
        return my_pbk.equals( this.receiver);
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {return message; }

    public void setMessage(String message) { this.message = message;}

}
