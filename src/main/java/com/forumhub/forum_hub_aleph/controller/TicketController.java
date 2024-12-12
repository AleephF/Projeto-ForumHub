package com.forumhub.forum_hub_aleph.controller;

import com.forumhub.forum_hub_aleph.repository.TicketRepository;
import com.forumhub.forum_hub_aleph.service.TicketService;
import com.forumhub.forum_hub_aleph.ticket.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping("/topicos")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class TicketController {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private TicketService ticketService;

    @PostMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity cadastrarTicket(@RequestBody @Valid DadosTicket dados, UriComponentsBuilder uriComponentsBuilder) {
        ticketService.cadastrarTicket(dados);
        var ticket = new Ticket(dados);

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(ticket.getId()).toUri();
        return ResponseEntity.created(uri).body(new ListarDadosTicket(ticket));
    }

    @GetMapping
    public ResponseEntity<Page<ListarDadosTicket>> listarTickets(@PageableDefault(size = 10, page = 0, sort = {"dataDeCriacao"}) Pageable pagina) {
        var paginas = ticketService.listarTodosTickets(pagina);
        return ResponseEntity.ok(paginas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListarDadosTicket> listarTicketsPorId(@PathVariable Long id) {
        try {
            var listarDadosTicket = ticketService.listarPorId(id);
            return ResponseEntity.ok(listarDadosTicket);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<ListarDadosTicket> atualizarTicket(@PathVariable Long id, @RequestBody @Valid AtualizarDadosTicket atualizarDados) {
        var dadosAtualizados = ticketService.atualizarTicket(id, atualizarDados);

        return ResponseEntity.ok(dadosAtualizados);
    }

    @PutMapping("/{id}/respostas")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<ListarDadosTicket> respostaTicket(@PathVariable Long id, @RequestBody @Valid RespostaTicket respostaTicket) {
        var ticketAtualizado = ticketService.respostaTicket(id, respostaTicket);

        return ResponseEntity.ok(ticketAtualizado);
    }

    // Exclusão completa
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deletarTicket(@PathVariable Long id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Exclusão lógica
    @DeleteMapping("/{id}/exclusaologica")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity deleteLogicoTicket(@PathVariable Long id) {
        ticketService.deleteLogico(id);

        return ResponseEntity.noContent().build();
    }
}
