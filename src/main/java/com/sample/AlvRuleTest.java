package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import data.Tuote;
import data.Tuotetyypit;

public class AlvRuleTest {

    public static final void main(String[] args) {
        try {
        	
            // Sääntömoottorin alustus
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            // Tuotteiden alustaminen
            Tuote tuote1 = new Tuote();
            tuote1.setNimi("Samsung-televisio");
            tuote1.setTyyppi(Tuotetyypit.YLEINEN);
            tuote1.setHinta(500.0);
            
            Tuote tuote2 = new Tuote();
            tuote2.setNimi("Valio-maito");
            tuote2.setTyyppi(Tuotetyypit.ELINTARVIKE);
            tuote2.setHinta(2.5);
            
            Tuote tuote3 = new Tuote();
            tuote3.setNimi("Kalevala");
            tuote3.setTyyppi(Tuotetyypit.KIRJA);
            tuote3.setHinta(34.95);
            
            Tuote tuote4 = new Tuote();
            tuote4.setNimi("V8-moottori");
            tuote4.setTyyppi(Tuotetyypit.MYYNTI_EU_ALV);
            tuote4.setHinta(12959.0);
            
            // Tuotteet annetaan kie-istunnolle
            kSession.insert(tuote1);
            kSession.insert(tuote2);
            kSession.insert(tuote3);
            kSession.insert(tuote4);
            
            kSession.fireAllRules();
            
            System.out.println("Tuotteen '" + tuote1.getNimi() + "' hinta (sis. alv) on " + tuote1.getHinta());
            System.out.println("Tuotteen '" + tuote2.getNimi() + "' hinta (sis. alv) on " + tuote2.getHinta());
            System.out.println("Tuotteen '" + tuote3.getNimi() + "' hinta (sis. alv) on " + tuote3.getHinta());
            System.out.println("Tuotteen '" + tuote4.getNimi() + "' hinta (sis. alv) on " + tuote4.getHinta());
            
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
