package com.example.demo.controller;

import com.example.demo.classes.Prodotto;
import com.example.demo.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
    private final ProdottoRepository prodottoRepository;

    @Autowired
    public ProdottoController(ProdottoRepository prodottoRepository) {
        this.prodottoRepository = prodottoRepository;
    }

    @GetMapping("/all")
    public List<Prodotto> getAllProdotti() {
        return prodottoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Prodotto getProdottoById(@PathVariable Long id) {
        return prodottoRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Prodotto createProdotto(@RequestBody Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }

    @PutMapping("/{id}")
    public Prodotto updateProdotto(@PathVariable Long id, @RequestBody Prodotto prodotto) {
        prodotto.setId(id);
        return prodottoRepository.save(prodotto);
    }

    @DeleteMapping("/{id}")
    public void deleteProdotto(@PathVariable Long id) {
        prodottoRepository.deleteById(id);
    }
}
