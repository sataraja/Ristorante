
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import prog.io.ConsoleInputManager;

public class CUCINA {

	ServerSocket server=null;
	Socket socketClient = null;
	DataInputStream in;

	
	int porta = 6116; //porta server
	
	
	
	public String Comunica() {
		String stato = null;
		
		try {
			System.out.println("Aspetto un messaggio dal client");
			String letto = in.readUTF();
			in.close();
			 System.out.println("Messaggio arrivato: "+ letto);
			StringTokenizer st = new StringTokenizer(letto, ",");
//Stampiamo dettagli ordine ricevuto
			int count=0;
       while(st.hasMoreTokens()) {
    	   if(count==0) System.out.println("ID_CLIENTE: " +st.nextToken());
    	   else if(count==1) {   
    		   System.out.println("Dettagli ordine: ");    /*alla cucina non importa sapere il costo dell'ordine.
        	   Da count>1 ci sarà lista delle pietanze ordinate
        	    */
    		   st.nextToken();
    	   }
    	   
    	   else System.out.println(st.nextToken());
    	  
    	   count++;
       }
			
			 stato=letto;
			 letto=null;

		} catch (IOException e) {
			
		}
		
		return stato;
	}
	
	
	
	
	
	public Socket attendi() {
		try {
		System.out.println("Inizializzo il server...");
		server= new ServerSocket(porta);  //inizializziamo il servizio
		
		System.out.println("Server pronto, in ascolto sulla porta: " + porta);
		//mi metto in ascolto sulla porta che ho aperto
		socketClient = server.accept(); //accettiamo connessione con client (quando avviene)
		System.out.println("Connessione stabilita!");
		server.close(); //(connessione punto-a-punto) una volta accettata connessione con un client, con questo metodo evitiamo connessioni multiple(che altri client possano connettersi)
		
		in= new DataInputStream(socketClient.getInputStream()); //riferimento al canale per lettura		
		
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		return socketClient;
	}
	
	
	
	
public static void main(String[] args) {
		// TODO Auto-generated method stub
CUCINA s= new CUCINA();
int fase=1;
while(true) {
switch(fase) {
case 1: s.attendi();
fase=2;


case 2: 
   String stato=  s.Comunica();
   if(stato!=null) fase=1;
   else fase=2;   
 
 
       }//switch
     }//while
	}





//pulisce console
	public static void clearScreen() {  
		for (int i = 0; i < 50; ++i) System.out.println();
 
	}





}