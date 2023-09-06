package com.example.demo.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clienti")
public class Cliente {

    @OneToMany(mappedBy = "cliente")
    private List<Carte> carte;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codice_cliente")
    private String codiceCliente;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "citta_cliente")
    private String cittaCliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodiceCliente() {
        return codiceCliente;
    }

    public void setCodiceCliente(String codiceCliente) {
        this.codiceCliente = codiceCliente;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCittaCliente() {
        return cittaCliente;
    }

    public void setCittaCliente(String cittaCliente) {
        this.cittaCliente = cittaCliente;
    }
}
