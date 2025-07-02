package mercado.emark.dto;

public record EnderecoDTO(
    String cep,
    String logradouro,
    String bairro,
    String localidade,
    String uf,
    String numero,
    String complemento
) {}

