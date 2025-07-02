package mercado.emark.dto;

import java.sql.Date;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mercado.emark.enums.Sexo;
import mercado.emark.enums.UserRole;

@Getter
@Setter
public class ClienteUpdateDTO {

    private Integer id;

    private String imagem;

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