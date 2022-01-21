package CLIENTE;

public class ASSEGNAZIONE_ID {

	static int count = 0;
	
	public static int assegna_id(){
		
		return count++;
		
	}
	
	 static void resettacount() {
		 count=0;
	 }
	
	
}