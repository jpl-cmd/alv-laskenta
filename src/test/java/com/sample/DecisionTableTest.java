package com.sample;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import data.Lasku;
import data.Tuote;
import data.Tuotetyyppi;

public class DecisionTableTest {

	
	KieServices ks;
    KieContainer kContainer;
	KieSession kSession;
	Lasku lasku;
    
	@BeforeEach
	void alustus() {
		this.ks = KieServices.Factory.get();
		this.kContainer = ks.getKieClasspathContainer();
		this.kSession = kContainer.newKieSession("ksession-dtables");
		
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
        this.lasku = new Lasku();
        lasku.lisaaTuote(tuote1);
        lasku.lisaaTuote(tuote2);
        lasku.lisaaTuote(tuote3);
        lasku.lisaaTuote(tuote4);
        
        lasku.laskeSumma();
        
        System.out.printf("Laskun summa (ei sis. AlVia): %.2f \n",lasku.getSumma());
        
        // Tuotteet syötetään sääntömoottoriin, joka laskee uudet hinnat sääntöjen perusteella
        for(Tuote tuote : lasku.getTuotteet()) {
        	 kSession.insert(tuote);
        }
        
        kSession.getAgenda().getAgendaGroup("alvlaskenta").setFocus();
	}
	
	@Test
	void testDate() {
		 // Asetetaan tuotteille ostopäivä
        for(Tuote tuote : lasku.getTuotteet()) {
        	tuote.setOstopaiva(LocalDate.now());
        }
        kSession.fireAllRules();
        lasku.laskeSumma();
        assertEquals(1606.295, lasku.getSumma());
	}
	
	@Test
	void testDateUusi() {
		 // Asetetaan tuotteille ostopäivä
        for(Tuote tuote : lasku.getTuotteet()) {
        	tuote.setOstopaiva(LocalDate.parse("2023-12-01"));
        }
        kSession.fireAllRules();
        lasku.laskeSumma();
        assertEquals(1731.644, lasku.getSumma());
       
	}
	
	@AfterEach
	void print() {
		System.out.printf("Laskun summa (sis. ALVin): %.2f \n",lasku.getSumma());
	}
	
}
