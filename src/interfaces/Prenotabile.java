package interfaces;

import java.time.LocalDate;
import java.util.Date;

public interface Prenotabile {
    default void prenota(LocalDate dataPrenotazione) {}
    void disdici();
    boolean prenotato();
}
