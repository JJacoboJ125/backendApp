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
public class ConductorDTO {
    
    private long idvehiculo;
    
    private String nombre;
    
    private String correo;
    
    private String telefono;
    
    private String pass;

    public ConductorDTO() {
    }

    public long getIdvehiculo() {
        return idvehiculo;
    }

    public void setIdvehiculo(long idvehiculo) {
        this.idvehiculo = idvehiculo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    
    
}
