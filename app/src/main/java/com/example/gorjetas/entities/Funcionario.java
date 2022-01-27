package com.example.gorjetas.entities;

public class Funcionario {
    private String idFuncionario;
    private String nome;
    private String cpf;

    public Funcionario(){

    }

    public Funcionario(String idFuncionario, String nome, String cpf) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return nome;
    }
}
