package org.example.trello2.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_lists")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;
    private String descricao;
    private String status = "Nulo";
    private String prioridade;
    private String dataLimite;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL)
    private List<Card> cards; // Relacionamento com Card

    // Getters e Setter

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(String dataLimite) {
        this.dataLimite = dataLimite;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        card.setTaskList(this); // Define a TaskList associada ao Card
        this.cards.add(card); // Adiciona o Card à lista
    }

    public void removeCard(Card card) {
        card.setTaskList(null); // Remove a referência da TaskList no Card
        this.cards.remove(card); // Remove o Card da lista
    }
}
