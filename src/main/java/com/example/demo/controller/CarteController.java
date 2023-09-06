package com.example.demo.controller;

import com.example.demo.classes.Carte;
import com.example.demo.repository.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.classes.Carte;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/carte")
public class CarteController {

    @Autowired
    private CarteRepository carteRepository;

    @GetMapping("/all")
    public List<Carte> tutteLeCarte() {
        return carteRepository.findAll();
    }

    @PostMapping("/generate")
    public List<Carte> generaCarte() throws ParseException {
        // Genera una data di creazione casuale una sola volta
        Date dataCreazione = Carte.generateRandomDateTime("05/05/2017", "05/05/2019");

        List<Carte> carteCasuali = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Carte carta = creaCartaCasuale(dataCreazione);
            carteRepository.save(carta); // Salva la carta nel database direttamente
            carteCasuali.add(carta);
        }

        return carteCasuali;
    }

    private Carte creaCartaCasuale(Date dataCreazione) throws ParseException {
        Carte carta = new Carte();

        // Genera un numero di carta casuale con un trattino ogni 4 cifre
        carta.setCardNumber(Carte.generateRandomCardNumber());

        // Imposta la data di creazione con quella passata come parametro
        carta.setCreationDate(dataCreazione);

        // Imposta l'activation_date e l'expiration_date a "0000-00-00"
        carta.setActivationDate(null);
        carta.setExpirationDate(null);

        // Imposta lo status a 0
        carta.setStatus(0);

        return carta;
    }
}
