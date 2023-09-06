package com.example.demo.repository;

import com.example.demo.classes.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {


    @Query(value = "SELECT * FROM prodotto ORDER BY random() LIMIT 1", nativeQuery = true)
    public List<Prodotto> getProdottoCasuale();




}
