package it.epicode.libreria;

public class Rivista  extends InfoLibriRiviste{

    public enum Periodicita {
        SETTIMANALE, MENSILE, SEMESTRALE
    }
    private Periodicita periodicita;

    public Rivista(int ISBN, String titolo, int annoPubblicazione, int numeroPagine) {
        super(ISBN, titolo, annoPubblicazione, numeroPagine);
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "periodicita=" + periodicita +
                '}';
    }
}
