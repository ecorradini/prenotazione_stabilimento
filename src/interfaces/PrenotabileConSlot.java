package interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface PrenotabileConSlot extends Prenotabile {
    void prenota(LocalDate dataPrenotazione, int slot);

    default void setOraPrenotazione(LocalTime oraPrenotazione) {}
}
