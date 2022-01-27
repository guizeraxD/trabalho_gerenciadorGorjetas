package com.example.gorjetas.entities;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gorjeta {

    private String idGorjeta;
    private String idFuncionario;
    private Double valor;
    private String idCaixa;

    public Gorjeta(){
    }

    public Gorjeta(String idGorjeta, String idFuncionario, Double valor, String idCaixa) {
        this.idGorjeta = idGorjeta;
        this.idFuncionario = idFuncionario;
        this.valor = valor;
        this.idCaixa = idCaixa;
    }

    public String getIdGorjeta() {
        return idGorjeta;
    }

    public void setIdGorjeta(String idGorjeta) {
        this.idGorjeta = idGorjeta;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getIdCaixa() {
        return idCaixa;
    }

    public void setIdCaixa(String idCaixa) {
        this.idCaixa = idCaixa;
    }

}
