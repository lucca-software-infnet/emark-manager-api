package mercado.emark.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mercado.emark.repository.ClienteRepository;
import mercado.emark.repository.FuncionarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        var token = recoverToken(request);
        if (token != null) {
            var email = tokenService.validateToken(token);
            if (email != null) {
                // Tenta encontrar como cliente
                var clienteOpt = clienteRepository.findByEmail(email);
                if (clienteOpt.isPresent()) {
                    var cliente = clienteOpt.get();
                    var authentication = new UsernamePasswordAuthenticationToken(cliente, null, cliente.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    var funcionarioOpt = funcionarioRepository.findByEmail(email);
                    if (funcionarioOpt.isPresent()) {
                        var funcionario = funcionarioOpt.get();
                        var authentication = new UsernamePasswordAuthenticationToken(funcionario, null, funcionario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.replace("Bearer ", "");
    }
}
