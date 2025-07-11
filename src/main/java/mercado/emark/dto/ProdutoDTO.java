package mercado.emark.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import mercado.emark.enums.Departamento;
import mercado.emark.model.ImagemProduto;

@Getter
@Setter
public class ProdutoDTO {

    private Integer id;

    @NotNull(message = "Código do produto é obrigatório")
    @Min(value = 1, message = "Código deve ser maior que zero")
    private Integer codigo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @FutureOrPresent(message = "Data de validade deve ser atual ou futura")
    private Date validade;

    @NotNull(message = "Volume é obrigatório")
    @Positive(message = "Volume deve ser positivo")
    private Integer volume;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    private Integer quantidade = 0;

    @NotNull(message = "Preço de custo é obrigatório")
    @Positive(message = "Preço de custo deve ser positivo")
    private Float precoCusto; 

    @NotNull(message = "Preço de venda é obrigatório")
    @Positive(message = "Preço de venda deve ser positivo")
    private Float precoVenda; 

    @Size(max = 100, message = "Marca deve ter no máximo 100 caracteres")
    private String marca;

    @NotNull(message = "Departamento é obrigatório")
    private Departamento departamento;

    @PositiveOrZero(message = "Prateleira deve ser um número positivo")
    private Integer prateleira;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Integer codigo, String descricao, Date validade, Integer volume,
            Integer quantidade, Float precoCusto, Float precoVenda,
            String marca, @NotBlank(message = "Departamento é obrigatório") Departamento departamento, Integer prateleira) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.validade = validade;
        this.volume = volume;
        this.quantidade = quantidade;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.marca = marca;
        this.departamento = departamento;
        this.prateleira = prateleira;
    }
}