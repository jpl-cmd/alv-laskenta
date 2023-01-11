package com.sample;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import data.Lasku;
import data.Tuote;
import data.Tuotetyyppi;

public class Rules {

    public static final void main(String[] args) {
        try {
        	
            // Sääntömoottorin alustus
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            // Tuotteiden alustaminen
            Tuote tuote1 = new Tuote();
            tuote1.setNimi("Samsung-televisio");
            tuote1.setTuotetyyppi(Tuotetyyppi.YLEINEN);
            tuote1.setHinta(500.0);
            
            Tuote tuote2 = new Tuote();
            tuote2.setNimi("Valio-maito");
            tuote2.setTuotetyyppi(Tuotetyyppi.ELINTARVIKE);
            tuote2.setHinta(2.5);
            
            Tuote tuote3 = new Tuote();
            tuote3.setNimi("Kalevala");
            tuote3.setTuotetyyppi(Tuotetyyppi.KIRJA);
            tuote3.setHinta(34.95);
            
            Tuote tuote4 = new Tuote();
            tuote4.setNimi("Artek-jakkara");
            tuote4.setTuotetyyppi(Tuotetyyppi.MYYNTI_EU_ALV);
            tuote4.setHinta(945.0);
            
            // Luodaan uusi lasku ja lisätään tuotteet
            Lasku lasku = new Lasku();
            lasku.lisaaTuote(tuote1);
            lasku.lisaaTuote(tuote2);
            lasku.lisaaTuote(tuote3);
            lasku.lisaaTuote(tuote4);
            
            // Lasketaan summa ensin ilman ALVia
            lasku.laskeSumma();
            
            System.out.printf("Laskun summa (ei sis. AlVia): %.2f \n",lasku.getSumma());
            
            // Tuotteet syötetään sääntömoottoriin, joka laskee uudet hinnat sääntöjen perusteella
            kSession.insert(tuote1);
            kSession.insert(tuote2);
            kSession.insert(tuote3);
            kSession.insert(tuote4);
            
            // Käynnistetään sääntömoottori
            kSession.getAgenda().getAgendaGroup("alvlaskenta").setFocus();
            kSession.fireAllRules();
            
            // Lasketaan laskun summa uudelleen ALVien kanssa
            lasku.laskeSumma();
            
            System.out.printf("Laskun summa (sis. ALVin): %.2f \n",lasku.getSumma());
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
