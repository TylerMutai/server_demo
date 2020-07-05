package com.testing.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
//All our api request URLs will start with /api and will return Json
public class ClientServerController {
    final String SERVERS_FILE = "servers";

    //clients registering as servers.
    @PostMapping(value = "/server/register")
    public Map<String, String> registerClientAsServer(HttpServletRequest request) throws IOException {
        //Get our servers file. TODO: These operations will definitely be handled by a database.
        String clientIP = request.getRemoteAddr();
        String randomString = getAlphaNumericString(3);
        String serverName = "Server_" + randomString;
        System.out.println(clientIP);
        System.out.println(serverName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(SERVERS_FILE, true));
        writer.write(serverName + "," + clientIP);
        writer.close();
        return Collections.singletonMap("message", "Successfully set up. Your server name is: " + serverName +
                " with an IP address of: " + clientIP);
    }


    //clients registering as clients
    @GetMapping(value = "/server/view")
    public ResponseEntity<Map<String, ArrayList<String>>> getClientsRegisteredAsServers() throws IOException {
        //JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
        File file = new File(SERVERS_FILE);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
          /*  jsonResponse.add("message", "No Clients to connect to are available. You can be one by" +
                    " clicking on the be a server button");
            return new ResponseEntity<>(jsonResponse.build().toString(), HttpStatus.NOT_FOUND);*/
            return new ResponseEntity<>(Collections.singletonMap("response", new ArrayList<>()), HttpStatus.NOT_FOUND);
        }
        String st;
        ArrayList<String> builder = new ArrayList<>();
        while ((st = br.readLine()) != null)
            builder.add(st);
        return ResponseEntity.ok(Collections.singletonMap("response", builder));
    }

    // function to generate a random string of length n
    private String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
