package mercado.emark.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import mercado.emark.enums.Sexo;
import mercado.emark.enums.UserRole;

@Getter
@Setter
public class RegisterClienteDTO {

    private String nome;
    private String sobrenome;
    private String cpf;
    private Sexo sexo;
    private String email;
    private String telefone;
    private String celular;
    private String password;
    private Date dataNascimento;
    private UserRole role;
    private String numero;
    private String complemento;
    private String cep;
}
