package com.example.demo.classes;

import java.util.Date;

class Transazione {
    private String prodotto;
    private int quantita;
    private double prezzo;
    private double punti;
    private Date dataCreazione;

    public Transazione(String prodotto, int quantita, double prezzo, double punti, Date dataCreazione) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
        this.punti = punti;
        this.dataCreazione = dataCreazione;
    }

    public String getProdotto() {
        return prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public double getPunti() {
        return punti;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }
}
