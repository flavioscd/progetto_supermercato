package com.example.demo.controller;

import com.example.demo.classes.Movimenti;
import com.example.demo.repository.MovimentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimenti")
public class MovimentiController {

    private final MovimentiRepository movimentiRepository;

    @Autowired
    public MovimentiController(MovimentiRepository movimentiRepository) {
        this.movimentiRepository = movimentiRepository;
    }

    @GetMapping("/all")
    public List<Movimenti> getAllMovimenti() {
        return movimentiRepository.findAll();
    }

    @GetMapping("/{id}")
    public Movimenti getMovimentiById(@PathVariable Long id) {
        return movimentiRepository.findById(id).orElse(null);
    }

    @PostMapping("/all")
    public Movimenti createMovimenti(@RequestBody Movimenti movimenti) {
        return movimentiRepository.save(movimenti);
    }

    @PutMapping("/{id}")
    public Movimenti updateMovimenti(@PathVariable Long id, @RequestBody Movimenti updatedMovimenti) {
        updatedMovimenti.setId(id);
        return movimentiRepository.save(updatedMovimenti);
    }

    @DeleteMapping("/{id}")
    public void deleteMovimenti(@PathVariable Long id) {
        movimentiRepository.deleteById(id);
    }
}



