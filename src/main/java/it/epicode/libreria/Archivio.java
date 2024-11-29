package it.epicode.libreria;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Archivio {
    private static final Logger LOGGER = LoggerFactory.getLogger(Archivio.class);

    private static HashSet<Integer> isbnSet = new HashSet<>(); // Per tenere traccia degli ISBN univoci


    //Metodo creazione libro
    public static Libri creazioneLibro(Scanner scanner) {
        try {
            System.out.println("Inserisci ISBN:");
            int ISBN = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            // Controlla se l'ISBN è già stato usato
            if (!isbnSet.add(ISBN)) {
                throw new ArchivioEx("ISBN duplicato: " + ISBN);
            }

            System.out.println("Inserisci il titolo:");
            String titolo = scanner.nextLine();

            System.out.println("Inserisci l'anno di pubblicazione:");
            int annoPubblicazione = scanner.nextInt();

            System.out.println("Inserisci il numero di pagine:");
            int numeroPagine = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            System.out.println("Inserisci l'autore:");
            String autore = scanner.nextLine();

            System.out.println("Inserisci il genere:");
            String genere = scanner.nextLine();

            return new Libri(ISBN, titolo, annoPubblicazione, numeroPagine, autore, genere);
        } catch (ArchivioEx e) {
            LOGGER.error(() -> e.getMessage());// Registra il messaggio di errore con il logger
        }
        return null;
    }


    //Metodo creazione rivista
    public static Rivista creazioneRivista(Scanner scanner) {
        try {
            System.out.println("Inserisci ISBN:");
            int ISBN = scanner.nextInt();
            scanner.nextLine();

            // Controlla se l'ISBN è già stato usato
            if (!isbnSet.add(ISBN)) {
                throw new ArchivioEx("ISBN duplicato: " + ISBN);
            }

            System.out.println("Inserisci il titolo:");
            String titolo = scanner.nextLine();

            System.out.println("Inserisci l'anno di pubblicazione:");
            int annoPubblicazione = scanner.nextInt();

            System.out.println("Inserisci il numero di pagine:");
            int numeroPagine = scanner.nextInt();

            System.out.println("Scegli la periodicità (1 = Settimanale, 2 = Mensile, 3 = Semestrale):");
            int sceltaPeriodicita = scanner.nextInt();

            Rivista.Periodicita periodicita;
            switch (sceltaPeriodicita) {
                case 1:
                    periodicita = Rivista.Periodicita.SETTIMANALE;
                    break;
                case 2:
                    periodicita = Rivista.Periodicita.MENSILE;
                    break;
                case 3:
                    periodicita = Rivista.Periodicita.SEMESTRALE;
                    break;
                default:
                    periodicita = Rivista.Periodicita.MENSILE;
                    System.out.println("Scelta non valida, impostato MENSILE come periodicità.");
            }

            Rivista rivista = new Rivista(ISBN, titolo, annoPubblicazione, numeroPagine);
            rivista.setPeriodicita(periodicita);
            return rivista;
        } catch (ArchivioEx e) {
            LOGGER.error(() -> e.getMessage()); // Registra il messaggio di errore con il logger
        }
        return null;
    }

    //metodo per ricerca ISBN
    public static InfoLibriRiviste ricercaPubblicazione(Scanner scanner,HashSet<InfoLibriRiviste> archivioPubblicazione){
        System.out.println("Inserisci ISBN per la ricerca della Pubblicazione");
        int ISBN = scanner.nextInt();
        return archivioPubblicazione .stream()
                .filter(pubblicazione -> pubblicazione.getISBN() == ISBN)
                .findFirst()
                .orElse(null);
    }

    // metodo per l'eliminazione della pubblicazione tramite ISBN
    public static void eliminazionePubblicazione(Scanner scanner, HashSet<InfoLibriRiviste> archivioPubblicazione) {
        System.out.println("Inserisci ISBN per l'eliminazione della Pubblicazione");
        int ISBN = scanner.nextInt();

        InfoLibriRiviste pubblicazioneDaEliminare = archivioPubblicazione.stream()
                .filter(pubblicazione -> pubblicazione.getISBN() == ISBN)
                .findFirst()
                .orElse(null);

        if (pubblicazioneDaEliminare != null) {
            archivioPubblicazione.remove(pubblicazioneDaEliminare);
            isbnSet.remove(ISBN);
            System.out.println("Pubblicazione con ISBN " + ISBN + " eliminata con successo.");
        } else {
            System.out.println("Nessuna pubblicazione trovata con l'ISBN " + ISBN);
        }
    }

    //ricerca per anno di pubblicazione
    public static void ricercaPerAnnoDiPubblicazione(Scanner scanner, HashSet<InfoLibriRiviste> archivioPubblicazione) {
        System.out.println("Inserisci anno di pubblicazione:");
        int annoDiPubblicazione = scanner.nextInt();

        List<InfoLibriRiviste> pubblicazioniTrovate = archivioPubblicazione.stream()
                .filter(pubblicazione -> pubblicazione.getAnnoPubblicazione() == annoDiPubblicazione)
                .collect(Collectors.toList());

        if (!pubblicazioniTrovate.isEmpty()) {
            System.out.println("Pubblicazioni trovate:");
            pubblicazioniTrovate.forEach(System.out::println);
        } else {
            System.out.println("Nessuna pubblicazione trovata per l'anno " + annoDiPubblicazione);
        }
    }

    // Ricerca per autore
    public static void ricercaPerAutore(Scanner scanner, HashSet<Libri> archivioPubblicazione) {
        System.out.println("Inserisci nome autore:");
        String nome = scanner.nextLine();

        List<Libri> libriTrovati = archivioPubblicazione.stream()
                .filter(libro -> libro.getAutore().equalsIgnoreCase(nome))
                .collect(Collectors.toList());

        if (!libriTrovati.isEmpty()) {
            System.out.println("Libri trovati dell'autore " + nome + ":");
            libriTrovati.forEach(System.out::println);
        } else {
            System.out.println("Nessun libro trovato con l'autore " + nome);
        }
    }

    // Metodo per la modifica dei parametri di una pubblicazione tramite ISBN
    public static void modificaPubblicazione(Scanner scanner, HashSet<InfoLibriRiviste> archivioPubblicazione) {
        System.out.println("Inserisci ISBN per la modifica della Pubblicazione");
        int ISBN = scanner.nextInt();
        scanner.nextLine();

        // Cerca la pubblicazione con l'ISBN specificato
        InfoLibriRiviste pubblicazioneDaModificare = archivioPubblicazione.stream()
                .filter(pubblicazione -> pubblicazione.getISBN() == ISBN)
                .findFirst()
                .orElse(null);

        if (pubblicazioneDaModificare != null) {
            System.out.println("Pubblicazione trovata: " + pubblicazioneDaModificare);

            // Modifica i parametri comuni
            System.out.println("Inserisci il nuovo titolo (lascia vuoto per non modificare):");
            String nuovoTitolo = scanner.nextLine();
            if (!nuovoTitolo.isEmpty()) {
                pubblicazioneDaModificare.setTitolo(nuovoTitolo);
            }

            System.out.println("Inserisci il nuovo anno di pubblicazione (inserisci -1 per non modificare):");
            int nuovoAnno = scanner.nextInt();
            if (nuovoAnno != -1) {
                pubblicazioneDaModificare.setAnnoPubblicazione(nuovoAnno);
            }

            System.out.println("Inserisci il nuovo numero di pagine (inserisci -1 per non modificare):");
            int nuovoNumeroPagine = scanner.nextInt();
            scanner.nextLine();
            if (nuovoNumeroPagine != -1) {
                pubblicazioneDaModificare.setNumeroPagine(nuovoNumeroPagine);
            }

            // Se la pubblicazione è un libro, modifica i parametri specifici dei libri
            if (pubblicazioneDaModificare instanceof Libri) {
                Libri libro = (Libri) pubblicazioneDaModificare;

                System.out.println("Inserisci il nuovo autore (lascia vuoto per non modificare):");
                String nuovoAutore = scanner.nextLine();
                if (!nuovoAutore.isEmpty()) {
                    libro.setAutore(nuovoAutore);
                }

                System.out.println("Inserisci il nuovo genere (lascia vuoto per non modificare):");
                String nuovoGenere = scanner.nextLine();
                if (!nuovoGenere.isEmpty()) {
                    libro.setGenere(nuovoGenere);
                }

            } else if (pubblicazioneDaModificare instanceof Rivista) {
                // Se la pubblicazione è una rivista, modifica la periodicità
                Rivista rivista = (Rivista) pubblicazioneDaModificare;

                System.out.println("Scegli la nuova periodicità (1 = Settimanale, 2 = Mensile, 3 = Semestrale, 0 = Non modificare):");
                int sceltaPeriodicita = scanner.nextInt();
                scanner.nextLine();

                if (sceltaPeriodicita != 0) {
                    Rivista.Periodicita nuovaPeriodicita;
                    switch (sceltaPeriodicita) {
                        case 1:
                            nuovaPeriodicita = Rivista.Periodicita.SETTIMANALE;
                            break;
                        case 2:
                            nuovaPeriodicita = Rivista.Periodicita.MENSILE;
                            break;
                        case 3:
                            nuovaPeriodicita = Rivista.Periodicita.SEMESTRALE;
                            break;
                        default:
                            nuovaPeriodicita = rivista.getPeriodicita(); // Nessuna modifica
                            System.out.println("Scelta non valida. La periodicità non sarà modificata.");
                    }
                    rivista.setPeriodicita(nuovaPeriodicita);
                }
            }

            System.out.println("Modifica completata con successo. Pubblicazione aggiornata: " + pubblicazioneDaModificare);

        } else {
            System.out.println("Nessuna pubblicazione trovata con l'ISBN " + ISBN);
        }
    }

    // Metodo per calcolare le statistiche del catalogo
    public static void statisticheCatalogo(HashSet<InfoLibriRiviste> archivioPubblicazione) {
        if (archivioPubblicazione.isEmpty()) {
            System.out.println("L'archivio è vuoto, non ci sono dati da analizzare.");
            return;
        }

        long numeroLibri = archivioPubblicazione.stream()
                .filter(elemento -> elemento instanceof Libri)
                .count();

        long numeroRiviste = archivioPubblicazione.stream()
                .filter(elemento -> elemento instanceof Rivista)
                .count();

        InfoLibriRiviste elementoConPiuPagine = archivioPubblicazione.stream()
                .max((a, b) -> Integer.compare(a.getNumeroPagine(), b.getNumeroPagine()))
                .orElse(null);

        OptionalDouble mediaPagine = archivioPubblicazione.stream()
                .mapToInt(InfoLibriRiviste::getNumeroPagine)
                .average();

        // Stampa dei risultati
        System.out.println("Statistiche del catalogo:");
        System.out.println("Numero totale di libri presenti: " + numeroLibri);
        System.out.println("Numero totale di riviste presenti: " + numeroRiviste);

        if (elementoConPiuPagine != null) {
            System.out.println("Elemento con il maggior numero di pagine: " + elementoConPiuPagine);
        }

        if (mediaPagine.isPresent()) {
            System.out.printf("Media del numero di pagine di tutti gli elementi: %.2f%n", mediaPagine.getAsDouble());
        } else {
            System.out.println("Media del numero di pagine: N/A");
        }
    }
}
