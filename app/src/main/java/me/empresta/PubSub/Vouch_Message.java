package me.empresta.PubSub;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Vouch_Message{
    private String header;
    private String state;
    private String clock;
    private String sender;
    private String receiver;
    private String message;
    private String nonce;
    private String hash;


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

    public static List<List<Integer>> Get_Matrix(List<Integer> idOrder, List<String> nodes) {

        // Creates an N*N matrix
        int size = nodes.size();
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            matrix.add(row);
        }
        /*
        // Goes through each node...
        for (int nodeIdx = 0; nodeIdx < idOrder.size(); nodeIdx++) {
            int nodeId = idOrder.get(nodeIdx);

            // ... and each vouch
            for (int otherId : nodes.get(nodeId).getVouches().keySet()) {
                int otherIdx = idOrder.indexOf(otherId); // Gets the idx from the ordered list

                // Don't check for connections if already exists
                if (matrix.get(nodeIdx).get(otherIdx) != 0) {
                    continue;
                }

                // If they vouch for each other...
                if (hasPositiveConnection(nodeId, otherId)) {
                    matrix.get(nodeIdx).set(otherIdx, 1);
                    matrix.get(otherIdx).set(nodeIdx, 1);
                }
                // If they vouch against each other...
                else if (hasNegativeConnection(nodeId, otherId)) {
                    matrix.get(nodeIdx).set(otherIdx, -1);
                    matrix.get(otherIdx).set(nodeIdx, -1);
                }
            }
        }
        */
        return matrix;
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
