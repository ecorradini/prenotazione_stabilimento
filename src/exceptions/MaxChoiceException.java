package exceptions;

import java.util.NoSuchElementException;

public class MaxChoiceException extends NoSuchElementException {

    public MaxChoiceException() {
        super("Scelta maggiore della scelta massima.");
    }
}
