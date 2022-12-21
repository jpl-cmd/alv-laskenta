package data;

import java.time.LocalDate;

public class AlvTiedot {
	
	public static final int LUOKKA1 = 0;
	public static final int LUOKKA2 = 1;
	public static final int LUOKKA3 = 2;
	public static final int LUOKKA4 = 3;

	public static final double LUOKKA1_KERROIN = 0.24;
	public static final double LUOKKA2_KERROIN = 0.14;
	public static final double LUOKKA3_KERROIN = 0.10;
	public static final double LUOKKA4_KERROIN = 0.00;
	
	public static final LocalDate ALV_PVM_ALKU =  LocalDate.parse("2005-01-01");
	public static final LocalDate ALV_PVM_LOPPU =  LocalDate.parse("2022-12-31");
	
	public static final LocalDate ALV_PVM_ALKU_UUSI =  LocalDate.parse("2023-01-01");
	public static final LocalDate ALV_PVM_LOPPU_UUSI =  LocalDate.parse("2030-12-31");
	
	
	

}
