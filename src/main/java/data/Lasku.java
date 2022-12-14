package data;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lasku {

	private ArrayList<Tuote> tuotteet;
	private double summa;
	private LocalDate ostopaiva;
	
	public Lasku() {
		this.tuotteet = new ArrayList<Tuote>();
		this.summa = 0.0;
	}
	
	public void lisaaTuote(Tuote tuote) {
		this.tuotteet.add(tuote);
	}

	public ArrayList<Tuote> getTuotteet() {
		return tuotteet;
	}

	public void setTuotteet(ArrayList<Tuote> tuotteet) {
		this.tuotteet = tuotteet;
	}

	public double getSumma() {
		return summa;
	}

	public void setSumma(double summa) {
		this.summa = summa;
	}
	
	public void laskeSumma() {
		double summa = 0;
		for(Tuote tuote : tuotteet) {
			summa += tuote.getHinta();
		}
		
		this.summa = summa;
	}

	public LocalDate getOstopaiva() {
		return ostopaiva;
	}

	public void setOstopaiva(LocalDate ostopaiva) {
		this.ostopaiva = ostopaiva;
	}
	
	
	
}
