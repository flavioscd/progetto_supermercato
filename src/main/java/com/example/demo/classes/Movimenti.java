package com.example.demo.classes;

import jakarta.persistence.*;
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
    private String prodotto;
    private double importo;
    private int quantita;
    private String tipo;
    private int punti;
    private double totale;

    public Movimenti() {
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

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public static void inserisciMovimenti(Connection connection, String carta, List<Transazione> scontrino) {
        String insertQuery = "INSERT INTO movimenti (carta, data, importo, prodotto, punti, quantita, tipo, totale) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            for (Transazione transazione : scontrino) {
                java.sql.Date data = new java.sql.Date(System.currentTimeMillis()); // Data corrente
                insertStatement.setString(1, carta);
                insertStatement.setDate(2, data);
                BigDecimal prezzo = BigDecimal.valueOf(transazione.getPrezzo());
                insertStatement.setBigDecimal(3, prezzo);
                insertStatement.setString(4, transazione.getProdotto());
                BigDecimal punti = BigDecimal.valueOf(transazione.getPunti());
                insertStatement.setBigDecimal(5, punti);
                insertStatement.setInt(6, transazione.getQuantita());
                insertStatement.setString(7, "acquisto");
                BigDecimal totale = prezzo.multiply(BigDecimal.valueOf(transazione.getQuantita()));
                insertStatement.setBigDecimal(8, totale);
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
