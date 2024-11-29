package com.forumhub.forum_hub_aleph.solicitacoes;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RespostaTicket(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        LocalDate dataDeCriacao,
        boolean statusTopico,
        @NotBlank
        String autor,
        @NotBlank
        String curso,
        @NotBlank
        String resposta) {
}
