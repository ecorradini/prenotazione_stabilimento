package entities;

import interfaces.Prenotabile;

import java.time.LocalDate;

public class Cabina extends Servizio {

    public Cabina(String nome) {
        super(nome, Costo.COSTO_CABINA);
    }

    @Override
    public double getCosto() {
        return slotPrenotazione * Costo.COSTO_CABINA.getCosto();
    }

    @Override
    public void prenota(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
        this.slotPrenotazione = 1;
    }
}
