package com.forumhub.forum_hub_aleph.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespostaTicket(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotBlank
        String autor,
        @NotNull
        CursosTicket curso,
        @NotBlank
        String resposta) {
}
