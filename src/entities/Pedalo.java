package entities;

import interfaces.PrenotabileConSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public class Pedalo extends Servizio implements PrenotabileConSlot {
    private LocalTime oraPrenotazione;

    public Pedalo(String nome) {
        super(nome, Costo.COSTO_PEDALO);
    }

    @Override
    public double getCosto() {
        return slotPrenotazione * Costo.COSTO_PEDALO.getCosto();
    }

    @Override
    public void setOraPrenotazione(LocalTime oraPrenotazione) {
        this.oraPrenotazione = oraPrenotazione;
    }

    @Override
    public void prenota(LocalDate dataPrenotazione, int slot) {
        this.dataPrenotazione = dataPrenotazione;
        this.slotPrenotazione = slot;
    }

    @Override
    public void disdici() {
        super.disdici();
        this.oraPrenotazione = null;
    }
}
