package com.forumhub.forum_hub_aleph.controller;

import com.forumhub.forum_hub_aleph.repository.TicketRepository;
import com.forumhub.forum_hub_aleph.ticket.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping("/topicos")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class TicketController {

    @Autowired
    private TicketRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarTicket(@RequestBody @Valid DadosTicket dados, UriComponentsBuilder uriComponentsBuilder) {
        if (repository.existsByTitulo(dados.titulo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um ticket com o título: " + dados.titulo());
        } if (repository.existsByMensagem(dados.mensagem())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um ticket com essa mensagem: " + dados.mensagem());
        }
        var ticket = new Ticket(dados);
        repository.save(ticket);

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(ticket.getId()).toUri();
        return ResponseEntity.created(uri).body(new ListarDadosTicket(ticket));
    }


    @GetMapping
    public ResponseEntity<Page<ListarDadosTicket>> listarTickets(@PageableDefault(size = 10, page = 0, sort = {"dataDeCriacao"}) Pageable pagina) {
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
    public ResponseEntity atualizarTicket(@PathVariable Long id, @RequestBody @Valid AtualizarDadosTicket atualizarDados) {
        var dadosTicket = repository.getReferenceById(id);
        dadosTicket.atualizarTicket(atualizarDados);

        return ResponseEntity.ok(new ListarDadosTicket(dadosTicket));
    }

    @PutMapping("/{id}/respostas")
    @Transactional
    public ResponseEntity respostaTicket(@PathVariable Long id, @RequestBody @Valid RespostaTicket respostaTicket) {
        var resposta = repository.getReferenceById(id);

        resposta.atualizarTicket(respostaTicket);
        return ResponseEntity.ok(new ListarDadosTicket(resposta));
    }

    // Exclusão completa
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

    //Exclusão lógica
    @DeleteMapping("/{id}/exclusaologica")
    @Transactional
    public ResponseEntity deleteLogicoTicket(@PathVariable Long id) {
        var deleteTicket = repository.getReferenceById(id);
        deleteTicket.deletar();

        return ResponseEntity.noContent().build();
    }
}
