
package utilidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class GeneradorNumeroGuia {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    private GeneradorNumeroGuia() {
        // Constructor privado para evitar su instancia
    }

    public static String generarGuia() {
        String fecha = LocalDateTime.now().format(FORMATO);
        String uuid = UUID.randomUUID()
            .toString()
            .substring(0, 8)
            .toUpperCase();

        return "PW-" + fecha + "-" + uuid;
    }
}