package com.forumhub.forum_hub_aleph.solicitacoes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findByStatusTopicoTrue(Pageable pagina);
}
