package agenda.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoDTO {
    
    @NotBlank(message = "O nome do contato é obrigatório")
    private String nome;

    @NotBlank(message = "O telefone do contato é obrigatório")
    private String telefone;

    @NotBlank(message = "O email do contato é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "A idade do contato é obrigatória")
    @Positive(message = "A idade do contato deve ser um número positivo")
    private Integer idade;

    @NotNull(message = "A data de nascimento do contato é obrigatória")
    private LocalDate dataNascimento;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;
}