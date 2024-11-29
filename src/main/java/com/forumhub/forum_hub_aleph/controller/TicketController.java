package com.forumhub.forum_hub_aleph.controller;

import com.forumhub.forum_hub_aleph.solicitacoes.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TicketController {

    @Autowired
    private TicketRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarTicket(@RequestBody @Valid DadosTicket dados){
        repository.save(new Ticket(dados));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ListarDadosTicket>> listarTickets (@PageableDefault(size = 10, page = 0, sort = {"dataDeCriacao"})Pageable pagina) {
        var paginas = repository.findByStatusTopicoTrue(pagina).map(ListarDadosTicket::new);

        return ResponseEntity.ok(paginas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListarDadosTicket> listarTicketsPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ticket -> ResponseEntity.ok(new ListarDadosTicket(ticket)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Ticket> atualizarTicket(@PathVariable Long id, @RequestBody @Valid AtualizarDadosTicket atualizarDados){
        var dadosTicket = repository.getReferenceById(id);
        dadosTicket.atualizarTicket(atualizarDados);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarTicket(@PathVariable Long id){
        var deleteTicket = repository.findById(id);

        if (deleteTicket.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}