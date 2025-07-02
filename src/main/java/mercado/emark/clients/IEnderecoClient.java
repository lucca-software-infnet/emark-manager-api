package mercado.emark.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mercado.emark.model.Endereco;

@FeignClient(url = "https://viacep.com.br/ws", name = "enderecoClient")
public interface IEnderecoClient {
    @GetMapping("/{cep}/json/")
    public Endereco buscaCep(@PathVariable String cep);
}
