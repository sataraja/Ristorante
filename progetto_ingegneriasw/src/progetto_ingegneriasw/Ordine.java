package progetto_ingegneriasw;

import java.util.StringTokenizer;
import java.util.Vector;

import prog.io.FileInputManager;
// stile ordine: prezzo ordine, lista numeri pietanze ordinate
public class Ordine {
	
   public Vector<String> ordine;
	public Vector<String>Prezzi_da_menu= new Vector<String>(); /*
	 Questo vector contiene i prezzi delle pietanze. (indice_vector +1) equivale al numero pietanza nel menù
	*/
	public Ordine() {
		FileInputManager infile= new FileInputManager("Menu.txt");
		String riga;
		while((riga=infile.readLine())!=null) {
			//inserisco in Prezzi_da_menu l'ultimo valore contenuto nella riga (il prezzo)
			StringTokenizer st = new StringTokenizer(riga, ",");
			String ultimo_valore=null;
			while(st.hasMoreTokens()) {
				ultimo_valore= st.nextToken();
			   }
			  Prezzi_da_menu.add(ultimo_valore);
			}
		ordine= new Vector<String>();	
		ordine.add("0");   //alla prima posizione riservo il posto per il prezzo totale ordine
	}
	
	
	public void add(String p) {
		 ordine.add(p);
		 //aggiorna prezzo totale dell'ordine
		int prezzopietanza=Integer.parseInt(Prezzi_da_menu.get((Integer.parseInt(p))-1));
		int prezzotot=Integer.parseInt(ordine.get(0));
		prezzotot=prezzotot+prezzopietanza;
		ordine.set(0, ""+prezzotot);
	}
	
	
	
	
	public void print() {
		System.out.println("Gli ordini nel carrello sono i seguenti:\n");

		for(int i= 1 ; i< ordine.size(); i++) {
			System.out.println("-"+ordine.get(i));
		}
		System.out.println("Il prezzo attuale dell'ordine è: " + ordine.get(0)+"\n");
	}
	
	
	
	
	public void cancella(String p) {
		String pminuscolo= p.toLowerCase();
		for(int i=0; i<ordine.size()-1; i++) {
			String tminuscolo= ordine.get(i).toLowerCase();
			if(tminuscolo.compareTo(pminuscolo) ==0) {
				ordine.remove(i);
				break;
			}
		}
		
	}
	
	public void cancellatutto() {
		for(int i=0; i<ordine.size()-1;i++) ordine.remove(i);
		for(int i=0; i<Prezzi_da_menu.size()-1; i++) Prezzi_da_menu.remove(i);
	}
	
	
	public String toString(){
		String stringa="";
		
		for(int i=0; i<ordine.size(); i++) {
			if(i==0) stringa= ordine.get(i);
			else stringa= stringa+","+ordine.get(i);
		}
		
		return stringa;
		
	}
	
	
	
	
	
}
