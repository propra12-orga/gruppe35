package Network;
import java.io.*;

public class SerializedObject implements Serializable {
   private int array[] = null;

   public SerializedObject() {
   }

   public void setArray(int array[]) {
     this.array = array;
   }

   public int[] getArray() {
     return array;
   }
}