package com.example.demo.classes;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

public class Scontrino {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/supermercato";
        String username = "postgres";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String carta = estraiCartaCasuale(connection);
            List<Transazione> scontrino = creaScontrinoCasuale(connection);

            BigDecimal totale = stampaScontrino(carta, scontrino);
            BigDecimal puntiTotali = calcolaTotalePunti(scontrino);

            System.out.println("Costo totale degli articoli: " + formatDecimal(totale) + " €");
            System.out.println("Totale punti aggiunti alla carta: " + formatDecimal(puntiTotali));

            aggiungiPuntiAllaCarta(connection, carta, puntiTotali.doubleValue());
            inserisciMovimenti(connection, carta, scontrino);

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static String estraiCartaCasuale(Connection connection) throws SQLException {
        String query = "SELECT card_number FROM carte ORDER BY RANDOM() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getString("card_number") : null;
        }
    }

    private static List<Transazione> creaScontrinoCasuale(Connection connection) throws SQLException, ParseException {
        List<Transazione> scontrino = new ArrayList<>();
        Random random = new Random();
        int numTransazioni = random.nextInt(10) + 1;

        Date dataScontrino = Carte.generateRandomDateTime("2017-05-05 00:00:00", "2019-05-05 23:59:59");

        for (int i = 0; i < numTransazioni; i++) {
            String prodotto = estraiProdottoCasuale(connection);
            int quantita = random.nextInt(2) + 1;
            double prezzo = calcolaPrezzoProdotto(connection, prodotto);

            double punti = calcolaPuntiProdotto(prezzo, quantita);
            scontrino.add(new Transazione(prodotto, quantita, prezzo, punti, dataScontrino));
        }

        return scontrino;
    }

    private static String estraiProdottoCasuale(Connection connection) throws SQLException {
        String query = "SELECT description FROM prodotto ORDER BY RANDOM() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? resultSet.getString("description") : null;
        }
    }

    private static double calcolaPrezzoProdotto(Connection connection, String prodotto) throws SQLException {
        String query = "SELECT price FROM prodotto WHERE description = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, prodotto);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getDouble("price") : 0.0;
            }
        }
    }

    private static void aggiungiPuntiAllaCarta(Connection connection, String carta, double punti) throws SQLException {
        double roundedPunti = Math.round(punti * 1000.0) / 1000.0;
        String updateQuery = "UPDATE carte SET card_points = card_points + ? WHERE card_number = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setDouble(1, roundedPunti);
            updateStatement.setString(2, carta);
            updateStatement.executeUpdate();
        }
    }

    private static double calcolaPuntiProdotto(double prezzo, int quantita) {
        BigDecimal prezzoDecimal = new BigDecimal(prezzo);
        BigDecimal quantitaDecimal = new BigDecimal(quantita);
        BigDecimal punti = prezzoDecimal.multiply(quantitaDecimal).multiply(new BigDecimal("0.1"));
        return punti.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private static BigDecimal stampaScontrino(String carta, List<Transazione> scontrino) {
        BigDecimal totaleCosto = BigDecimal.ZERO;
        BigDecimal totalePunti = BigDecimal.ZERO;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("Carta: " + carta);
        System.out.println("Scontrino:");
        for (Transazione transazione : scontrino) {
            double importoTransazione = transazione.getQuantita() * transazione.getPrezzo();
            totaleCosto = totaleCosto.add(BigDecimal.valueOf(importoTransazione));
            totalePunti = totalePunti.add(BigDecimal.valueOf(transazione.getPunti()));

            String dataFormattata = dateFormat.format(transazione.getDataCreazione());
            System.out.println(
                    transazione.getProdotto() + " - Quantità: " + transazione.getQuantita() +
                            " - Prezzo: " + transazione.getPrezzo() +
                            " - Punti: " + transazione.getPunti() +
                            " - Data e Orario: " + dataFormattata
            );
        }

        return totaleCosto;
    }

    private static BigDecimal calcolaTotalePunti(List<Transazione> scontrino) {
        BigDecimal totalePunti = BigDecimal.ZERO;
        for (Transazione transazione : scontrino) {
            totalePunti = totalePunti.add(BigDecimal.valueOf(transazione.getPunti()));
        }
        return totalePunti.setScale(3, RoundingMode.HALF_UP);
    }

    private static void inserisciMovimenti(Connection connection, String carta, List<Transazione> scontrino) throws SQLException {
        BigDecimal totaleScontrino = calcolaTotaleScontrino(scontrino);
        String tipoMovimento = "acquisto";

        String query = "INSERT INTO movimenti (carta, prodotto, quantita, importo, punti, data, totale, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Transazione transazione : scontrino) {
                statement.setString(1, carta);
                statement.setString(2, transazione.getProdotto());
                statement.setInt(3, transazione.getQuantita());
                statement.setDouble(4, transazione.getPrezzo());
                statement.setDouble(5, transazione.getPunti());
                statement.setTimestamp(6, Timestamp.valueOf(dateFormat.format(transazione.getDataCreazione())));
                statement.setBigDecimal(7, totaleScontrino);
                statement.setString(8, tipoMovimento);

                statement.executeUpdate();
            }
        }
    }

    private static BigDecimal calcolaTotaleScontrino(List<Transazione> scontrino) {
        BigDecimal totaleScontrino = BigDecimal.ZERO;
        for (Transazione transazione : scontrino) {
            double importoTransazione = transazione.getQuantita() * transazione.getPrezzo();
            totaleScontrino = totaleScontrino.add(BigDecimal.valueOf(importoTransazione));
        }
        return totaleScontrino.setScale(3, RoundingMode.HALF_UP);
    }

    private static String formatDecimal(BigDecimal decimal) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return decimalFormat.format(decimal);
    }
}
