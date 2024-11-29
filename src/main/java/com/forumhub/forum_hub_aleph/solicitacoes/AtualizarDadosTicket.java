package com.forumhub.forum_hub_aleph.solicitacoes;

import jakarta.validation.constraints.NotBlank;

public record AtualizarDadosTicket(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotBlank
        String autor,
        @NotBlank
        String curso) {
}
