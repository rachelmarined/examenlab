package Pokemon;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GestorSerializacion {

    public static byte[] guardar(Entrenador entrenador) {
        if (entrenador == null) {
            throw new IllegalArgumentException("No hay entrenador para guardar.");
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(entrenador);
            oos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo guardar el equipo.", e);
        }
    }

    public static Entrenador restaurar(byte[] datos) {
        if (datos == null || datos.length == 0) {
            throw new IllegalArgumentException("No hay datos guardados para restaurar.");
        }
        try (ByteArrayInputStream bais = new ByteArrayInputStream(datos);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            Object leido = ois.readObject();
            if (!(leido instanceof Entrenador entrenador)) {
                throw new IllegalStateException("Los datos guardados no corresponden a un entrenador.");
            }
            return entrenador;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("No se pudo restaurar el equipo guardado.", e);
        }
    }
}
