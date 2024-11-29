package com.forumhub.forum_hub_aleph.solicitacoes;

import java.time.LocalDate;

public record RespostaTicket(Long id,
                             String mensagem,
                             String topico,
                             LocalDate dataDeCriacao,
                             String autor,
                             String solucao) {
}
