package de.cas.camel.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MapO365ToLancom implements Processor {
    public void process(Exchange exchange) throws Exception {
        String payload =  exchange.getIn().getBody(String.class);

        StringBuilder xmlBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        StringBuilder xmlBuilder2 = new StringBuilder();

        boolean foundFirstEvent = false;
        DateTimeFormatter customDateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(payload);

        // splitArray has size 1
        ArrayNode splitArray = (ArrayNode) json.get("arrayToSplit");

        ArrayNode value = (ArrayNode) splitArray.get(0).get("value");

        String a = String.valueOf(splitArray.get(0).get("@odataContext"));
        System.out.println(a);
        //RuhrTestSchild
        if(a.contains("Raum_BO_Ruhr%40c-a-s.de")){
            xmlBuilder.append("<TaskOrder title=\"templated data for label D3182047\">");
            xmlBuilder.append("<TemplateTask labelId=\"D3182047\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"Ruhr\">");
        }
        //Ruhr_Aussen
        else if (a.contains("Raum_BO_Ruhr_Außen%40c-a-s.de")) {
            xmlBuilder.append("<TaskOrder title=\"templated data for label D31829DA\">");
            xmlBuilder.append("<TemplateTask labelId=\"D31829DA\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"Ruhr\">");
        }
        //Marbach
        else if (a.contains("Raum_BO_Marbach%40c-a-s.de")) {
            xmlBuilder.append("<TaskOrder title=\"templated data for label D318280F\">");
            xmlBuilder.append("<TemplateTask labelId=\"D318280F\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"Marbach\">");
        }
        //Elbe1
        else if (a.contains("Raum_HH_Elbe%40c-a-s.de")) {
            xmlBuilder.append("<TaskOrder title=\"templated data for label D308D857\">");
            xmlBuilder.append("<TemplateTask labelId=\"D308D857\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"Elbe\">");
        }
        //Elbe_Aussen
        else if (a.contains("Raum_HH_Elbe_Außen%40c-a-s.de")) {
            xmlBuilder.append("<TaskOrder title=\"templated data for label D3182A7A\">");
            xmlBuilder.append("<TemplateTask labelId=\"D3182A7A\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"Elbe\">");
        }
        //Alster1
        else if (a.contains("Raum_HH_Alster%40c-a-s.de")) {
            xmlBuilder.append("<TaskOrder title=\"templated data for label D308D89C\">");
            xmlBuilder.append("<TemplateTask labelId=\"D308D89C\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"Alster\">");
        }
        //Alster2
        else if (a.contains("Raum_HH_Alster_Außen%40c-a-s.de")) {
            xmlBuilder.append("<TaskOrder title=\"templated data for label D31820A9\">");
            xmlBuilder.append("<TemplateTask labelId=\"D31820A9\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"Alster\">");
        }
        //DoppelBuero
        else if (a.contains("Raum_HH_DoppelBuero%40c-a-s.de")) {
            xmlBuilder.append("<TaskOrder title=\"templated data for label D308D8F4\">");
            xmlBuilder.append("<TemplateTask labelId=\"D308D8F4\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"DoppelBuero\">");
        }
        //ThinkTank
        else if (a.contains("Raum_HH_ThinkTank%40c-a-s.de")) {
            xmlBuilder.append("<TaskOrder title=\"templated data for label D308D85C\">");
            xmlBuilder.append("<TemplateTask labelId=\"D308D85C\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"ThinkTank\">");
        }
        //IT-Testraum
        else if (a.contains("IT-Testraum%40c-a-s.de")) {
            xmlBuilder.append("<TaskOrder title=\"templated data for label D308D897\">");
            xmlBuilder.append("<TemplateTask labelId=\"D308D897\" template=\"itc_template.xsl\">");
            xmlBuilder.append("<room roomName=\"IT-Testraum\">");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS z");

        if (value.size() == 0) {
            xmlBuilder.append("<field key=\"date\" value=\"" + LocalDate.now().format(customDateFormat) + "\"/>");
            xmlBuilder.append("<field key=\"purpose1\" value=\"Keine Reservierungen\"/>");
        } else {
            for (JsonNode event : value) {
                JsonNode start = event.get("start");
                JsonNode end = event.get("end");
                // 2023-08-21T12:30:00.0000000
                String startTime = start.get("dateTime").asText(); // 12:30 Uhr ist eigentlich 14:30 Uhr
                Date startDate = formatter.parse(startTime.concat(" UTC"));
                System.out.println(startDate);

                String endTime = end.get("dateTime").asText(); // 12:30 Uhr ist eigentlich 14:30 Uhr
                Date endDate = formatter.parse(endTime.concat(" UTC"));
                System.out.println(endDate);

                LocalDateTime currentDate = new Date().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
                System.out.println(currentDate);

                LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                //LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (currentDate.isBefore(endDate.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime())) {
                    if (!foundFirstEvent) {
                        if (currentDate.toLocalDate().isBefore(startLocalDate)) {
                            break;
                        }
                        System.out.println("Date: " + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalDate().format(customDateFormat));
                        xmlBuilder.append("<field key=\"date\" value=\"" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalDate().format(customDateFormat) + "\"/>");
                        // date = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(customDateFormat)
                        System.out.println(startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime());
                        xmlBuilder.append("<field key=\"time1\" value=\"" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "\"/>");
                        // time1 = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                        System.out.println(event.get("subject"));
                        xmlBuilder.append("<field key=\"purpose1\" value=\"" + event.get("subject").asText() + "\"/>");
                        // purpose1 = event.get("subject")
                        JsonNode organizer = event.get("organizer");
                        JsonNode organizerMail = organizer.get("emailAddress");
                        System.out.println(organizerMail.get("name"));
                        xmlBuilder.append("<field key=\"chair1\" value=\"" + organizerMail.get("name").asText() + "\"/>");
                        // chair1 = organizerMail.get("name")
                        foundFirstEvent = true;
                    } else {
                        if (currentDate.toLocalDate().isBefore(startLocalDate)) {
                            break;
                        }
                        System.out.println(startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime());
                        xmlBuilder.append("<field key=\"time2\" value=\"" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "\"/>");
                        // time2 = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                        System.out.println(event.get("subject"));
                        xmlBuilder.append("<field key=\"purpose2\" value=\"" + event.get("subject").asText() + "\"/>");
                        // purpose2 = event.get("subject")
                        JsonNode organizer = event.get("organizer");
                        JsonNode organizerMail = organizer.get("emailAddress");
                        System.out.println(organizerMail.get("name"));
                        xmlBuilder.append("<field key=\"chair2\" value=\"" + organizerMail.get("name").asText() + "\"/>");
                        // chair2 = organizerMail.get("name")
                        break;
                    }
                }
            }
        }

        if (!foundFirstEvent) {
            xmlBuilder.append("<field key=\"date\" value=\"" + LocalDate.now().format(customDateFormat) + "\"/>");
            xmlBuilder.append("<field key=\"purpose1\" value=\"Keine Reservierungen\"/>");
        }

        xmlBuilder.append("</room>");
        xmlBuilder.append("</TemplateTask>");
        xmlBuilder.append("</TaskOrder>");
        System.out.println(xmlBuilder);
        exchange.getIn().setBody(xmlBuilder.toString());
    }
//    public void process(Exchange exchange) throws Exception {
//        String payload = exchange.getIn().getBody(String.class);
//
//        StringBuilder xmlBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
//        xmlBuilder.append("<TaskOrder title=\"templated data for label D3182047\">");
//        xmlBuilder.append("<TemplateTask labelId=\"D3182047\" template=\"lcsconference_landscape.xsl\">");
//        xmlBuilder.append("<room roomName=\"Ruhr\">");
//
//
//        boolean foundFirstEvent = false;
//
//        JSONObject json = new JSONObject(payload);
//        JSONArray value = (JSONArray) json.get("value");
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS z");
//
//        for (int i = 0; i < value.length(); i++) {
//            JSONObject event = (JSONObject) value.get(i);
//            JSONObject start = (JSONObject) event.get("start");
//            JSONObject end = (JSONObject) event.get("end");
//            // 2023-08-21T12:30:00.0000000
//            String startTime = (String) start.get("dateTime"); // 12:30 Uhr ist eigentlich 14:30 Uhr
//            Date startDate = formatter.parse(startTime.concat(" UTC"));
//            System.out.println(startDate);
//
//            String endTime = (String) end.get("dateTime"); // 12:30 Uhr ist eigentlich 14:30 Uhr
//            Date endDate = formatter.parse(endTime.concat(" UTC"));
//            System.out.println(endDate);
//
//            Date currentDate = new Date();
//            currentDate.setHours(0);
//            System.out.println(currentDate);
//
//            DateTimeFormatter customDateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//            LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//            if (currentDate.before(endDate)) {
//                if (!foundFirstEvent) {
//                    if (currentLocalDate.isBefore(startLocalDate)) {
//                        break;
//                    }
//                    System.out.println("Date: " + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(customDateFormat));
//                    xmlBuilder.append("<field key=\"date\" value=\"" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(customDateFormat) + "\"/>");
//                    // date = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(customDateFormat)
//                    System.out.println(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
//                    xmlBuilder.append("<field key=\"time1\" value=\"" + startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "\"/>");
//                    // time1 = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
//                    System.out.println(event.get("subject"));
//                    xmlBuilder.append("<field key=\"purpose1\" value=\"" + event.get("subject") + "\"/>");
//                    // purpose1 = event.get("subject")
//                    JSONObject organizer = (JSONObject) event.get("organizer");
//                    JSONObject organizerMail = (JSONObject) organizer.get("emailAddress");
//                    System.out.println(organizerMail.get("name"));
//                    xmlBuilder.append("<field key=\"chair1\" value=\"" + organizerMail.get("name") + "\"/>");
//                    // chair1 = organizerMail.get("name")
//                    foundFirstEvent = true;
//                } else {
//                    if (currentLocalDate.isBefore(startLocalDate)) {
//                        break;
//                    }
//                    System.out.println(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
//                    xmlBuilder.append("<field key=\"time2\" value=\"" + startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "\"/>");
//                    // time2 = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
//                    System.out.println(event.get("subject"));
//                    xmlBuilder.append("<field key=\"purpose2\" value=\"" + event.get("subject") + "\"/>");
//                    // purpose2 = event.get("subject")
//                    JSONObject organizer = (JSONObject) event.get("organizer");
//                    JSONObject organizerMail = (JSONObject) organizer.get("emailAddress");
//                    System.out.println(organizerMail.get("name"));
//                    xmlBuilder.append("<field key=\"chair2\" value=\"" + organizerMail.get("name") + "\"/>");
//                    // chair2 = organizerMail.get("name")
//                    break;
//                }
//            }
//
//            xmlBuilder.append("</room>");
//            xmlBuilder.append("</TemplateTask>");
//            xmlBuilder.append("</TaskOrder>");
//            exchange.getIn().setBody(xmlBuilder.toString());
//        }
//    }
}