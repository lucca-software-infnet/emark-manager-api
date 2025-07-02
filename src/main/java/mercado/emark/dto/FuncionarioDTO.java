package mercado.emark.dto;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mercado.emark.enums.Cargo;
import mercado.emark.enums.Setor;
import mercado.emark.enums.Sexo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;


@Getter
@Setter
public class FuncionarioDTO {

    private Integer id;

    private String imagem;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(max = 100, message = "Sobrenome deve ter no máximo 100 caracteres")
    private String sobrenome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}", 
             message = "CPF deve seguir o padrão 000.000.000-00")
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    @Size(min = 9, max = 20, message = "RG deve ter entre 9 e 20 caracteres")
    private String rg;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private Date dataNascimento;

    @NotNull(message = "Cargo é obrigatório")
    private Cargo cargo;

    @NotNull(message = "Setor é obrigatório")
    private Setor setor;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @NotNull(message = "Sexo é obrigatório")
    private Sexo sexo;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;

    @Pattern(regexp = "\\(?\\d{2}\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}", 
             message = "Telefone deve seguir o padrão (00) 0000-0000 ou (00) 00000-0000")
    private String telefone;

    @Pattern(regexp = "\\(?\\d{2}\\)?[\\s-]?\\d{5}[\\s-]?\\d{4}", 
             message = "Celular deve seguir o padrão (00) 00000-0000")
    private String celular;

    @NotBlank(message = "Logradouro é obrigatório")
    @Size(max = 150, message = "Logradouro deve ter no máximo 150 caracteres")
    private String logradouro;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve seguir o padrão 00000-000")
    private String cep;

    @NotBlank(message = "Bairro é obrigatório")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "Estado deve ser a sigla com 2 caracteres")
    private String estado;

}