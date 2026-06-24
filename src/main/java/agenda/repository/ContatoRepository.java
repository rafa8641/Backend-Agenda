package agenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agenda.entity.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    List<Contato> findByUsuarioId(Long usuarioId);
    List<Contato> findByNomeContainingIgnoreCase(String nome);
    List<Contato> findByAtivoTrue();
    long countByAtivoTrue();

}