package entities;

import interfaces.Prenotabile;

import java.time.LocalDate;

public class Sdraio extends Servizio {

    public Sdraio(String nome) {
        super(nome, Costo.COSTO_SDRAIO);
    }

    @Override
    public double getCosto() {
        return slotPrenotazione * Costo.COSTO_SDRAIO.getCosto();
    }

    @Override
    public void prenota(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
        this.slotPrenotazione = 1;
    }
}
