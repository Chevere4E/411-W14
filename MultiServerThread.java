import java.net.*;
import java.io.*;
import java.util.Date;

public class MultiServerThread extends Thread {
   private Socket socket = null;

   public MultiServerThread(Socket socket) {
      super("MultiServerThread");
      this.socket = socket;
      ServerMultiClient.NoClients++;
   }

   public void run() {

      try {
         PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         String lineIn, lineOut;
		
	     while((lineIn = entrada.readLine()) != null){
            System.out.println("Received: "+lineIn);
            escritor.flush();
            if(lineIn.equals("FIN")){
               ServerMultiClient.NoClients--;
			      break;
            }
            else if(lineIn.equals("#")){
               escritor.println("Personas Conectadas: "+ServerMultiClient.NoClients);
            }else{
               escritor.println("Echo... "+lineIn);
               escritor.flush();
            }
         } 
         try{		
            entrada.close();
            escritor.close();
            socket.close();
         }catch(Exception e){ 
            System.out.println ("Error : " + e.toString()); 
            socket.close();
            System.exit (0); 
   	   } 
      }catch (IOException e) {
         e.printStackTrace();
      }
   }
} 
