package com.example.demo.classes;

import jakarta.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class Carte {

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private int status;
    private int cardPoints;
    private Date creationDate;
    private Date activationDate;
    private Date expirationDate;

    public Carte() {
    }

    public Carte(String cardNumber, Date creationDate, Date activationDate, Date expirationDate, int status, int cardPoints) {
        this.cardNumber = cardNumber;
        this.status = status;
        this.cardPoints = cardPoints;
        this.creationDate = creationDate;
        this.activationDate = activationDate;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCardPoints() {
        return cardPoints;
    }

    public void setCardPoints(int cardPoints) {
        this.cardPoints = cardPoints;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public static String generateRandomCardNumber() {
        Random rand = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if (i > 0 && i % 4 == 0) {
                cardNumber.append("-");
            }
            cardNumber.append(rand.nextInt(10));
        }
        return cardNumber.toString();
    }

    public static Date generateRandomDateTime(String startDateStr, String endDateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);

        long randomMillisSinceEpoch = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
        return new Date(randomMillisSinceEpoch);
    }
}
