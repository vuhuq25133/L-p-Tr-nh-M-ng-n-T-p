package client;

import org.apache.axis.Message;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.namespace.QName;

public class WSClient {

   public static void main(String[] args) throws Exception {

       String endpoint  = "http://203.162.10.109:8080/JNPWS/CharacterService";
       String namespace = "http://medianews.vn/";
       String studentCode = "B22DCCN372";
       String qCode       = "J71YLMKT";

       // =======================
       // 1. REQUEST STRING (vẫn dùng Axis bình thường)
       // =======================
       Service service = new Service();
       Call call = (Call) service.createCall();
       call.setTargetEndpointAddress(new java.net.URL(endpoint));
       call.setOperationName(new QName(namespace, "requestString"));
       call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
       call.addParameter("studentCode", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
       call.addParameter("qCode",        org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);

       String input = (String) call.invoke(new Object[]{studentCode, qCode});
       System.out.println("Input: " + input);

       // =======================
       // 2. FORMAT CHUỖI
       // =======================
       String[] words = input.trim().toLowerCase().split("\\s+|_+");

       StringBuilder pascal = new StringBuilder();
       for (String w : words)
           pascal.append(w.substring(0,1).toUpperCase()).append(w.substring(1));

       StringBuilder camel = new StringBuilder(words[0]);
       for (int i = 1; i < words.length; i++)
           camel.append(words[i].substring(0,1).toUpperCase()).append(words[i].substring(1));

       String snake = String.join("_", words);

       // =======================
       // 3. GỬI RAW SOAP (CHẮC CHẮN SERVER CHẤP NHẬN)
       // =======================
       String body =
               "<ns2:submitCharacterStringArray xmlns:ns2=\"" + namespace + "\">" +
                       "<studentCode>" + studentCode + "</studentCode>" +
                       "<qCode>" + qCode + "</qCode>" +
                       "<data>" + pascal + "</data>" +
                       "<data>" + camel  + "</data>" +
                       "<data>" + snake  + "</data>" +
                       "</ns2:submitCharacterStringArray>";

       String envelope =
               "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                       "<soapenv:Body>" + body + "</soapenv:Body>" +
                       "</soapenv:Envelope>";

       Message msg = new Message(envelope);
       Call submit = (Call) service.createCall();
       submit.setTargetEndpointAddress(new java.net.URL(endpoint));

       // raw literal – no mapping
       submit.setOperationStyle(org.apache.axis.constants.Style.DOCUMENT);
       submit.setOperationUse(org.apache.axis.constants.Use.LITERAL);
       submit.setSOAPActionURI("");

       Object resp = submit.invoke(msg);

       System.out.println("Server Response: " + resp);
   }
}

