package entities;

import interfaces.Prenotabile;

import java.time.LocalDate;
import java.util.Date;

public abstract class Servizio implements Prenotabile {
    public enum Costo {
        COSTO_OMBRELLONE(20.0),
        COSTO_SDRAIO(8.0),
        COSTO_CABINA(50.0),
        COSTO_PEDALO(5.5);

        private double costo;
        Costo(double costo) {
            this.costo = costo;
        }

        public double getCosto() { return costo; }
    }
    private int id;
    private static int nextId = 0;
    private String nome;
    protected Costo costo;
    protected int slotPrenotazione = 0;
    protected LocalDate dataPrenotazione;

    public Servizio(String nome, Costo costo) {
        this.id = nextId;
        nextId++;
        this.nome = nome;
        this.costo = costo;
    }

    @Override
    public String toString() { return nome; }

    public abstract double getCosto();

    //public boolean isDisponibile() { return slotPrenotazione == 0; }

    @Override
    public boolean prenotato() {
        return slotPrenotazione != 0;
    }

    @Override
    public void disdici() {
        this.dataPrenotazione = null;
        this.slotPrenotazione = 0;
    }
}
