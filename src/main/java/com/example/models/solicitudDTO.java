/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author USER
 */
public class solicitudDTO {

    private String estado; 
    
    private long idVe;

    
    public solicitudDTO() {
    }

    public long getIdVe() {
        return idVe;
    }

    public void setIdVe(long idVe) {
        this.idVe = idVe;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
