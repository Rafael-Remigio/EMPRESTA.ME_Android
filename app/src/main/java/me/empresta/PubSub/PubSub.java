package me.empresta.PubSub;


import android.util.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import me.empresta.DI.Repository;


public class PubSub{



    @Inject
    Message_Handler message_handler;
    @Inject
    Repository repository;

    @Inject
    public PubSub(Message_Handler message_handler){
        this.message_handler = message_handler;
        this.message_handler.setPubSub(this);
    }

    public  Thread start_listening(String EXCHANGE_NAME,String host)throws Exception{

        Thread subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {

                    try {
                        //System.out.println("\n\n" + host + "\n\n");

                        Log.d("DEBUG"," [x] HOST '" +  host+ "'");
                        Log.d("DEBUG"," [x]EXCHANGE_NAME '" +  EXCHANGE_NAME+ "'");

                        ConnectionFactory factory = new ConnectionFactory();
                        factory.setHost(host);
                        Connection connection = factory.newConnection();
                        Channel channel = connection.createChannel();

                        System.out.println("Waiting CTRL+C");

                        channel.queueDeclare(EXCHANGE_NAME, true, false, false, null);

                        Log.d("DEBUG"," Waiting for messages. To exit press CTRL+C");


                        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                            String message = new String(delivery.getBody(), "UTF-8");


                            Log.d("DEBUG"," [x] Received '" + message + "'");

                            try {
                                message_handler.Handle(message, EXCHANGE_NAME);
                            }
                            catch (
                                    Exception e){
                                System.out.println(e);

                            }
                            finally {
                                Log.d("DEBUG"," [x] Done");

                                GetResponse gr = channel.basicGet(EXCHANGE_NAME, false);
                                Log.d("DEBUG","Delivery Tag: " + gr.getEnvelope().getDeliveryTag() );
                                channel.basicNack(gr.getEnvelope().getDeliveryTag(), false, true);
                            }
                        };
                        boolean autoAck = false;
                        channel.basicConsume(EXCHANGE_NAME, autoAck, deliverCallback, consumerTag -> { });



                    } catch (TimeoutException | IOException e) {
                        System.out.println(e);
                        throw new RuntimeException(e);

                    }
            }
        });
        subscribeThread.start();
        return subscribeThread;
    }

    /*
    Vouch
        - Public keys
        - Vouch Type
        - Description
    */
    public static void Publish_Vouch(String host,String my_public_key, String other_public_key, String my_communities, List<LinkedHashMap<String, Object>> other_communities, String Description, Integer state){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectionFactory factory = new ConnectionFactory();

                    factory.setHost(host);
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();

                    channel.queueDeclare(my_public_key, true, false, false, null);

                    //channel.exchangeDeclare(my_public_key, "fanout"); //channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                    int nonce = 0;

                    String other_coms ="";

                    for (LinkedHashMap map:other_communities) {
                        String url = (String) map.get("url");
                        other_coms += url.substring(7,url.length() - 6)+" " ;
                    }

                    JSONObject j_message = new JSONObject();
                    j_message.put("header", "VOUCH");
                    j_message.put("clock", "clock"); //TODO: clock
                    j_message.put("hash", "hash"); //TODO: hash of the message
                    j_message.put("nonce", nonce);
                    j_message.put("signature", "signature"); //TODO: Signature
                    j_message.put("sender", my_public_key);
                    j_message.put("receiver", other_public_key);
                    j_message.put("sender_community", my_communities);
                    j_message.put("receiver_community", other_coms);
                    j_message.put("state", state);
                    j_message.put("message", Description);


                    //channel.basicPublish(my_public_key, "", null, j_message.toString().getBytes()); //channel.basicPublish("", QUEUE_NAME, null, j_message.toString().getBytes());

                    channel.basicPublish("", my_public_key,
                            null,
                            j_message.toString().getBytes());
                    System.out.println(" [x] Sent '" +  j_message.toString() + "'");

                    channel.close();
                    connection.close();

                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException("Rabbitmq problem", e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
    }

    public static void Publish_Item_Announcement_Update(String host, String my_public_key, String Name, String Description, String Photo){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectionFactory factory = new ConnectionFactory();

                    //System.out.println("\n\n\n"+host+"\n\n\n");
                    //System.out.println("\n\n\n"+my_public_key+"\n\n\n");
                    Log.d("DEBUG"," [x] HOST '" +  host+ "'");
                    Log.d("DEBUG"," [x]my_public_key '" +  my_public_key+ "'");



                    factory.setHost(host);
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();

                    channel.queueDeclare(my_public_key, true, false, false, null);
                    //channel.exchangeDeclare(my_public_key, "fanout"); //channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                    int nonce = 0; //Nonce that needs to be randomized

                    JSONObject j_message = new JSONObject();
                    j_message.put("header", "ITEM_ANNOUNCEMENT");
                    j_message.put("clock", "clock"); //TODO: clock
                    j_message.put("hash", "hash"); //TODO: hash of the message
                    j_message.put("nonce", nonce);
                    j_message.put("signature", "signature"); //TODO: Signature
                    j_message.put("sender", my_public_key);
                    j_message.put("name", Name);
                    j_message.put("description", Description);
                    j_message.put("image", Photo);
                    //System.out.println(" [x] Almost Sent '" +  j_message+ "'");

                    Log.d("DEBUG"," [x] Almost Sent '" +  j_message+ "'");
                    channel.basicPublish("", my_public_key,
                            null,
                            j_message.toString().getBytes());
                    //System.out.println(" [x] Sent '" +  j_message+ "'");
                    Log.d("DEBUG","[x] Sent '" +  j_message+ "'");
                    channel.close();
                    connection.close();

                } catch (IOException | TimeoutException e) {
                    System.out.println(e);
                    throw new RuntimeException("Rabbitmq problem", e);
                } catch (JSONException e) {
                    System.out.println(e);
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
    }


    public static void Publish_Item_Request(String host,String my_public_key, String Name, String Description){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectionFactory factory = new ConnectionFactory();
                    //System.out.println("\n\n\n"+host+"\n\n\n");
                    //System.out.println("\n\n\n"+my_public_key+"\n\n\n");
                    Log.d("DEBUG"," [x] HOST '" +  host+ "'");
                    Log.d("DEBUG"," [x]my_public_key '" +  my_public_key+ "'");


                    factory.setHost(host);
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();

                    channel.queueDeclare(my_public_key, true, false, false, null);
                    //channel.exchangeDeclare(my_public_key, "fanout"); //channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                    int nonce = 0; //Nonce that needs to be randomized

                    JSONObject j_message = new JSONObject();
                    j_message.put("header", "ITEM_REQUEST");
                    j_message.put("clock", "clock"); //TODO: clock
                    j_message.put("hash", "hash"); //TODO: hash of the message
                    j_message.put("nonce", nonce);
                    j_message.put("signature", "signature"); //TODO: Signature
                    j_message.put("sender", my_public_key);
                    j_message.put("name", Name);
                    j_message.put("description", Description);
                    Log.d("DEBUG"," [x] Almost Sent '" +  j_message+ "'");

                    //System.out.println(" [x] Almost Sent '" +  j_message+ "'");
                    channel.basicPublish("", my_public_key,
                            null,
                            j_message.toString().getBytes());
                    //System.out.println(" [x] Sent '" +  j_message + "'");

                    Log.d("DEBUG","[x] Sent '" +  j_message+ "'");
                    channel.close();
                    connection.close();

                } catch (IOException | TimeoutException e) {
                    System.out.println(e);
                    throw new RuntimeException("Rabbitmq problem", e);
                } catch (JSONException e) {
                    System.out.println(e);
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
    }

    public static void Publish_Ask_Info(String host,String my_public_key, String target_public_key, String message){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectionFactory factory = new ConnectionFactory();

                    factory.setHost(host);
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();

                    channel.queueDeclare(my_public_key, true, false, false, null);
                    //channel.exchangeDeclare(my_public_key, "fanout"); //channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                    int nonce = 0; //Nonce that needs to be randomized

                    JSONObject j_message = new JSONObject();
                    j_message.put("header", "ASK_INFO");
                    j_message.put("clock", "clock"); //TODO: clock
                    j_message.put("hash", "hash"); //TODO: hash of the message
                    j_message.put("nonce", nonce);
                    j_message.put("signature", "signature"); //TODO: Signature
                    j_message.put("sender", my_public_key);
                    j_message.put("receiver", target_public_key);
                    j_message.put("message", message);

                    channel.basicPublish("", my_public_key,
                            null,
                            j_message.toString().getBytes());
                    Log.d("DEBUG","[x] Sent '" +  j_message+ "'");

                    channel.close();
                    connection.close();

                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException("Rabbitmq problem", e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
    }

}

