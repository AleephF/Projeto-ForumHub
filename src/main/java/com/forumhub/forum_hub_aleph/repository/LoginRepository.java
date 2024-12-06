package com.forumhub.forum_hub_aleph.repository;

import com.forumhub.forum_hub_aleph.infra.security.usuario.UsuarioLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginRepository extends JpaRepository<UsuarioLogin, Long> {
    UserDetails findByLogin(String username);

    boolean existsByLogin(String login);
}
