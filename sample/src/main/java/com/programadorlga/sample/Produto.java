package com.programadorlga.sample;

import com.programadorlga.materialdroid.annotation.form.DateTimeFormat;
import com.programadorlga.materialdroid.annotation.model.VerboseName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Produto extends RealmObject{

    @PrimaryKey
    private long id;
    private String nome;
    @DateTimeFormat(date = "dd/MM/yyyy", time = "HH:mm")
    @VerboseName(singular = R.string.data_de_fabricacao)
    private Date dataFabricacao;
    private float valor;
    private int quantidade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(Date dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
