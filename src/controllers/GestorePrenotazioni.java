package controllers;

import entities.*;
import interfaces.Prenotabile;
import interfaces.PrenotabileConSlot;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class GestorePrenotazioni {
    //private static Prenotabile[] serviziPrenotabili;
    private static final Set<Prenotabile> serviziPrenotabili = new HashSet<>();
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static void init() {
        serviziPrenotabili.add(new Ombrellone("Ombrellone 1"));
        serviziPrenotabili.add(new Ombrellone("Ombrellone 2"));
        serviziPrenotabili.add(new Ombrellone("Ombrellone 3"));
        serviziPrenotabili.add(new Sdraio("Sdraio 1"));
        serviziPrenotabili.add(new Sdraio("Sdraio 2"));
        serviziPrenotabili.add(new Sdraio("Sdraio 3"));
        serviziPrenotabili.add(new Pedalo("Pedalò blu"));
        serviziPrenotabili.add(new Pedalo("Pedalò rosso"));
        serviziPrenotabili.add(new Cabina("Cabina #1"));
        serviziPrenotabili.add(new Cabina("Cabina #2"));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Prenotabile> T[] filterByClass(Class<T> c) {
        return serviziPrenotabili.stream().filter(c::isInstance)
                .toArray(size -> (T[]) Array.newInstance(c, size));
    }

    public static Prenotabile[] getPrenotati() {
        return serviziPrenotabili.stream().filter(Prenotabile::prenotato)
                .toArray(Prenotabile[]::new);
    }

    public static <T extends Servizio & Prenotabile> String prenota(
            T daPrenotare,
            LocalDate dataPrenotazione
    ) {
        daPrenotare.prenota(dataPrenotazione);
        return daPrenotare.getClass().getSimpleName() + " prenotato per "
                + dataPrenotazione.format(DATE_FORMATTER)
                + ".\nCosto totale: " + daPrenotare.getCosto();
    }

    public static <T extends Servizio & PrenotabileConSlot> String prenota(
            T daPrenotare,
            LocalDate dataPrenotazione,
            int slotPrenotazione) {
        daPrenotare.prenota(dataPrenotazione, slotPrenotazione);
        return daPrenotare.getClass().getSimpleName() + " prenotato per "
                + dataPrenotazione.format(DATE_FORMATTER)
                + ".\nCosto totale: " + daPrenotare.getCosto();
    }

    public static <T extends Pedalo> String prenota(
            T daPrenotare,
            LocalDate dataPrenotazione,
            LocalTime oraPrenotazione,
            int slotPrenotazione
    ) {
        daPrenotare.setOraPrenotazione(oraPrenotazione);
        StringBuilder result = new StringBuilder(prenota(daPrenotare, dataPrenotazione, slotPrenotazione));
        result.insert(result.indexOf(".\n"), ", per l'ora " + oraPrenotazione.format(HOUR_FORMATTER));
        return result.toString();
    }
}
