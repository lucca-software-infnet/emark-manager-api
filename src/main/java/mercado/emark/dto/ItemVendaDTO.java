package mercado.emark.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVendaDTO {
    private Integer produtoCodigo;
    private Integer quantidade;
    private Float precoUnitario;
}
