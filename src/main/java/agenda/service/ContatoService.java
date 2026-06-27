package agenda.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import agenda.entity.Contato;
import agenda.entity.Usuario;
import agenda.repository.ContatoRepository;
import agenda.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final UsuarioRepository usuarioRepository;

    public ContatoService(ContatoRepository contatoRepository, UsuarioRepository usuarioRepository) {
        this.contatoRepository = contatoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Contato findById(Long id) {
         Optional<Contato> contatoOptional = this.contatoRepository.findById(id);
        return contatoOptional.orElseThrow(() -> new RuntimeException("Contato não encontrado"));
    }

      public Contato findByEmail(String email) {
        Optional<Contato> contatoOptional = this.contatoRepository.findByEmail(email);
        return contatoOptional.orElseThrow(() -> new RuntimeException("Contato não encontrado"));
    }

    public List<Contato> findByNome(String nome) {
        return this.contatoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Contato findByTelefone(String telefone) {
        Optional<Contato> contatoOptional = this.contatoRepository.findByTelefone(telefone);
        return contatoOptional.orElseThrow(() -> new RuntimeException("Contato não encontrado"));
    }

    public List<Contato> findByUsuarioId(Long usuarioId) {
        return this.contatoRepository.findByUsuarioId(usuarioId);
    }

    public List<Contato> findAtivosByUsuario(Long usuarioId) {
        return contatoRepository.findByUsuarioIdAndAtivoTrue(usuarioId);
    }

    public Iterable<Contato> findAll() {
        return this.contatoRepository.findAll();
    }

    @Transactional
    public Contato create(Contato contato) {
        contato.setId(null);

        Usuario usuario = usuarioRepository.findById(contato.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        contato.setUsuario(usuario);

        validarContato(contato);
        if (contatoRepository.existsByEmailAndUsuarioId(
            contato.getEmail(),
            contato.getUsuario().getId())) {

            throw new RuntimeException("Este contato já está cadastrado para este usuário");
        }

        contato = this.contatoRepository.save(contato);
        return contato;
    }

    @Transactional
    public Contato update(Long id, Contato contato) {
        Contato contatoExistente = findById(id);

        Usuario usuario = usuarioRepository.findById(contato.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        validarContato(contato);
        if (!contatoExistente.getEmail().equals(contato.getEmail())
                && contatoRepository.existsByEmailAndUsuarioId(contato.getEmail(), contato.getUsuario().getId())) {

            throw new RuntimeException("E-mail já cadastrado.");
        }

        contatoExistente.setNome(contato.getNome());
        contatoExistente.setEmail(contato.getEmail());
        contatoExistente.setTelefone(contato.getTelefone());
        contatoExistente.setIdade(contato.getIdade());
        contatoExistente.setDataNascimento(contato.getDataNascimento());
        contatoExistente.setUsuario(usuario);

        return contatoRepository.save(contatoExistente);
    }

    @Transactional
    public void desativar(Long id) {
        Contato contatoExistente = findById(id);
        contatoExistente.setAtivo(false);
        contatoRepository.save(contatoExistente);
    }

    public long countByAtivoTrueAndUsuarioId(Long usuarioId) {
        return contatoRepository.countByAtivoTrueAndUsuarioId(usuarioId);
    }

    private void validarContato(Contato contato) {
         if (contato.getNome() == null || contato.getNome().trim().isEmpty()) {
        throw new RuntimeException("O nome é obrigatório.");
        }
        if (contato.getEmail() == null || contato.getEmail().trim().isEmpty()) {
            throw new RuntimeException("O e-mail é obrigatório.");
        }
        if (contato.getTelefone() == null || contato.getTelefone().trim().isEmpty()) {
            throw new RuntimeException("O telefone é obrigatório.");
        }
        if (contato.getIdade() == null || contato.getIdade() <= 0) {
            throw new RuntimeException("A idade deve ser maior que zero.");
        }
        if (contato.getDataNascimento() == null) {
            throw new RuntimeException("A data de nascimento é obrigatória.");
        }
        if (contato.getUsuario() == null) {
            throw new RuntimeException("O contato deve estar vinculado a um usuário.");
        }
        if (contato.getDataNascimento().isAfter(LocalDate.now())) {
            throw new RuntimeException("A data de nascimento não pode ser futura.");
        }
    }

    private boolean existsByEmailAndUsuarioId(String email, Long usuarioId) {
        return contatoRepository.existsByEmailAndUsuarioId(email, usuarioId);
    }
    
}