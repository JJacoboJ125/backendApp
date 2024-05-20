/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

import com.sun.istack.NotNull;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USER
 */
@Entity
public class Vehiculos {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "create_at", updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;

    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Calendar updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario dueno;
    
    @OneToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    private String placa;

    private String marca;

    private String modelo;

    private String capacidadCar;

    private String tipoCarroceria;

    public Vehiculos() {
    }
    

    public Vehiculos(String placa, String marca, String modelo, String capacidadCar, String tipoCarroceria, Usuario dueno) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadCar = capacidadCar;
        this.tipoCarroceria = tipoCarroceria;
        this.dueno = dueno;
    }

    @PreUpdate
    private void updateTimestamp() {
        this.updatedAt = Calendar.getInstance();
    }

    @PrePersist
    private void creationTimestamp() {
        this.createdAt = this.updatedAt = Calendar.getInstance();
    }

    public Usuario getDueno() {
        return dueno;
    }

    public void setDueno(Usuario dueno) {
        this.dueno = dueno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCapacidadCar() {
        return capacidadCar;
    }

    public void setCapacidadCar(String capacidadCar) {
        this.capacidadCar = capacidadCar;
    }

    public String getTipoCarroceria() {
        return tipoCarroceria;
    }

    public void setTipoCarroceria(String tipoCarroceria) {
        this.tipoCarroceria = tipoCarroceria;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }
    
    

}
