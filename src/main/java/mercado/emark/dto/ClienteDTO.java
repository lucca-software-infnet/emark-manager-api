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

@Getter
@Setter
public class ClienteDTO {

    private Integer id;

    private String imagem;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(max = 100, message = "Sobrenome deve ter no máximo 100 caracteres")
    private String sobrenome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}", message = "CPF inválido (formato esperado: 000.000.000-00)")
    private String cpf;

    @NotNull(message = "Sexo é obrigatório")
    private Sexo sexo;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @Pattern(regexp = "(\\(?\\d{2}\\)?[\\s-]?)?(\\d{4,5}[\\s-]?\\d{4})", message = "Telefone inválido (formatos aceitos: (00) 0000-0000, (00) 00000-0000, 0000-0000)")
    private String telefone;

    @Pattern(regexp = "(\\(?\\d{2}\\)?[\\s-]?)?(\\d{5}[\\s-]?\\d{4})", message = "Celular inválido (formatos aceitos: (00) 00000-0000, 00000-0000)")
    private String celular;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;

    
    @Size(max = 150, message = "Logradouro deve ter no máximo 150 caracteres")
    private String logradouro;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido (formato esperado: 00000-000)")
    private String cep;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser uma data passada")
    private Date dataNascimento;
}