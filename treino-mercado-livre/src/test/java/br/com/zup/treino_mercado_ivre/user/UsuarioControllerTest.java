package br.com.zup.treino_mercado_ivre.user;

import br.com.zup.treino_mercado_ivre.usuario.NovoUsuarioRequest;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import br.com.zup.treino_mercado_ivre.usuario.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class UsuarioControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper jsonMapper;

    @MockBean
    UsuarioRepository usuarioRepository;

    @Captor
    ArgumentCaptor<Usuario> argumentCaptor;

    @Test
    void deveCriarUmUsuarioComLoginESenha() throws Exception {
        NovoUsuarioRequest novoUsuarioRequest = new NovoUsuarioRequest("email@email.com", "123456");

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(novoUsuarioRequest)))
                .andExpect(status().isOk());
        //Usa assim sem consultar ao banco
        Mockito.verify(usuarioRepository).save(argumentCaptor.capture());

        Usuario usuarioSalvo = argumentCaptor.getValue();
        assertEquals("email@email.com", usuarioSalvo.getUsername());
        //usando repository para consultar no banco:
//        Optional<Usuario> usuarioCriado = usuarioRepository.findByLogin("email@email.com");
//        assertTrue(usuarioCriado.isPresent());
    }

    @Test
    void nãoDeveCriarUmUsuarioComLoginNulo() throws Exception {
        NovoUsuarioRequest novoUsuarioRequest = new NovoUsuarioRequest(null, "123456");

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(novoUsuarioRequest)))
                .andExpect(status().isBadRequest());
    }
}
