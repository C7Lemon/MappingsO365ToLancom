package de.cas.camel.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class DuplicateForTwo implements Processor {
    public void process(Exchange exchange) throws Exception {
        String payload = exchange.getIn().getBody(String.class);
        String newPayload = null;


        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(payload);

        // splitArray has size 1
        ArrayNode splitArray = (ArrayNode) json.get("arrayToSplit");


        String a = String.valueOf(splitArray.get(0).get("@odataContext"));
        System.out.println(a);
        //RuhrTestSchild
        if (a.contains("Raum_BO_Ruhr%40c-a-s.de")) {
            String suchString = "Raum_BO_Ruhr%40c-a-s.de";
            String ersetzenString = "Raum_BO_Ruhr_Außen%40c-a-s.de";
            String newAddress = payload.replace(suchString, ersetzenString);
            System.out.println(newAddress);
            newPayload = payload + "\n" + newAddress;
        }
        //Marbach
        else if (a.contains("Raum_BO_Marbach%40c-a-s.de")) {
            newPayload = payload;
        }
        //Elbe1
        else if (a.contains("Raum_HH_Elbe%40c-a-s.de")) {
            String suchString = "Raum_HH_Elbe%40c-a-s.de";
            String ersetzenString = "Raum_HH_Elbe_Außen%40c-a-s.de";
            String newAddress = payload.replace(suchString, ersetzenString);
            System.out.println(newAddress);
            newPayload = payload + "\n" + newAddress;
        }
        //Alster1
        else if (a.contains("Raum_HH_Alster%40c-a-s.de")) {
            String suchString = "Raum_HH_Alster%40c-a-s.de";
            String ersetzenString = "Raum_HH_Alster_Außen%40c-a-s.de";
            String newAddress = payload.replace(suchString, ersetzenString);
            System.out.println(newAddress);
            newPayload = payload + "\n" + newAddress;
        }
        //DoppelBuero
        else if (a.contains("Raum_HH_DoppelBuero%40c-a-s.de")) {
            newPayload = payload;
        }
        //ThinkTank
        else if (a.contains("Raum_HH_ThinkTank%40c-a-s.de")) {
            newPayload = payload;
        }
        //IT-Testraum
        else if (a.contains("IT-Testraum%40c-a-s.de")) {
            newPayload = payload;
        }

        System.out.println(newPayload);
        exchange.getIn().setBody(newPayload);
    }
}