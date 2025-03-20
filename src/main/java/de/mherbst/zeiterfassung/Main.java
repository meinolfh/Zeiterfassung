package de.mherbst.zeiterfassung;

/**
 * When JavaFX application is started without Classloader info for JavaFX, we need another Main class.
 */
public class Main {

    public static void main ( String[] args ) {

        Application.main ( args );
    }
}
