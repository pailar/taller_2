package controlador;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public final class Util {

    private Util() {
    }

    public static int entero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String texto(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return valor.trim();
    }

    public static LocalDateTime fechaHora(String valor) throws DateTimeParseException {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(valor.trim());
    }
}