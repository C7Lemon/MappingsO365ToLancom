package de.cas.camel.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MapO365ToLancomDOMParser implements Processor {

    public static String documentToString(Document doc) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Verwenden Sie einen StringWriter als Ziel für die Transformation
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));

        // Erhalten Sie den resultierenden XML-String
        return writer.toString();
    }

    public void process(Exchange exchange) throws Exception {
        String payload = exchange.getIn().getBody(String.class);

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        doc.setXmlStandalone(true);
        Element root = doc.createElement("Termine");
        doc.appendChild(root);
        Element TaskOrder = doc.createElement("TaskOrder");
        Element TaskOrder2 = doc.createElement("TaskOrder");
        root.appendChild(TaskOrder);
        root.appendChild(TaskOrder2);

        Element TemplateTask = doc.createElement("TemplateTask");
        TaskOrder.appendChild(TemplateTask);
        Element TemplateTask2 = doc.createElement("TemplateTask");
        TaskOrder2.appendChild(TemplateTask2);
        Element room = doc.createElement("room");
        TemplateTask.appendChild(room);
        Element room2 = doc.createElement("room");
        TemplateTask2.appendChild(room2);


        //StringBuilder xmlBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        //StringBuilder xmlBuilder2 = new StringBuilder();
        boolean foundFirstEvent = false;
        DateTimeFormatter customDateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(payload);

        ArrayNode value = (ArrayNode) json.get(0).get("value");

        String a = String.valueOf(json.get(0).get("@odataContext"));
        //System.out.println(a);
        //RuhrTestSchild
        if (a.contains("Raum_BO_Ruhr%40c-a-s.de")) {
            //xmlBuilder.append("<TaskOrder title=\"templated data for label D3182047\">");
            TaskOrder.setAttribute("title", "templated data for label D3182047");
            //xmlBuilder.append("<TemplateTask labelId=\"D3182047\" template=\"itc_template.xsl\">");
            //TemplateTask.setAttribute("labelId","D3182047");
            TemplateTask.setAttribute("labelId", "D3182047");
            TemplateTask.setAttribute("template", "itc_template.xsl");
            //xmlBuilder.append("<room roomName=\"Ruhr\">");
            room.setAttribute("roomName", "Ruhr");

            //xmlBuilder2.append("<TaskOrder title=\"templated data for label D31829DA\">");
            TaskOrder2.setAttribute("title", "templated data for label D31829DA");
            //xmlBuilder2.append("<TemplateTask labelId=\"D31829DA\" template=\"itc_template.xsl\">");
            TemplateTask2.setAttribute("labelId", "D31829DA");
            TemplateTask2.setAttribute("template", "itc_template.xsl");
            room2.setAttribute("roomName", "Ruhr");
        }
        //Marbach
        else if (a.contains("Raum_BO_Marbach%40c-a-s.de")) {
            //xmlBuilder.append("<TaskOrder title=\"templated data for label D318280F\">");
            TaskOrder.setAttribute("title", "templated data for label D318280F");
            //xmlBuilder.append("<TemplateTask labelId=\"D318280F\" template=\"itc_template.xsl\">");
            TemplateTask.setAttribute("labelId", "D318280F");
            TemplateTask.setAttribute("template", "itc_template.xsl");
            //xmlBuilder.append("<room roomName=\"Marbach\">");
            room.setAttribute("roomName", "Marbach");
        }
        //Elbe1
        else if (a.contains("Raum_HH_Elbe%40c-a-s.de")) {
            //xmlBuilder.append("<TaskOrder title=\"templated data for label D308D857\">");
            TaskOrder.setAttribute("title", "templated data for label D308D857");
            //xmlBuilder.append("<TemplateTask labelId=\"D308D857\" template=\"itc_template.xsl\">");
            TemplateTask.setAttribute("labelId", "D308D857");
            TemplateTask.setAttribute("template", "itc_template.xsl");
            //xmlBuilder.append("<room roomName=\"Elbe\">");
            room.setAttribute("roomName", "Elbe");

            //xmlBuilder.append("<TaskOrder title=\"templated data for label D3182A7A\">");
            TaskOrder2.setAttribute("title", "templated data for label D3182A7A");
            //xmlBuilder.append("<TemplateTask labelId=\"D3182A7A\" template=\"itc_template.xsl\">");
            TemplateTask2.setAttribute("labelId", "D3182A7A");
            TemplateTask2.setAttribute("template", "itc_template.xsl");
            room2.setAttribute("roomName", "Elbe");
        }
        //Alster1
        else if (a.contains("Raum_HH_Alster%40c-a-s.de")) {
            //xmlBuilder.append("<TaskOrder title=\"templated data for label D308D89C\">");
            TaskOrder.setAttribute("title", "templated data for label D308D89C");
            //xmlBuilder.append("<TemplateTask labelId=\"D308D89C\" template=\"itc_template.xsl\">");
            TemplateTask.setAttribute("labelId", "D308D89C");
            TemplateTask.setAttribute("template", "itc_template.xsl");
            //xmlBuilder.append("<room roomName=\"Alster\">");
            room.setAttribute("roomName", "Alster");

            //xmlBuilder.append("<TaskOrder title=\"templated data for label D31820A9\">");
            TaskOrder2.setAttribute("title", "templated data for label D31820A9");
            //xmlBuilder.append("<TemplateTask labelId=\"D31820A9\" template=\"itc_template.xsl\">");
            TemplateTask2.setAttribute("labelId", "D31820A9");
            TemplateTask2.setAttribute("template", "itc_template.xsl");
            room2.setAttribute("roomName", "Alster");
        }
        //DoppelBuero
        else if (a.contains("Raum_HH_DoppelBuero%40c-a-s.de")) {
            //xmlBuilder.append("<TaskOrder title=\"templated data for label D308D8F4\">");
            TaskOrder.setAttribute("title", "templated data for label D308D8F4");
            //xmlBuilder.append("<TemplateTask labelId=\"D308D8F4\" template=\"itc_template.xsl\">");
            TemplateTask.setAttribute("labelId", "D308D8F4");
            TemplateTask.setAttribute("template", "itc_template.xsl");
            //xmlBuilder.append("<room roomName=\"DoppelBuero\">");
            room.setAttribute("roomName", "Alster");
        }
        //ThinkTank
        else if (a.contains("Raum_HH_ThinkTank%40c-a-s.de")) {
            //xmlBuilder.append("<TaskOrder title=\"templated data for label D308D85C\">");
            TaskOrder.setAttribute("title", "templated data for label D308D85C");
            //xmlBuilder.append("<TemplateTask labelId=\"D308D85C\" template=\"itc_template.xsl\">");
            TemplateTask.setAttribute("labelId", "D308D85C");
            TemplateTask.setAttribute("template", "itc_template.xsl");
            //xmlBuilder.append("<room roomName=\"ThinkTank\">");
            room.setAttribute("roomName", "ThinkTank");
        }
        //IT-Testraum
        else if (a.contains("IT-Testraum%40c-a-s.de")) {
            //xmlBuilder.append("<TaskOrder title=\"templated data for label D308D897\">");
            TaskOrder.setAttribute("title", "templated data for label D308D897");
            //xmlBuilder.append("<TemplateTask labelId=\"D308D897\" template=\"itc_template.xsl\">");
            TemplateTask.setAttribute("labelId", "D308D897");
            TemplateTask.setAttribute("template", "itc_template.xsl");
            //xmlBuilder.append("<room roomName=\"IT-Testraum\">");
            room.setAttribute("roomName", "IT-Testraum");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS z");

        if (value.size() == 0) {
            Element field1 = doc.createElement("field");
            room.appendChild(field1);
            field1.setAttribute("key", "date");
            field1.setAttribute("value", "" + LocalDate.now().format(customDateFormat));

            Element field2 = doc.createElement("field");
            room.appendChild(field2);
            field2.setAttribute("key", "purpose1");
            field2.setAttribute("value", "Keine Reservierungen");

            //xmlBuilder.append("<field key=\"date\" value=\"" + LocalDate.now().format(customDateFormat) + "\"/>");
            //xmlBuilder.append("<field key=\"purpose1\" value=\"Keine Reservierungen\"/>");
            //xmlBuilder2.append("<field key=\"date\" value=\"" + LocalDate.now().format(customDateFormat) + "\"/>");
            //xmlBuilder2.append("<field key=\"purpose1\" value=\"Keine Reservierungen\"/>");
        } else {
            for (JsonNode event : value) {
                JsonNode start = event.get("start");
                JsonNode end = event.get("end");
                // 2023-08-21T12:30:00.0000000
                String startTime = start.get("dateTime").asText(); // 12:30 Uhr ist eigentlich 14:30 Uhr
                Date startDate = formatter.parse(startTime.concat(" UTC"));
                //System.out.println(startDate);

                String endTime = end.get("dateTime").asText(); // 12:30 Uhr ist eigentlich 14:30 Uhr
                Date endDate = formatter.parse(endTime.concat(" UTC"));
                //System.out.println(endDate);

                LocalDateTime currentDate = new Date().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
                //System.out.println(currentDate);

                LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                //LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (currentDate.isBefore(endDate.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime())) {
                    if (!foundFirstEvent) {
                        if (currentDate.toLocalDate().isBefore(startLocalDate)) {
                            break;
                        }
                        //System.out.println("Date: " + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalDate().format(customDateFormat));

                        Element field1a = doc.createElement("field");
                        room.appendChild(field1a);
                        field1a.setAttribute("key", "date");
                        field1a.setAttribute("value", "" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalDate().format(customDateFormat));
                        Element field1b = doc.createElement("field");
                        room2.appendChild(field1b);
                        field1b.setAttribute("key", "date");
                        field1b.setAttribute("value", "" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalDate().format(customDateFormat));


                        Element field2a = doc.createElement("field");
                        room.appendChild(field2a);
                        field2a.setAttribute("key", "time1");
                        field2a.setAttribute("value", "" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime());
                        Element field2b = doc.createElement("field");
                        room2.appendChild(field2b);
                        field2b.setAttribute("key", "time1");
                        field2b.setAttribute("value", "" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime());


                        Element field3a = doc.createElement("field");
                        room.appendChild(field3a);
                        field3a.setAttribute("key", "purpose1");
                        field3a.setAttribute("value", "" + event.get("subject").asText());
                        Element field3b = doc.createElement("field");
                        room2.appendChild(field3b);
                        field3b.setAttribute("key", "purpose1");
                        field3b.setAttribute("value", "" + event.get("subject").asText());


                        JsonNode organizer = event.get("organizer");
                        JsonNode organizerMail = organizer.get("emailAddress");

                        Element field4a = doc.createElement("field");
                        room.appendChild(field4a);
                        field4a.setAttribute("key", "chair1");
                        field4a.setAttribute("value", "" + organizerMail.get("name").asText());
                        Element field4b = doc.createElement("field");
                        room2.appendChild(field4b);
                        field4b.setAttribute("key", "chair1");
                        field4b.setAttribute("value", "" + organizerMail.get("name").asText());


                        //xmlBuilder.append("<field key=\"date\" value=\"" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalDate().format(customDateFormat) + "\"/>");
                        //xmlBuilder2.append("<field key=\"date\" value=\"" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalDate().format(customDateFormat) + "\"/>");
                        //date = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(customDateFormat)
                        //System.out.println(startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime());
                        //xmlBuilder.append("<field key=\"time1\" value=\"" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "\"/>");
                        //xmlBuilder2.append("<field key=\"time1\" value=\"" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "\"/>");
                        //time1 = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                        //System.out.println(event.get("subject"));
                        //xmlBuilder.append("<field key=\"purpose1\" value=\"" + event.get("subject").asText() + "\"/>");
                        //xmlBuilder2.append("<field key=\"purpose1\" value=\"" + event.get("subject").asText() + "\"/>");
                        //purpose1 = event.get("subject")

                        //System.out.println(organizerMail.get("name"));
                        //xmlBuilder.append("<field key=\"chair1\" value=\"" + organizerMail.get("name").asText() + "\"/>");
                        //xmlBuilder2.append("<field key=\"chair1\" value=\"" + organizerMail.get("name").asText() + "\"/>");
                        // chair1 = organizerMail.get("name")
                        foundFirstEvent = true;
                    } else {
                        if (currentDate.toLocalDate().isBefore(startLocalDate)) {
                            break;
                        }

                        Element field5a = doc.createElement("field");
                        room.appendChild(field5a);
                        field5a.setAttribute("key", "time2");
                        field5a.setAttribute("value", "" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime());
                        Element field5b = doc.createElement("field");
                        room2.appendChild(field5b);
                        field5b.setAttribute("key", "time2");
                        field5b.setAttribute("value", "" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime());


                        Element field6a = doc.createElement("field");
                        room.appendChild(field6a);
                        field6a.setAttribute("key", "purpose2");
                        field6a.setAttribute("value", "" + event.get("subject").asText());
                        Element field6b = doc.createElement("field");
                        room2.appendChild(field6b);
                        field6b.setAttribute("key", "purpose2");
                        field6b.setAttribute("value", "" + event.get("subject").asText());


                        JsonNode organizer = event.get("organizer");
                        JsonNode organizerMail = organizer.get("emailAddress");

                        Element field7a = doc.createElement("field");
                        room.appendChild(field7a);
                        field7a.setAttribute("key", "chair2");
                        field7a.setAttribute("value", "" + organizerMail.get("name").asText());
                        Element field7b = doc.createElement("field");
                        room2.appendChild(field7b);
                        field7b.setAttribute("key", "chair2");
                        field7b.setAttribute("value", "" + organizerMail.get("name").asText());


                        //System.out.println(startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime());
                        //xmlBuilder.append("<field key=\"time2\" value=\"" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "\"/>");
                        //xmlBuilder2.append("<field key=\"time2\" value=\"" + startDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.of("Europe/Berlin")).toLocalTime() + "\"/>");
                        //time2 = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() + "-" + endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                        //System.out.println(event.get("subject"));
                        //xmlBuilder.append("<field key=\"purpose2\" value=\"" + event.get("subject").asText() + "\"/>");
                        //xmlBuilder2.append("<field key=\"purpose2\" value=\"" + event.get("subject").asText() + "\"/>");
                        //purpose2 = event.get("subject")
                        //JsonNode organizer = event.get("organizer");
                        //JsonNode organizerMail = organizer.get("emailAddress");
                        //System.out.println(organizerMail.get("name"));
                        //xmlBuilder.append("<field key=\"chair2\" value=\"" + organizerMail.get("name").asText() + "\"/>");
                        //xmlBuilder2.append("<field key=\"chair2\" value=\"" + organizerMail.get("name").asText() + "\"/>");
                        //chair2 = organizerMail.get("name")
                        break;
                    }
                }
            }
        }

        if (!foundFirstEvent) {
            Element field1a = doc.createElement("field");
            room.appendChild(field1a);
            field1a.setAttribute("key", "date");
            field1a.setAttribute("value", "" + LocalDate.now().format(customDateFormat));
            Element field1b = doc.createElement("field");
            room2.appendChild(field1b);
            field1b.setAttribute("key", "date");
            field1b.setAttribute("value", "" + LocalDate.now().format(customDateFormat));

            Element field2a = doc.createElement("field");
            room.appendChild(field2a);
            field2a.setAttribute("key", "purpose1");
            field2a.setAttribute("value", "Keine Reservierungen");
            Element field2b = doc.createElement("field");
            room2.appendChild(field2b);
            field2b.setAttribute("key", "purpose1");
            field2b.setAttribute("value", "Keine Reservierungen");

            //xmlBuilder.append("<field key=\"date\" value=\"" + LocalDate.now().format(customDateFormat) + "\"/>");
            //xmlBuilder.append("<field key=\"purpose1\" value=\"Keine Reservierungen\"/>");
            //xmlBuilder2.append("<field key=\"date\" value=\"" + LocalDate.now().format(customDateFormat) + "\"/>");
            //xmlBuilder2.append("<field key=\"purpose1\" value=\"Keine Reservierungen\"/>");
        }

        //xmlBuilder.append("</room>");
        //xmlBuilder.append("</TemplateTask>");
        //xmlBuilder.append("</TaskOrder>");
        //xmlBuilder2.append("</room>");
        //xmlBuilder2.append("</TemplateTask>");
        //xmlBuilder2.append("</TaskOrder>");
        //System.out.println(xmlBuilder);
        //System.out.println(xmlBuilder2);
        //String beideSchilder = xmlBuilder.toString() + xmlBuilder2.toString();
        //System.out.println(beideSchilder);

        String x = documentToString(doc);
        exchange.getIn().setBody(x);
    }

}
