package com.sample;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import org.drools.decisiontable.DecisionTableProviderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import data.Alvluokitus;
import data.Lasku;
import data.Tuote;
import data.Tuotetyyppi;
import data.Varasto;

public class ProcessTest {

	
	KieServices ks;
    KieContainer kContainer;
	KieSession kSession;
	Lasku lasku;
    
	@BeforeEach
	void alustus() {
		this.ks = KieServices.Factory.get();
		this.kContainer = ks.getKieClasspathContainer();
		this.kSession = kContainer.newKieSession("ksession-process");
		
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
        this.lasku = new Lasku();
        lasku.lisaaTuote(tuote1);
        lasku.lisaaTuote(tuote2);
        lasku.lisaaTuote(tuote3);
        lasku.lisaaTuote(tuote4);
        
        lasku.laskeSumma();
        
        System.out.printf("Laskun summa (ei sis. AlVia): %.2f \n",lasku.getSumma());
        
        //printDRL("Alvprosentti.xls");
        
	}
	
	@Test
	void testProcess2023() {
		 // Asetetaan tuotteille ostopäivä
        for(Tuote tuote : lasku.getTuotteet()) {
        	tuote.setOstopaiva(LocalDate.now());
        }
        
        // Tuotteet syötetään sääntömoottoriin, joka laskee uudet hinnat sääntöjen perusteella
        for(Tuote tuote : lasku.getTuotteet()) {
        	 kSession.insert(tuote);
        }
        kSession.startProcess("alvprosessi");
        kSession.fireAllRules();
        lasku.laskeAlvSumma();
        assertEquals(1731.644, lasku.getAlvsumma());
	}
	
	@Test
	void testProcess2011() {
		 // Asetetaan tuotteille ostopäivä
        for(Tuote tuote : lasku.getTuotteet()) {
        	tuote.setOstopaiva(LocalDate.parse("2011-01-01"));
        }
        
        // Tuotteet syötetään sääntömoottoriin, joka laskee uudet hinnat sääntöjen perusteella
        for(Tuote tuote : lasku.getTuotteet()) {
        	 kSession.insert(tuote);
        }
        kSession.startProcess("alvprosessi");
        kSession.fireAllRules();
        lasku.laskeAlvSumma();
        assertEquals(1606.295, lasku.getAlvsumma());
	}
	
	@AfterEach
	void print() {
//		for(Tuote tuote : lasku.getTuotteet()) {
//        	System.out.println(tuote.getAlvluokitus());
//        }
//		for(Tuote tuote : lasku.getTuotteet()) {
//        	System.out.println(tuote.getAlvprosentti());
//        }
		System.out.printf("Laskun summa (sis. ALVin): %.2f \n",lasku.getAlvsumma());
		System.out.println("Varastokapasiteetti:"
				+ "\n\tTelevisiot: " + Varasto.TELEVISIO
				+ "\n\tMaito: " + Varasto.MAITO
				+ "\n\tKirjat: " + Varasto.KIRJA
				+ "\n\tJakkarat: " + Varasto.JAKKARA);
	}
	
	void printDRL(String polku) {
		Resource dt 
        = ResourceFactory
          .newClassPathResource(polku,
            getClass());

        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();
       
        String drl = decisionTableProvider.loadFromResource(dt, null);
        System.out.println("\n" + drl + "\n");
	}
	
}
