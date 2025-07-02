package mercado.emark.service;

import jakarta.validation.Valid;
import mercado.emark.clients.IEnderecoClient;
import mercado.emark.dto.AuthenticationDTO;
import mercado.emark.dto.ClienteJwtCadastroResponseDTO;
import mercado.emark.dto.FuncionarioJwtCadastroResponseDTO;
import mercado.emark.dto.LoginResponseDTO;
import mercado.emark.dto.RegisterClienteDTO;
import mercado.emark.dto.RegisterFuncionarioDTO;
import mercado.emark.enums.UserRole;
import mercado.emark.model.Cliente;
import mercado.emark.model.Endereco;
import mercado.emark.model.Funcionario;
import mercado.emark.repository.ClienteRepository;
import mercado.emark.repository.EnderecoRepository;
import mercado.emark.repository.FuncionarioRepository;
import mercado.emark.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthorizationService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IEnderecoClient enderecoClient;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> login(@Valid AuthenticationDTO data) {
        var loginToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication authentication = authenticationManager.authenticate(loginToken);
    
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = tokenService.generateToken(principal);
    
        String userType = "DESCONHECIDO";
        if (principal instanceof Cliente) {
            userType = "CLIENTE";
        } else if (principal instanceof Funcionario) {
            userType = "FUNCIONARIO";
        }
    
        // Log no terminal
        System.out.println("{");
        System.out.println("  \"token\": \"" + token + "\",");
        System.out.println("  \"userType\": \"" + userType + "\"");
        System.out.println("}");
    
        return ResponseEntity.ok(new LoginResponseDTO(token, userType));
    }
    

    public ResponseEntity<Object> registrarCliente(@Valid RegisterClienteDTO dto) {
        if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já registrado");
        }

        Endereco endereco = enderecoClient.buscaCep(dto.getCep());
        if (endereco == null || endereco.getCep() == null) {
            return ResponseEntity.badRequest().body("CEP inválido ou não encontrado");
        }
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());

        endereco = enderecoRepository.save(endereco);

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setSobrenome(dto.getSobrenome());
        cliente.setCpf(dto.getCpf());
        cliente.setSexo(dto.getSexo());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCelular(dto.getCelular());
        cliente.setNumero(dto.getNumero());
        cliente.setComplemento(dto.getComplemento());
        if (dto.getDataNascimento() != null) {
            cliente.setDataNascimento(new java.sql.Date(dto.getDataNascimento().getTime()));
        }

        cliente.setPassword(passwordEncoder.encode(dto.getPassword()));
        cliente.setEndereco(endereco);
        cliente.setDataRegistro(LocalDateTime.now());
        cliente.setRole(UserRole.USER);

        clienteRepository.save(cliente);

        String token = tokenService.generateToken(cliente);
        return ResponseEntity.status(201)
        .body(new ClienteJwtCadastroResponseDTO(token, "CLIENTE", cliente.getId()));

    }

    public ResponseEntity<Object> registrarFuncionario(@Valid RegisterFuncionarioDTO dto) {
        if (funcionarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já registrado");
        }

        Endereco endereco = enderecoClient.buscaCep(dto.getCep());
        if (endereco == null || endereco.getCep() == null) {
            return ResponseEntity.badRequest().body("CEP inválido ou não encontrado");
        }
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());

        endereco = enderecoRepository.save(endereco);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setSobrenome(dto.getSobrenome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setRg(dto.getRg());
        funcionario.setSexo(dto.getSexo());
        funcionario.setEmail(dto.getEmail());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setCelular(dto.getCelular());
        funcionario.setNumero(dto.getNumero());
        funcionario.setComplemento(dto.getComplemento());
        if (dto.getDataNascimento() != null) {
            funcionario.setDataNascimento(new java.sql.Date(dto.getDataNascimento().getTime()));
        }

        funcionario.setCargo(dto.getCargo());
        funcionario.setSetor(dto.getSetor());
        funcionario.setPassword(passwordEncoder.encode(dto.getPassword()));
        funcionario.setEndereco(endereco);
        funcionario.setDataRegistro(LocalDateTime.now());

        funcionario.setRole(dto.getRole() != null ? dto.getRole() : UserRole.ADMIN);

        funcionarioRepository.save(funcionario);

        String token = tokenService.generateToken(funcionario);
        return ResponseEntity.status(201)
        .body(new FuncionarioJwtCadastroResponseDTO(token, "FUNCIONARIO", funcionario.getId()));
    }
}
