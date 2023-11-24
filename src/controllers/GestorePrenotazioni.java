package controllers;

import entities.*;
import interfaces.Prenotabile;
import interfaces.PrenotabileConSlot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GestorePrenotazioni {
    //private static Prenotabile[] serviziPrenotabili;
    private static final Set<Prenotabile> serviziPrenotabili = new HashSet<>();
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static <T extends Servizio> T[] splitSezione(Class<T> tipoServizio, String sezione) {
        String[] nomi = sezione.split("\n");
        Servizio[] result = new Servizio[nomi.length];
        for(int i = 0; i<nomi.length; i++) {
            if(!nomi[i].isEmpty()) {
                try {
                    T corrente = tipoServizio.getConstructor(String.class).newInstance(nomi[i]);
                    result[i] = corrente;
                } catch (Exception ignored) {
                }
            }
        }
        return Arrays.stream(result).toArray(size -> (T[]) Array.newInstance(tipoServizio, size));
    }

    public static void init() {
        try {
            Scanner fileInput = new Scanner(new FileInputStream("servizi.txt"));
            fileInput.useDelimiter("###");
            String[] sezioni = new String[4];
            int i = 0;
            while(fileInput.hasNext()) {
                sezioni[i] = fileInput.next();
                i++;
            }
            serviziPrenotabili.addAll(Arrays.stream(splitSezione(Ombrellone.class, sezioni[0])).toList());
            serviziPrenotabili.addAll(Arrays.stream(splitSezione(Cabina.class, sezioni[1])).toList());
            serviziPrenotabili.addAll(Arrays.stream(splitSezione(Sdraio.class, sezioni[2])).toList());
            serviziPrenotabili.addAll(Arrays.stream(splitSezione(Pedalo.class, sezioni[3])).toList());
        } catch(FileNotFoundException e) {
            System.out.println("Non ho trovato il file di dati.");
        }
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
