import java.io.Serializable;

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