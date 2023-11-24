import controllers.GestorePrenotazioni;
import entities.*;
import exceptions.MaxChoiceException;
import interfaces.Prenotabile;
import interfaces.PrenotabileConSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);

    private static int readIntInput() {
        try {
            int input = INPUT.nextInt();
            INPUT.nextLine();
            return input;
        } catch (InputMismatchException e) {
            INPUT.nextLine();
            return 0;
        }
    }

    private static String readStringInput() {
        return INPUT.nextLine();
    }

    private static int getScelta() throws MaxChoiceException {
        System.out.println("\n\n--------\nBenvenut* nel gestore prenotazioni!\n");
        System.out.println(
                "Che cosa vuoi prenotare?\n1. Ombrellone\n2. Sdraio\n3. Cabina\n4. Pedalò" +
                        "\n5. Visualizza prenotazioni"
        );
        System.out.print("Scelta: ");
        int scelta = readIntInput();
        if(scelta >= 1 && scelta <= 5) {
            return scelta;
        }
        else {
            throw new MaxChoiceException();
        }
    }

    private static <T extends Prenotabile> T visualizzaEScegliServizio(Class<T> daVisualizzare)
    throws MaxChoiceException {
        System.out.println("\n-----\nQuale " + daVisualizzare.getSimpleName().toLowerCase() + " vuoi prenotare?");
        T[] tuttiServizi = GestorePrenotazioni.filterByClass(daVisualizzare);
        for(int i=0; i<tuttiServizi.length; i++) {
            System.out.println((i+1) + ". " + tuttiServizi[i].toString());
        }
        System.out.print("Scelta: ");
        int scelta = readIntInput();
        if(scelta > tuttiServizi.length || scelta < 1) {
            throw new MaxChoiceException();
        }
        return tuttiServizi[scelta-1];
    }

    private static LocalDate scegliDataPrenotazione() {
        LocalDate dataPrenotazione = null;
        do {
            System.out.print("\nInserisci data prenotazione (dd/MM/yyyy): ");
            String dataS = readStringInput();
            try {
                dataPrenotazione = LocalDate.parse(dataS, GestorePrenotazioni.DATE_FORMATTER);
            } catch(DateTimeParseException ignored) {
            }
        } while(dataPrenotazione == null);
        return dataPrenotazione;
    }

    private static LocalTime scegliOraPrenotazione() {
        LocalTime oraPrenotazione = null;
        do {
            System.out.print("\nInserisci ora prenotazione (HH:mm): ");
            String oraS = readStringInput();
            try {
                oraPrenotazione = LocalTime.parse(oraS, GestorePrenotazioni.HOUR_FORMATTER);
            } catch(DateTimeParseException ignored) {
            }
        } while(oraPrenotazione == null);
        return oraPrenotazione;
    }

    private static <T extends Servizio & Prenotabile> void prenota(Class<T> tipologiaDaPrenotare)
    throws MaxChoiceException {
        T daPrenotare = visualizzaEScegliServizio(tipologiaDaPrenotare);
        LocalDate dataPrenotazione = scegliDataPrenotazione();
        LocalTime oraPrenotazione;
        int slot;
        String ricevuta = null;
        if(daPrenotare instanceof PrenotabileConSlot) {
            if(daPrenotare instanceof Pedalo) {
                oraPrenotazione = scegliOraPrenotazione();
                System.out.print("\nPer quante ore vuoi prenotare il pedalò? ");
                slot = readIntInput();
                ricevuta = GestorePrenotazioni.prenota((Pedalo) daPrenotare, dataPrenotazione, oraPrenotazione, slot);
            } else if(daPrenotare instanceof Ombrellone) {
                System.out.println("\nPer quanto vuoi prenotare?\n1. Mattina\n2. Pomeriggio\n3. Giornata");
                System.out.print("Scelta: ");
                slot = readIntInput();
                if(slot < 1 || slot > 3) {
                    throw new MaxChoiceException();
                }
                ricevuta = GestorePrenotazioni.prenota((Ombrellone) daPrenotare, dataPrenotazione, slot);
            }
        } else {
            ricevuta = GestorePrenotazioni.prenota(daPrenotare, dataPrenotazione);
        }

        System.out.println("\n" + ricevuta);
    }

    private static void visualizzaEScegliPrenotato() {
        System.out.println("\nQuesti sono i servizi attualmente prenotati:");
        Prenotabile[] serviziPrenotati = GestorePrenotazioni.getPrenotati();
        for(int i=0; i<serviziPrenotati.length; i++) {
            System.out.println((i+1) + ". " + serviziPrenotati[i].toString());
        }
        System.out.print("Scelta: ");
    }

    public static void main(String[] args) {
        GestorePrenotazioni.init();
        int scelta = 0;
        do {
            try {
                scelta = getScelta();
                switch (scelta) {
                    case 1:
                        prenota(Ombrellone.class);
                        break;
                    case 2:
                        prenota(Sdraio.class);
                        break;
                    case 3:
                        prenota(Cabina.class);
                        break;
                    case 4:
                        prenota(Pedalo.class);
                        break;
                    case 5:
                        visualizzaEScegliPrenotato();
                        break;
                    default:
                        break;
                }
            } catch (MaxChoiceException e) {
                System.out.println("Scelta non conforme.");
            }
        } while(scelta <=5 && scelta >= 0);
    }
}