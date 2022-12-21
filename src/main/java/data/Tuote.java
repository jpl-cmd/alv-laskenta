package data;

import java.time.LocalDate;

//POJO- eli Plain Old Java Object, sisältää Droolsin termeillä "faktat" (Facts).
public class Tuote {
	
	private String nimi;
	private int tyyppi;
	private double hinta;
	private LocalDate ostopaiva;
	
	public String getNimi() {
		return nimi;
	}
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	public int getTyyppi() {
		return tyyppi;
	}
	public void setTyyppi(int tyyppi) {
		this.tyyppi = tyyppi;
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
	

}