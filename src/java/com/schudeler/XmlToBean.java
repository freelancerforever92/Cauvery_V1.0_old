

package com.schudeler;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XmlToBean {
     public String convertToXml(Object source, Class... type) {
        String result;
        StringWriter sw = new StringWriter();
        try {
            JAXBContext carContext = JAXBContext.newInstance(type);
            Marshaller carMarshaller = carContext.createMarshaller();
            carMarshaller.marshal(source, sw);
            result = sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public LineItems xmlToBean(String Xml) {
        LineItems items = null;
        try {
            JAXBContext context = JAXBContext
                    .newInstance(LineItems.class);
            Unmarshaller unMarshaller = context.createUnmarshaller();
            items = (LineItems) unMarshaller.unmarshal(new StringReader(Xml));
        } catch (Exception e) {
            System.out.println("Exception in creating XML : "+e.getMessage());
        }
        return items;
    }
}
