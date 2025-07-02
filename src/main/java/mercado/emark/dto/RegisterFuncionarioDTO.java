package mercado.emark.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import mercado.emark.enums.Cargo;
import mercado.emark.enums.Setor;
import mercado.emark.enums.Sexo;
import mercado.emark.enums.UserRole;

@Getter
@Setter
public class RegisterFuncionarioDTO {

    private String nome;
    private String sobrenome;
    private String cpf;
    private String rg;
    private Sexo sexo;
    private String email;
    private String telefone;
    private String celular;
    private String password;
    private Date dataNascimento;
    private Cargo cargo;
    private Setor setor;
    private UserRole role;
    private String numero;
    private String complemento;
    private Boolean administrador;
    private String cep;
    
}
