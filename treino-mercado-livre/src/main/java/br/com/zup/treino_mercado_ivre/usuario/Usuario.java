package br.com.zup.treino_mercado_ivre.usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String login;

    @NotBlank
    @Size(min = 6)
    @Column(nullable = false)
    private String senha;

    @PastOrPresent
    private LocalDateTime instanteCadastro;

    public Usuario(@NotBlank @Email String login, @Valid @NotNull SenhaLimpa senhaLimpa) {
        Assert.isTrue(StringUtils.hasLength(login),"Email náo pode ser em branco");
        Assert.notNull(senhaLimpa, "O objeto senha limpa não pode ser nulo!");
        this.login = login;
//        Salvo a senha no campo usando bCrypt e gero um sal para cada senha, logo se a senha for igual em dois usuários o hash será diferente
        this.senha = senhaLimpa.hash() ;
        this.instanteCadastro = LocalDateTime.now();
    }

    @Deprecated
    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(login, usuario.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
