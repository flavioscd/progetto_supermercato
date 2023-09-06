package com.example.demo.repository;

import com.example.demo.classes.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface CarteRepository extends JpaRepository<Carte, Long> {


}
