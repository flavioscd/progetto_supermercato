package com.example.demo.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Entity
public class Movimenti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date data;
    private String carta;
    private String cliente;
    private String prodotto;
    private double importo;
    private int quantita;
    private double totale;
    private String tipo;
    private int punti;
    private double sconto;

    public Movimenti(Long id, Date data, String carta, String cliente, String prodotto, double importo, int quantita, double totale, String tipo, int punti, double sconto) {
        this.id = id;
        this.data = data;
        this.carta = carta;
        this.cliente = cliente;
        this.prodotto = prodotto;
        this.importo = importo;
        this.quantita = quantita;
        this.totale = totale;
        this.tipo = tipo;
        this.punti = punti;
        this.sconto = sconto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProdotto() {
        return prodotto;
    }

    public void setProdotto(String prodotto) {
        this.prodotto = prodotto;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public double getSconto() {
        return sconto;
    }

    public void setSconto(double sconto) {
        this.sconto = sconto;
    }

    public static void inserisciMovimenti(Connection connection, String carta, List<Transazione> scontrino) {
        String insertQuery = "INSERT INTO movimenti (carta, data, importo, prodotto, punti, quantita, tipo, totale) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            for (Transazione transazione : scontrino) {
                java.sql.Date data = new java.sql.Date(System.currentTimeMillis()); // Data corrente
                insertStatement.setString(1, carta);
                insertStatement.setDate(2, data);
                insertStatement.setBigDecimal(3, BigDecimal.valueOf(transazione.getPrezzo()));
                insertStatement.setString(4, transazione.getProdotto());
                insertStatement.setBigDecimal(5, BigDecimal.valueOf(transazione.getPunti()));
                insertStatement.setInt(6, transazione.getQuantita());
                insertStatement.setString(7, "acquisto"); // Puoi personalizzare il tipo di transazione
                double totale = transazione.getQuantita() * transazione.getPrezzo();
                insertStatement.setBigDecimal(8, BigDecimal.valueOf(totale));
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
