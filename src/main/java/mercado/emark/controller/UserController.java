package mercado.emark.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import mercado.emark.model.Cliente;
import mercado.emark.model.Funcionario;
import mercado.emark.service.ClienteService;
import mercado.emark.service.FuncionarioService;


import java.util.Collection;



@RequestMapping("/users")
@RestController
public class UserController {
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    public UserController(ClienteService clienteService, FuncionarioService funcionarioService) {
        this.clienteService = clienteService;
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object currentUser = authentication.getPrincipal();
        
        if (currentUser instanceof Cliente) {
            return ResponseEntity.ok((Cliente) currentUser);
        } else if (currentUser instanceof Funcionario) {
            return ResponseEntity.ok((Funcionario) currentUser);
        }
        
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/clientes")
    public ResponseEntity<Collection<Cliente>> allClientes() {
        Collection<Cliente> clientes = clienteService.obterLista();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<Collection<Funcionario>> allFuncionarios() {
        Collection<Funcionario> funcionarios = funcionarioService.obterLista();
        return ResponseEntity.ok(funcionarios);
    }
}
