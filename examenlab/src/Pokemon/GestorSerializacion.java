
package Pokemon;
import java.io.*;

public class GestorSerializacion {
    public static byte[] guardar(Entrenador entrenador) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(entrenador);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Entrenador restaurar(byte[] datos) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(datos);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (Entrenador) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
