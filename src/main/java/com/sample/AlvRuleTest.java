package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import data.Tuote;
import data.Tuotetyypit;

public class AlvRuleTest {

    public static final void main(String[] args) {
        try {
        	
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go !
            Tuote tuote = new Tuote();
            tuote.setNimi("Samsung-televisio");
            tuote.setTyyppi(Tuotetyypit.YLEINEN);
            tuote.setHinta(500);
            kSession.insert(tuote);
            kSession.fireAllRules();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
