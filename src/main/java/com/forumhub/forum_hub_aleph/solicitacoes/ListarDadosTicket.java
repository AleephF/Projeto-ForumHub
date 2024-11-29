package com.forumhub.forum_hub_aleph.solicitacoes;

import java.time.LocalDate;

public record ListarDadosTicket(
        String titulo,
        String mensagem,
        LocalDate dataDeCriacao,
        Boolean statusTopico,
        String autor,
        String curso,
        String resposta) {

    public ListarDadosTicket(Ticket tickets) {
        this(tickets.getTitulo(), tickets.getMensagem(), tickets.getDataDeCriacao(), tickets.isStatusTopico(), tickets.getAutor(), tickets.getCurso(), tickets.getResposta());
    }
}
