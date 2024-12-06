package com.forumhub.forum_hub_aleph.service;

import com.forumhub.forum_hub_aleph.infra.security.authentication.AutenticarAcesso;
import com.forumhub.forum_hub_aleph.infra.security.authentication.DadosTokenJWT;
import com.forumhub.forum_hub_aleph.infra.security.authentication.TokenService;
import com.forumhub.forum_hub_aleph.infra.security.usuario.UsuarioLogin;
import com.forumhub.forum_hub_aleph.infra.security.usuario.UsuarioTicket;
import com.forumhub.forum_hub_aleph.repository.LoginRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioService {

    @Autowired
    private AuthenticationManager autenticacao;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LoginRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void cadastrarLogin(UsuarioTicket dados) {
        if (repository.existsByLogin(dados.login())) {
            System.err.println("Erro: Já existe um usuário com esse login: " + dados.login());
            throw new RuntimeException("Já existe um usuario com esse login: " + dados.login());
        }
        if (dados.senha().isEmpty()){
            System.err.println("Senha não digitada, digite uma senha, por favor!");
            throw new RuntimeException("Senha não digitada, digite uma senha, por favor!");
        }
        if (dados.senha().length() <= 5){
            System.err.println("Senha não permitida! Digite uma senha com 6 ou mais caracteres");
            throw new RuntimeException("Senha não permitida! Digite uma senha com 6 ou mais caracteres");
        }
        String senhaHash = passwordEncoder.encode(dados.senha());
        var usuario = new UsuarioLogin(dados.login(), senhaHash);
        repository.save(usuario);
    }

    public DadosTokenJWT efetuarLogin(@RequestBody @Valid AutenticarAcesso autenticarAcesso){
        try {
            var token = new UsernamePasswordAuthenticationToken(autenticarAcesso.login(), autenticarAcesso.senha());
            var logar = autenticacao.authenticate(token);
            var tokenJWT = tokenService.gerarToken((UsuarioLogin) logar.getPrincipal());

            return new DadosTokenJWT(tokenJWT);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro na autenticação: " + e.getMessage());
        }
    }
}
