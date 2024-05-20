/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

/**
 *
 * @author USER
 */
public class SingletonLogin {
    private static SingletonLogin instance;
    private String uid;
    private int tipoUs;

    private SingletonLogin() {
        // Constructor privado para evitar la creación de instancias externas
    }

    public static synchronized SingletonLogin getInstance() {
        if (instance == null) {
            instance = new SingletonLogin();
        }
        return instance;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public int getTipoUs() {
        return tipoUs;
    }

    public void setTipoUs(int tipoUs) {
        this.tipoUs = tipoUs;
    }
    
    
    
}
