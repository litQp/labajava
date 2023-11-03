package com.example.labv4;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "agreements")
public class AgreementListWrapper {

    private List<Agreement> agreements;

    @XmlElement(name = "agreement")
    public List<Agreement> getAgreements() {
        return agreements;
    }

    public void setAgreements(List<Agreement> agreements) {
        this.agreements = agreements;
    }
}
