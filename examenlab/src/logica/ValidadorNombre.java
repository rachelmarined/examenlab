package logica;

public final class ValidadorNombre {

    public static final int MINIMO = 3;
    public static final int MAXIMO = 10;

    private ValidadorNombre() {
    }

    public static String validar(String nombre) {
        if (nombre == null) {
            return "Escribe un nombre.";
        }
        String limpio = nombre.trim();
        if (limpio.isEmpty()) {
            return "El nombre no puede quedar vacío ni formado solo por espacios.";
        }
        if (limpio.length() < MINIMO) {
            return "El nombre debe tener al menos " + MINIMO + " caracteres.";
        }
        if (limpio.length() > MAXIMO) {
            return "El nombre no puede superar los " + MAXIMO + " caracteres.";
        }
        for (int i = 0; i < limpio.length(); i++) {
            char caracter = limpio.charAt(i);
            if (!Character.isLetterOrDigit(caracter) && caracter != ' ') {
                return "El nombre solo admite letras, números y espacios.";
            }
        }
        return null;
    }

    public static String normalizar(String nombre) {
        return nombre == null ? "" : nombre.trim();
    }
}
