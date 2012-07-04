package Network;
import java.io.Serializable;

/**
 * 
 * 
 * The SerializedBool class is used to send a serialized boolean array from Client to Server.
 * This array contains the information about Controls, namely pressend an released keys.
 * <P>
 * 
 * @author Friedrich
 * 
 */

public class SerializedBool implements Serializable {
   private Boolean array[] = null;

   public SerializedBool() {
   }

   public void setArray(Boolean array[]) {
     this.array = array;
   }

   public Boolean[] getArray() {
     return array;
   }
}