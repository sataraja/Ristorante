package Cassa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import prog.io.FileInputManager;
import prog.io.FileOutputManager;
import prog.utili.Data;

public class CASSA {
 static Vector<String> DaPagare = new Vector<String>();
	ServerSocket server=null;
	Socket socketClient = null;
	DataInputStream in;

	
	int porta = 6117; //porta server
	
	
	
	public void Comunica() {
		try {
			String letto = in.readLine();
			
			StringTokenizer st = new StringTokenizer(letto, ",");
			//Salviamo dettagli ordini su Vector DaPagare
			       while(st.hasMoreTokens()) {
			    	 DaPagare.add(st.nextToken());
			    	 System.out.println("ordine ricevuto");
			       }

			letto=null;

		} catch (IOException e) {
			
		}
	}
	
	
	
	
	
	public Socket attendi() {
		try {
		System.out.println("Inizializzo il server...");
		server= new ServerSocket(porta);  //inizializziamo il servizio
		
		System.out.println("Server pronto, in ascolto sulla porta: " + porta);
		//mi metto in ascolto sulla porta che ho aperto
		socketClient = server.accept(); //accettiamo connessione con client (quando avviene)
		System.out.println("Connessione stabilita!");
		server.close(); //(connessione punto-a-punto) una volta accettata connessione con un client, con questo metodo evitiamo connessioni multiple(che altri client possano connettersi anche)
		
		in= new DataInputStream(socketClient.getInputStream()); //riferimento al canale per lettura				
		
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		return socketClient;
	}
	
	
	
	//aggiunge al file Pagati i dettagli degli ordini effettuati dai clienti (compresa giornata in cui è stato effettuato)
	public static void Registra_pagamento(String id_cliente) {
		String cliente_pagante = null;
		
		for(int i=0; i<DaPagare.size()-1; i++) {
			StringTokenizer st = new StringTokenizer(DaPagare.get(i), ",");
			if(st.nextToken().equals(id_cliente)) { //se primo elemento della riga contenuta in DaPagare.get(i) == id_cliente 
				cliente_pagante=DaPagare.get(i);
				DaPagare.remove(i);
				break;
			}
			
		}
		
		//registra pagamento
		FileOutputManager outfile = new FileOutputManager ("Pagati.txt");
		 outfile.println(new Data()+"   "+cliente_pagante); 
	}
	
	
	
	
public static void main(String[] args) {
		// TODO Auto-generated method stub
CASSA s= new CASSA();
 

//avvio Thread per gestione pagamenti
//SezionePagamenti r = new SezionePagamenti(); 
//Thread t = new Thread(r); 
//t.start(); 

//Ordini da Pagare
while(true) {
s.attendi();
s.Comunica();
System.out.println("\n");
}



	}




//pulisce console
	public static void clearScreen() {  
		for (int i = 0; i < 50; ++i) System.out.println();
 
	}





}
