package mercado.emark.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class VendaRequestDTO {
    private Date dataVenda;
    private String numeroPedido;
    private String formaPagamento;
    private String tipoPagamento;
    private String bandeira;
    private Integer clienteId;
    private List<ItemVendaDTO> itens;
}
