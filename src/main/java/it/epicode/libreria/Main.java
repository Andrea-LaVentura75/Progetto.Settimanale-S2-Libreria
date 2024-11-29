package it.epicode.libreria;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashSet<InfoLibriRiviste> archivio = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        // Ciclo per creare libri o riviste
        while (continua) {
            System.out.println("Vuoi creare un libro o una rivista? (1 = Libro, 2 = Rivista)");
            int scelta = scanner.nextInt();

            if (scelta == 1) {
                InfoLibriRiviste libro = Archivio.creazioneLibro(scanner);
                if (libro != null) {
                    archivio.add(libro);
                }
            } else if (scelta == 2) {
                InfoLibriRiviste rivista = Archivio.creazioneRivista(scanner);
                if (rivista != null) {
                    archivio.add(rivista);
                }
            } else {
                System.out.println("Scelta non valida, riprova.");
            }

            System.out.println("Vuoi aggiungere un altro elemento? (true/false)");
            continua = scanner.nextBoolean();
        }

        // Menu principale per operazioni sul catalogo
        boolean esci = false;

        while (!esci) {
            System.out.println("\nMenu - Seleziona un'opzione:");
            System.out.println("1 - Creare un libro o una rivista");
            System.out.println("2 - Ricerca una pubblicazione tramite ISBN");
            System.out.println("3 - Eliminare una pubblicazione tramite ISBN");
            System.out.println("4 - Modificare una pubblicazione tramite ISBN");
            System.out.println("5 - Ricerca per anno di pubblicazione");
            System.out.println("6 - Ricerca per autore (solo libri)");
            System.out.println("7 - Visualizzare le statistiche del catalogo");
            System.out.println("0 - Uscire dal programma");

            System.out.print("Inserisci la tua scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    // Ripetiamo il ciclo per creare un libro o una rivista
                    System.out.println("Vuoi creare un libro o una rivista? (1 = Libro, 2 = Rivista)");
                    int sceltaCreazione = scanner.nextInt();
                    scanner.nextLine();

                    if (sceltaCreazione == 1) {
                        InfoLibriRiviste libro = Archivio.creazioneLibro(scanner);
                        if (libro != null) {
                            archivio.add(libro);
                        }
                    } else if (sceltaCreazione == 2) {
                        InfoLibriRiviste rivista = Archivio.creazioneRivista(scanner);
                        if (rivista != null) {
                            archivio.add(rivista);
                        }
                    } else {
                        System.out.println("Scelta non valida.");
                    }
                    break;

                case 2:
                    // Ricerca pubblicazione tramite ISBN
                    InfoLibriRiviste pubblicazioneTrovata = Archivio.ricercaPubblicazione(scanner, archivio);
                    if (pubblicazioneTrovata != null) {
                        System.out.println("Pubblicazione trovata: " + pubblicazioneTrovata);
                    } else {
                        System.out.println("Nessuna pubblicazione trovata con l'ISBN inserito.");
                    }
                    break;

                case 3:
                    // Eliminazione pubblicazione tramite ISBN
                    Archivio.eliminazionePubblicazione(scanner, archivio);
                    break;

                case 4:
                    // Modifica pubblicazione tramite ISBN
                    Archivio.modificaPubblicazione(scanner, archivio);
                    break;

                case 5:
                    // Ricerca per anno di pubblicazione
                    Archivio.ricercaPerAnnoDiPubblicazione(scanner, archivio);
                    break;

                case 6:
                    // Filtra solo gli oggetti di tipo Libri
                    HashSet<Libri> libriArchivio = new HashSet<>();
                    for (InfoLibriRiviste pubblicazione : archivio) {
                        if (pubblicazione instanceof Libri) {
                            libriArchivio.add((Libri) pubblicazione);
                        }
                    }

                    // Richiama il metodo di ricerca per autore
                    Archivio.ricercaPerAutore(scanner, libriArchivio);
                    break;


                case 7:
                    // Visualizzare le statistiche del catalogo
                    Archivio.statisticheCatalogo(archivio);
                    break;

                case 0:
                    // Esci dal programma
                    esci = true;
                    System.out.println("Uscita dal programma. Grazie e arrivederci!");
                    break;

                default:
                    System.out.println("Scelta non valida. Riprovare.");
                    break;
            }
        }
        scanner.close();
    }
}
