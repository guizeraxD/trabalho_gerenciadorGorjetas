package com.example.gorjetas.entities;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class Caixa {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    private String dataFormatada = sdf.format(data);

    private String idCaixa;
    private String situacao;

    public Caixa(){
    }

    public Caixa(String idCaixa, String situacao) {
        this.idCaixa = idCaixa;
        this.situacao = situacao;
    }

    public String getIdCaixa() {
        return idCaixa;
    }

    public void setIdCaixa(String idCaixa) {
        this.idCaixa = idCaixa;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getData() { return dataFormatada; }

    public void setData(String data) { this.dataFormatada = data; }

}
