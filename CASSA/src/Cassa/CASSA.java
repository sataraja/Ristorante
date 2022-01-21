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
import prog.utili.Orario;

public class CASSA {
  private static Vector<String> DaPagare = new Vector<String>();
	ServerSocket server=null;
	Socket socketClient = null;
	DataInputStream in;

	
	int porta = 6117; //porta server
	
	static protected int getdimensioneDaPagare() {
		return DaPagare.size();
	}
	
	public String Comunica() {
		String stato = null;
		try {
			String letto = in.readUTF();
			in.close();
			 System.out.println("ordine ricevuto: "+ letto);
			 DaPagare.add(letto);
			 System.out.println("DaPagare è stato incrementato. Adesso ha dimensione "+ DaPagare.size());
			 
			StringTokenizer st = new StringTokenizer(letto, ",");
			 System.out.println("DaPagare adesso ha dimensione (dopo st) "+ DaPagare.size());

			//Salviamo dettagli ordini su Vector DaPagare
			      String cliente;
			      cliente=st.nextToken();
			    	 
			    	 System.out.println("Il cliente che ha inviato ordine è: " + cliente);
			       
           stato=letto;
			letto=null;
			 System.out.println("DaPagare Adesso ha dimensione (dopo letto=null) "+ DaPagare.size());


		} catch (IOException e) {
			
		}
		
		return stato;
	}
	
	
	
	
	
	public Socket attendi() {
		try {
		System.out.println("Inizializzo il server...");
		System.out.println("Dim. DaPagare all'inizio del secondo attendi: " + getdimensioneDaPagare());
		server= new ServerSocket(porta);  //inizializziamo il servizio
		System.out.println("Dim. DaPagare all'inizio del secondo attendi, dopo creazione server: " + getdimensioneDaPagare());

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
	public static String Registra_pagamento(String id_cliente) {
		System.out.println("DaPagare prima del for, prima di cliente_pagante= null ha dim.: " + DaPagare.size());
		String cliente_pagante = null;
		System.out.println("DaPagare prima del for, dopo creazione di cliente pagante = null ha dimensione: " + DaPagare.size());
		String prezzo_pagato=null;
		System.out.println("DaPagare prima del for ha dimensione: " + DaPagare.size());
		for(int i=0; i<DaPagare.size(); i=i+1) {
			System.out.println("Sono dentro al for, sto analizzando DaPagare");
			StringTokenizer st = new StringTokenizer(DaPagare.get(i), ",");
			if(st.nextToken().equals(id_cliente)) {//se primo elemento della riga contenuta in DaPagare.get(i) == id_cliente 
				System.out.println("Trovato in DaPagare il pagante" + id_cliente);
				cliente_pagante=id_cliente;
				prezzo_pagato=st.nextToken();
				DaPagare.remove(i);
				
				//registra pagamento
				FileOutputManager outfile = new FileOutputManager ("Pagati.txt");
				 outfile.println(new Data()+" " + new Orario()+"   ID_Cliente:"+cliente_pagante + "   Pagati: "+ prezzo_pagato);
				 break;
			}
			
		}
		
		
		return cliente_pagante; 
	}
	
	
	
	
public static void main(String[] args) {
		// TODO Auto-generated method stub
CASSA s= new CASSA();
 


//Ordini da Pagare


CASSA c= new CASSA();
int fase=1;
while(true) {
switch(fase) {
case 1: c.attendi();
fase=2;


case 2: 
   String stato=  c.Comunica();
   System.out.println("DaPagare ha dimensione (dopo il comunica del case 2): " + DaPagare.size());
   if(stato!=null) {fase=1;
   System.out.println("DaPagare ha dimensione ( case 2, nell'if): " + DaPagare.size());

   }
   else {fase=2;   
   System.out.println("DaPagare ha dimensione (nel case 2, nell'else): " + DaPagare.size());

   }
       }//switch
     }//while

	}




//pulisce console
	public static void clearScreen() {  
		for (int i = 0; i < 50; ++i) System.out.println();
 
	}





}
