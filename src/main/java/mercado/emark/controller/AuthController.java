package mercado.emark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mercado.emark.dto.AuthenticationDTO;
import mercado.emark.dto.RegisterClienteDTO;
import mercado.emark.dto.RegisterFuncionarioDTO;
import mercado.emark.service.AuthorizationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        return authorizationService.login(authenticationDTO);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        // Lógica para invalidar o token se necessário
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registrar-cliente")
    public ResponseEntity<Object> registrarCliente(@RequestBody @Valid RegisterClienteDTO dto) {
        try {
            return authorizationService.registrarCliente(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao registrar cliente: " + e.getMessage());
        }
    }

    @PostMapping("/registrar-funcionario")
    public ResponseEntity<Object> registrarFuncionario(@RequestBody 
    @Valid RegisterFuncionarioDTO dto) {
        try {
            return authorizationService.registrarFuncionario(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao registrar funcionário: " + e.getMessage());

        }
    }
}
