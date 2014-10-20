package com.mashape.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Created by yxzhao on 10/20/14.
 */
public class JerseyTestClient {
    public static void main(String[] args) {
        try {

            Client client = Client.create();

            WebResource webResource = client.resource("http://localhost:8888/tasks/5444c5593004c68635cf0055");

            String input = "{\"content\":\"HAD CONTENT.0.2079524875003187\",\"done\":\"false\",\"taskId\":\"5444c5593004c68635cf0055\",\"title\":\"Task title0.19796036818903973\"}";

            ClientResponse response = webResource.type("application/json").put(ClientResponse.class, input);

            System.out.println("Output from Server .... \n");
            String output = response.getEntity(String.class);
            System.out.println(output);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
