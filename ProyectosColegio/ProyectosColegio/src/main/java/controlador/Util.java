package controlador;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public final class Util {

    private Util() {
    }

    // Convierte a int; si viene vacio o invalido devuelve -1
    public static int entero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Texto vacio se guarda como null
    public static String texto(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return valor.trim();
    }

    // El input type="datetime-local" envia yyyy-MM-ddTHH:mm; vacio = sin fecha
    public static LocalDateTime fechaHora(String valor) throws DateTimeParseException {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(valor.trim());
    }
}