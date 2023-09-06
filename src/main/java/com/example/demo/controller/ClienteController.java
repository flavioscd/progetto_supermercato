package com.example.demo.controller;

import com.example.demo.classes.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public Cliente creaCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @GetMapping("/all")
    public List<Cliente> elencoClienti() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato con ID: " + id));
    }

    @PutMapping("/{id}")
    public Cliente aggiornaCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato con ID: " + id));

        existingCliente.setCodiceCliente(cliente.getCodiceCliente());
        existingCliente.setFirstName(cliente.getFirstName());
        existingCliente.setLastName(cliente.getLastName());
        existingCliente.setEmail(cliente.getEmail());
        existingCliente.setCittaCliente(cliente.getCittaCliente());

        return clienteRepository.save(existingCliente);
    }

    @DeleteMapping("/{id}")
    public void eliminaCliente(@PathVariable Long id) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato con ID: " + id));

        clienteRepository.delete(existingCliente);
    }
}
