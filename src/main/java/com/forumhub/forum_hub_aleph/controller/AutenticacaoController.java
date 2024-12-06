package com.forumhub.forum_hub_aleph.controller;


import com.forumhub.forum_hub_aleph.infra.security.authentication.AutenticarAcesso;
import com.forumhub.forum_hub_aleph.infra.security.authentication.DadosTokenJWT;
import com.forumhub.forum_hub_aleph.infra.security.authentication.TokenService;
import com.forumhub.forum_hub_aleph.infra.security.usuario.UsuarioLogin;
import com.forumhub.forum_hub_aleph.infra.security.usuario.UsuarioTicket;
import com.forumhub.forum_hub_aleph.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager autenticacao;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarLogin(@RequestBody @Valid UsuarioTicket dados, UriComponentsBuilder uriComponentsBuilder) {
        try {
            usuarioService.cadastrarLogin(dados);
            var usuarioLogin = new UsuarioLogin(dados.login(), dados.senha());

            var uri = uriComponentsBuilder.path("/cadastrar").buildAndExpand(usuarioLogin.getId()).toUri();
            return ResponseEntity.created(uri).body(new UsuarioTicket(usuarioLogin));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticarAcesso autenticarAcesso) {
        try {
            DadosTokenJWT tokenJWT = usuarioService.efetuarLogin(autenticarAcesso);
            return ResponseEntity.ok(tokenJWT);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}