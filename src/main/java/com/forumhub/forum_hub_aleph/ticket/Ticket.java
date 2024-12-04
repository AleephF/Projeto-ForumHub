package com.forumhub.forum_hub_aleph.ticket;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "Ticket")
@Table(name = "tickets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    @Column(name = "data_de_criacao", insertable = false, updatable = false)
    private LocalDate dataDeCriacao;
    private boolean statusTopico;
    private String autor;
    @Enumerated(EnumType.STRING)
    private CursosTicket curso;
    private String resposta;

    public Ticket(DadosTicket dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.statusTopico = true;
        this.autor = dados.autor();
        this.curso = dados.curso();
    }

    public void atualizarTicket(AtualizarDadosTicket dadosTicket){
        if (dadosTicket.titulo() != null){
            this.titulo = dadosTicket.titulo();
        }
        if (dadosTicket.mensagem() != null) {
            this.mensagem = dadosTicket.mensagem();
        }
        if (dadosTicket.autor() != null){
            this.autor = dadosTicket.autor();
        }
        if (dadosTicket.curso() != null){
            this.curso = dadosTicket.curso();
        }
    }

    public void atualizarTicket(@Valid RespostaTicket respostaTicket) {
        if (respostaTicket.titulo() != null){
            this.titulo = respostaTicket.titulo();
        }
        if (respostaTicket.mensagem() != null) {
            this.mensagem = respostaTicket.mensagem();
        }
        if (respostaTicket.autor() != null){
            this.autor = respostaTicket.autor();
        }
        if (respostaTicket.curso() != null){
            this.curso = respostaTicket.curso();
        }
        if (respostaTicket.resposta() != null){
            this.resposta = respostaTicket.resposta();
        }
    }

    public void deletar(){
        this.statusTopico = false;
    }
}
