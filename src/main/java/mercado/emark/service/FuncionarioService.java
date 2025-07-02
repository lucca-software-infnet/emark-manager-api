package mercado.emark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mercado.emark.clients.IEnderecoClient;
import mercado.emark.dto.FuncionarioDTO;
import mercado.emark.dto.FuncionarioUpdateDTO;
import mercado.emark.exception.BadRequestException;
import mercado.emark.model.Endereco;
import mercado.emark.model.Funcionario;
import mercado.emark.repository.FuncionarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEnderecoClient enderecoClient;

    public Funcionario cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {

        if (funcionarioRepository.existsByCpf(funcionarioDTO.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }
        if (funcionarioRepository.existsByEmail(funcionarioDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Funcionario funcionario = new Funcionario();
        mapDtoToEntity(funcionarioDTO, funcionario);
        funcionario.setDataRegistro(LocalDateTime.now());

        return funcionarioRepository.save(funcionario);
    }

    

    public List<Funcionario> obterLista() {
        return listarFuncionarios();
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarFuncionarioPorId(Integer id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }

    public Funcionario buscarFuncionarioPorCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }

    public void deletarFuncionario(Integer id) {
        funcionarioRepository.deleteById(id);
    }

    public Funcionario atualizarFuncionario(FuncionarioUpdateDTO funcionarioUpdateDTO) {
        return funcionarioRepository.findById(funcionarioUpdateDTO.getId())
                .map(funcionario -> {
                    mapUpdateDtoToEntity(funcionarioUpdateDTO, funcionario);
                    return funcionarioRepository.save(funcionario);
                })
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }

    private void mapDtoToEntity(FuncionarioDTO dto, Funcionario entity) {
        entity.setNome(dto.getNome());
        entity.setSobrenome(dto.getSobrenome());
        entity.setCpf(dto.getCpf());
        entity.setRg(dto.getRg());
        entity.setSexo(dto.getSexo());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setCargo(dto.getCargo());
        entity.setSetor(dto.getSetor());
        entity.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            entity.setPassword(encodedPassword);
        } else {
            throw new RuntimeException("Senha não pode ser vazia");
        }

        entity.setTelefone(dto.getTelefone());
        entity.setCelular(dto.getCelular());


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

    private void mapUpdateDtoToEntity(FuncionarioUpdateDTO dto, Funcionario entity) {
        if (dto.getNome() != null)
            entity.setNome(dto.getNome());
        if (dto.getSobrenome() != null)
            entity.setSobrenome(dto.getSobrenome());
        if (dto.getCpf() != null)
            entity.setCpf(dto.getCpf());
        if (dto.getRg() != null)
            entity.setRg(dto.getRg());
        if (dto.getSexo() != null)
            entity.setSexo(dto.getSexo());
        if (dto.getDataNascimento() != null)
            entity.setDataNascimento(dto.getDataNascimento());
        if (dto.getCargo() != null)
            entity.setCargo(dto.getCargo());
        if (dto.getSetor() != null)
            entity.setSetor(dto.getSetor());
        if (dto.getEmail() != null)
            entity.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            entity.setPassword(encodedPassword);
        }

        if (dto.getTelefone() != null)
            entity.setTelefone(dto.getTelefone());
        if (dto.getCelular() != null)
            entity.setCelular(dto.getCelular());


        if (dto.getCep() != null) {
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
