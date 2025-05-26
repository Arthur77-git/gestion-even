package com.evenements.serialization;

import com.evenements.model.Evenement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

public class XmlSerializer {

    @XmlRootElement(name = "evenements")
    public static class EvenementsWrapper {
        @XmlElement(name = "evenement")
        private List<Evenement> evenements;

        public EvenementsWrapper() {}

        public EvenementsWrapper(List<Evenement> evenements) {
            this.evenements = evenements;
        }

        public List<Evenement> getEvenements() { return evenements; }
        public void setEvenements(List<Evenement> evenements) { this.evenements = evenements; }
    }

    public void sauvegarderEvenements(List<Evenement> evenements, String filepath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EvenementsWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        EvenementsWrapper wrapper = new EvenementsWrapper(evenements);
        marshaller.marshal(wrapper, new File(filepath));
    }

    public List<Evenement> chargerEvenements(String filepath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EvenementsWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        EvenementsWrapper wrapper = (EvenementsWrapper) unmarshaller.unmarshal(new File(filepath));
        return wrapper.getEvenements();
    }
}