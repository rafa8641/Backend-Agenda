package agenda.dto;

import java.time.LocalDate;

import agenda.entity.Contato;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContatoRespostaDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Integer idade;
    private LocalDate dataNascimento;
    private Long usuarioId;

    public ContatoRespostaDTO(Contato contato) {
        this.id = contato.getId();
        this.nome = contato.getNome();
        this.email = contato.getEmail();
        this.telefone = contato.getTelefone();
        this.idade = contato.getIdade();
        this.dataNascimento = contato.getDataNascimento();
        this.usuarioId = contato.getUsuario().getId();
    }
}