package data;

import java.time.LocalDate;

//POJO- eli Plain Old Java Object, sisältää Droolsin termeillä "faktat" (Facts).
public class Tuote {
	
	private String nimi;
	private Tuotetyyppi tuotetyyppi;
	private double hinta;
	private LocalDate ostopaiva;
	private Alvluokitus alvluokitus;
	private double alvprosentti;
	
	public String getNimi() {
		return nimi;
	}
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	public Tuotetyyppi getTuotetyyppi() {
		return tuotetyyppi;
	}
	public void setTuotetyyppi(Tuotetyyppi tyyppi) {
		this.tuotetyyppi = tyyppi;
	}
	public double getHinta() {
		return hinta;
	}
	public void setHinta(double hinta) {
		this.hinta = hinta;
	}
	public LocalDate getOstopaiva() {
		return ostopaiva;
	}
	public void setOstopaiva(LocalDate ostopaiva) {
		this.ostopaiva = ostopaiva;
	}
	public Alvluokitus getAlvluokitus() {
		return alvluokitus;
	}
	public void setAlvluokitus(Alvluokitus alvluokitus) {
		this.alvluokitus = alvluokitus;
	}
	public double getAlvprosentti() {
		return alvprosentti;
	}
	public void setAlvprosentti(double alvprosentti) {
		this.alvprosentti = alvprosentti;
	}
	
	
}