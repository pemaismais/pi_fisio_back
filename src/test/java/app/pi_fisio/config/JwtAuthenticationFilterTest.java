package app.pi_fisio.config;

import app.pi_fisio.entity.User;
import app.pi_fisio.repository.UserRepository;
import app.pi_fisio.service.JwtService;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class jwtAuthenticationFilterTest {

        @InjectMocks
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Mock
        private UserRepository userRepository;

        @Mock
        private HttpServletRequest request;

        @Mock
        private HttpServletResponse response;

        @Mock
        private FilterChain filterChain;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void AutenticacaoNull() throws ServletException, IOException {
        //aqui vai simular se o token é nulo
            when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
//o do filter internal vai ser responsável por  processar a solicitação e ver se o user esta autenticado <(^^)>
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//verifica se a autenticação é nula
            assert(SecurityContextHolder.getContext().getAuthentication() == null);
            //confirma que a solicitação passou pra adiante
            verify(filterChain).doFilter(request, response);

        }

    }


