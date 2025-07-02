package mercado.emark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mercado.emark.clients.IEnderecoClient;
import mercado.emark.model.Endereco;
import mercado.emark.repository.EnderecoRepository;

@Service
public class EnderecoService {
    @Autowired
    private IEnderecoClient enderecoClient;

    public Endereco buscaCep( String cep){
        return enderecoClient.buscaCep(cep);
    }

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }
}
