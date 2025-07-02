package mercado.emark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mercado.emark.clients.IEnderecoClient;
import mercado.emark.dto.ClienteDTO;
import mercado.emark.dto.ClienteUpdateDTO;
import mercado.emark.exception.BadRequestException;
import mercado.emark.model.Cliente;
import mercado.emark.model.Endereco;
import mercado.emark.repository.ClienteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private IEnderecoClient enderecoClient;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Cliente cadastrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        mapDtoToEntity(clienteDTO, cliente);
        cliente.setDataRegistro(LocalDateTime.now());
        return clienteRepository.save(cliente);
    }

    public List<Cliente> obterLista() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public void deletarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente atualizarCliente(ClienteUpdateDTO clienteUpdateDTO) {
        return clienteRepository.findById(clienteUpdateDTO.getId())
                .map(cliente -> {
                    mapUpdateDtoToEntity(clienteUpdateDTO, cliente);
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    private void mapDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setNome(dto.getNome());
        entity.setSobrenome(dto.getSobrenome());
        entity.setCpf(dto.getCpf());
        entity.setSexo(dto.getSexo());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setCelular(dto.getCelular());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            entity.setPassword(encodedPassword);
        } else {
            throw new RuntimeException("Senha não pode ser vazia");
        }

        entity.setDataNascimento(dto.getDataNascimento());

        Endereco enderecoViaCep = enderecoClient.buscaCep(dto.getCep());

        if (enderecoViaCep == null || enderecoViaCep.getCep() == null) {
            throw new RuntimeException("CEP inválido ou não encontrado.");
        }

        Endereco endereco = new Endereco();
        endereco.setCep(enderecoViaCep.getCep());
        endereco.setLogradouro(enderecoViaCep.getLogradouro());
        endereco.setBairro(enderecoViaCep.getBairro());
        endereco.setLocalidade(enderecoViaCep.getLocalidade());
        endereco.setUf(enderecoViaCep.getUf());

        endereco = enderecoService.salvar(endereco);

        entity.setEndereco(endereco);

    }

    private void mapUpdateDtoToEntity(ClienteUpdateDTO dto, Cliente entity) {

        if (dto.getNome() != null)
            entity.setNome(dto.getNome());
        if (dto.getSobrenome() != null)
            entity.setSobrenome(dto.getSobrenome());
        if (dto.getCpf() != null)
            entity.setCpf(dto.getCpf());
        if (dto.getSexo() != null)
            entity.setSexo(dto.getSexo());
        if (dto.getEmail() != null)
            entity.setEmail(dto.getEmail());
        if (dto.getTelefone() != null)
            entity.setTelefone(dto.getTelefone());
        if (dto.getCelular() != null)
            entity.setCelular(dto.getCelular());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            entity.setPassword(encodedPassword);
        }

        if (dto.getDataNascimento() != null)
            entity.setDataNascimento(dto.getDataNascimento());

        // Atualizar endereço
        if (dto.getCep() != null) {

            // Consulta no ViaCEP
            Endereco enderecoViaCep = enderecoClient.buscaCep(dto.getCep());

            if (enderecoViaCep == null || enderecoViaCep.getCep() == null) {
                throw new BadRequestException("CEP inválido ou não encontrado.");
            }

            Endereco endereco = entity.getEndereco();
            
            if (endereco == null) {
                endereco = new Endereco();
            }

            endereco.setCep(enderecoViaCep.getCep());
            endereco.setLogradouro(enderecoViaCep.getLogradouro());
            endereco.setBairro(enderecoViaCep.getBairro());
            endereco.setLocalidade(enderecoViaCep.getLocalidade());
            endereco.setUf(enderecoViaCep.getUf());

           
            endereco.setNumero(dto.getNumero());
            endereco.setComplemento(dto.getComplemento());

            endereco = enderecoService.salvar(endereco);
            entity.setEndereco(endereco);

            
            entity.setNumero(dto.getNumero());
            entity.setComplemento(dto.getComplemento());
        }
    }

}
