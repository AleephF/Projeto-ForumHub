package com.forumhub.forum_hub_aleph.ticket;

import java.time.LocalDate;

public record ListarDadosTicket(
        String titulo,
        String mensagem,
        LocalDate dataDeCriacao,
        Boolean statusTopico,
        String autor,
        CursosTicket curso,
        String resposta) {

    public ListarDadosTicket(Ticket tickets) {
        this(tickets.getTitulo(), tickets.getMensagem(), tickets.getDataDeCriacao(), tickets.isStatusTopico(), tickets.getAutor(), tickets.getCurso(), tickets.getResposta());
    }
}
