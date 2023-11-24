package entities;

import interfaces.PrenotabileConSlot;

import java.time.LocalDate;
import java.util.Date;

public class Ombrellone extends Servizio implements PrenotabileConSlot {

    public enum Slot {
        NON_PRENOTATO(0),
        MATTINA(1),
        POMERIGGIO(2),
        GIORNATA(3);

        private final int slot;
        Slot(int slot) { this.slot = slot; }

        public int getSlot() { return slot; }
    }

    private Slot slotPrenotazione=Slot.NON_PRENOTATO;

    public Ombrellone(String nome) {
        super(nome, Costo.COSTO_OMBRELLONE);
    }

    @Override
    public double getCosto() {
        switch(slotPrenotazione) {
            case MATTINA, POMERIGGIO:
                return Costo.COSTO_OMBRELLONE.getCosto();
            case GIORNATA:
                return 2 * Costo.COSTO_OMBRELLONE.getCosto();
            default:
                return 0;
        }
    }

    @Override
    public void prenota(LocalDate dataPrenotazione, int slot) {
        this.dataPrenotazione = dataPrenotazione;
        switch(slot) {
            case 1:
                slotPrenotazione = Slot.MATTINA;
                break;
            case 2:
                slotPrenotazione = Slot.POMERIGGIO;
                break;
            case 3:
                slotPrenotazione = Slot.GIORNATA;
                break;
        }
    }

    @Override
    public void disdici() {
        this.dataPrenotazione = null;
        this.slotPrenotazione = Ombrellone.Slot.NON_PRENOTATO;
    }

    @Override
    public boolean prenotato() {
        return slotPrenotazione != Slot.NON_PRENOTATO;
    }
}
