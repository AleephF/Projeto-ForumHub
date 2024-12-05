package com.forumhub.forum_hub_aleph.service;

import com.forumhub.forum_hub_aleph.repository.TicketRepository;
import com.forumhub.forum_hub_aleph.ticket.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    @Transactional
    public void cadastrarTicket(DadosTicket dados) {
        if (repository.existsByTitulo(dados.titulo())) {
            throw new RuntimeException("Já existe um ticket com esse título: " + dados.titulo());
        }
        if (repository.existsByMensagem(dados.mensagem())) {
            throw new RuntimeException("Já existe um ticket com essa mensagem: " + dados.mensagem());
        }
        var ticket = new Ticket(dados);
        repository.save(ticket);
    }

    public Page<ListarDadosTicket> listarTodosTickets(Pageable pagina) {
        return repository.findByStatusTopicoTrue(pagina).map(ListarDadosTicket::new);
    }

    public ListarDadosTicket listarPorId(Long id) {
        var ticket = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket não encontrado!"));

        return new ListarDadosTicket(ticket);
    }

    @Transactional
    public ListarDadosTicket atualizarTicket(Long id, AtualizarDadosTicket atualizarDados) {
        var ticket = repository.getReferenceById(id);
        ticket.atualizarTicket(atualizarDados);

        return new ListarDadosTicket(ticket);
    }

    @Transactional
    public ListarDadosTicket respostaTicket(Long id, RespostaTicket respostaTicket) {
        var ticket = repository.getReferenceById(id);
        ticket.atualizarTicket(respostaTicket);

        return new ListarDadosTicket(ticket);
    }

    // Exclusão completa
    @Transactional
    public void delete(@PathVariable Long id){
        var deleteTicket = repository.findById(id);

        if (deleteTicket.isPresent()){
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Ticket não encontrado com esse ID!");
        }
    }

    //Exclusão lógica
    @Transactional
    public void deleteLogico(@PathVariable Long id){
        var deleteTicket = repository.getReferenceById(id);
        deleteTicket.deletar();
    }
}
