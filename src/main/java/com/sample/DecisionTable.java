package com.sample;

import java.time.LocalDate;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import data.Lasku;
import data.Tuote;
import data.Tuotetyyppi;

public class DecisionTable {
	

    public static final void main(String[] args) {
        try {
      
        	
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-dtables");

        	// Tuotteiden alustaminen
            Tuote tuote1 = new Tuote();
            tuote1.setNimi("Samsung-televisio");
            tuote1.setTyyppi(Tuotetyyppi.YLEINEN);
            tuote1.setHinta(500.0);
            
            Tuote tuote2 = new Tuote();
            tuote2.setNimi("Valio-maito");
            tuote2.setTyyppi(Tuotetyyppi.ELINTARVIKE);
            tuote2.setHinta(2.5);
            
            Tuote tuote3 = new Tuote();
            tuote3.setNimi("Kalevala");
            tuote3.setTyyppi(Tuotetyyppi.KIRJA);
            tuote3.setHinta(34.95);
            
            Tuote tuote4 = new Tuote();
            tuote4.setNimi("Artek-jakkara");
            tuote4.setTyyppi(Tuotetyyppi.MYYNTI_EU_ALV);
            tuote4.setHinta(945.0);
            	
            // Luodaan uusi lasku ja lisätään tuotteet
            Lasku lasku = new Lasku();
            lasku.lisaaTuote(tuote1);
            lasku.lisaaTuote(tuote2);
            lasku.lisaaTuote(tuote3);
            lasku.lisaaTuote(tuote4);
            
            // Asetetaan tuotteille ostopäivä
            for(Tuote tuote : lasku.getTuotteet()) {
            	tuote.setOstopaiva(LocalDate.parse("2023-12-01"));
            }
            
            
            // Lasketaan summa ensin ilman ALVia
            lasku.laskeSumma();
            
            System.out.printf("Laskun summa (ei sis. AlVia): %.2f \n",lasku.getSumma());
            
            // Tuotteet syötetään sääntömoottoriin, joka laskee uudet hinnat sääntöjen perusteella
            for(Tuote tuote : lasku.getTuotteet()) {
            	 kSession.insert(tuote);
            }
            
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
