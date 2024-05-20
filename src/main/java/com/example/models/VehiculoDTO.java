/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

/**
 *
 * @author USER
 */
public class VehiculoDTO {
    
    private String placa;

    private String marca;

    private String modelo;

    private String capacidadCar;

    private String tipoCarroceria;
    
    private long IdDueno;
    
    private long IdConductor;

    public VehiculoDTO() {
        
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

    public Long getIdDueno() {
        return IdDueno;
    }

    public void setIdDueno(Long IdDueno) {
        this.IdDueno = IdDueno;
    }

    public long getIdConductor() {
        return IdConductor;
    }

    public void setIdConductor(long IdConductor) {
        this.IdConductor = IdConductor;
    }
    
    
    
}
