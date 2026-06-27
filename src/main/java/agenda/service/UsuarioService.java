package agenda.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import agenda.entity.Usuario;
import agenda.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findById(Long id) {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(id);
        return usuarioOptional.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario findByEmail(String email) {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(email);
        return usuarioOptional.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario create(Usuario usuario) {
        usuario.setId(null);
        validarUsuario(usuario);
        if(existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        usuario = this.usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = findByEmail(email);
        if (!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta");
        }
        return usuario;
    }

    @Transactional
    public Usuario update(Long id,Usuario usuario) {
         Usuario usuarioExistente = findById(id);
        if (!usuarioExistente.getEmail().equals(usuario.getEmail())
                && existsByEmail(usuario.getEmail())) {

            throw new RuntimeException("E-mail já cadastrado.");
        }

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setSenha(usuario.getSenha());

        return usuarioRepository.save(usuarioExistente);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.usuarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível excluir o usuário");
        }
    }

    private boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    private void validarUsuario(Usuario usuario) {
         if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
        throw new RuntimeException("O nome é obrigatório.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new RuntimeException("O e-mail é obrigatório.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().length() < 8) {
            throw new RuntimeException("A senha deve possuir no mínimo 8 caracteres.");
        }
    }

}