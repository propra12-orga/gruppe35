import java.io.*;
import java.net.*;



class Connect extends Thread {
   private Socket client = null;
   private ObjectInputStream ois = null;
   private ObjectOutputStream oos = null;
    
   public Connect() {}

   public Connect(Socket clientSocket) {
     client = clientSocket;
     try {
      ois = new ObjectInputStream(client.getInputStream());
      oos = new ObjectOutputStream(client.getOutputStream());
     } catch(Exception e1) {
         try {
            client.close();
         }catch(Exception e) {
           System.out.println(e.getMessage());
         }
         return;
     }
     this.start();
   }

   @Override
public void run() {
      SerializedObject x = null;
      //SerializedObject y = null;
      int dataset1[] = new int[1];
      //int dataset2[] = new int[7];
      int result[] = new int[4];
      try {
         x = (SerializedObject) ois.readObject();
         //y = (SerializedObject) ois.readObject();
         dataset1 = x.getArray();
         //dataset2 = y.getArray();
         // create an array by multiplying two arrays
         for(int i=0;i<dataset1.length;i++) {
           result[i] = 0;
         }
         result[0] += dataset1[0];
         // ship the object to the client
         SerializedObject output = new SerializedObject();
         output.setArray(result);
         oos.writeObject(output);
         oos.flush();
         // close connections
         ois.close();
         oos.close();
         client.close(); 
      } catch(Exception e) {}       
   }
}

//// Dieses Programm arbeitet als Server und sendet
//// die Benutzereingaben an die Clients
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.concurrent.TimeUnit;
//
//class UniversalServer {
//	static ServerSocket server = null;
//
//	public static void main(String[] args) throws IOException, ClassNotFoundException {
//		// if (args.length != 1) {
//		// if (true == false){
//		// System.err.println("Verwendung: java UniversalServer " + "<port>");
//		// System.exit(1);
//		// }
//		server = new ServerSocket(5000);
//		// System.out.println("Warte auf Verbindung auf Port " + args[0]);
//		Socket sock = server.accept();
//		System.out.println("Verbindung hergestellt, "
//				+ "geben Sie ihre Daten ein (ICH BIN SERVER)");
//		
//		
//		ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
//
//		ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
//
//		ReceiveThread a = (new ReceiveThread(in, "Server: "));
//		a.start();
//		
//		double o = (Double) in.readObject();
//		System.out.println("aus dem Client kommt: " + o + '\n');
//
//		while (true) {
//			// System.out.println(str3 + '\n');
//			// System.out.println(str3 + '\n');
//			// eine Zeile einlesen
//			// String str2 = keyboard.readLine();
//			// System.out.println("was da loooos");
//			// if (str2.equalsIgnoreCase("quit")) {
//			// sock.close();
//			// System.exit(1);
//			// }
//			// System.out.print(garbage);
//			
//			// die Zeile an den Client senden
//			// out.write(("vom server kommich").getBytes());
//			// out.writeObject(was soll geschriebenwerden?);
//			out.flush();
//
//			try {
//				TimeUnit.MILLISECONDS.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			System.out.println("das hier packe ichnicht einmal!");
//		}
//
//	}
//}
