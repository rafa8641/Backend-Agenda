package agenda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agenda.entity.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    List<Contato> findByUsuarioId(Long usuarioId);
    List<Contato> findByNomeContainingIgnoreCase(String nome);
    Optional<Contato> findByTelefone(String telefone);
    List<Contato> findByUsuarioIdAndAtivoTrue(Long usuarioId);
    long countByAtivoTrueAndUsuarioId(Long usuarioId);
    Optional<Contato> findByEmail(String email);
    boolean existsByEmailAndUsuarioId(String email, Long usuarioId);

}