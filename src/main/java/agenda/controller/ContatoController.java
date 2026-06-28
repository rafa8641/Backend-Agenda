package agenda.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agenda.dto.ContatoDTO;
import agenda.dto.ContatoRespostaDTO;
import agenda.entity.Contato;
import agenda.entity.Usuario;
import agenda.service.ContatoService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/contatos")
public class ContatoController {

    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ContatoDTO contatoDTO){
        Contato contato = new Contato();
        contato.setNome(contatoDTO.getNome());
        contato.setTelefone(contatoDTO.getTelefone());
        contato.setEmail(contatoDTO.getEmail());
        contato.setIdade(contatoDTO.getIdade());
        contato.setDataNascimento(contatoDTO.getDataNascimento());

        Usuario usuario = new Usuario();
        usuario.setId(contatoDTO.getUsuarioId());
        contato.setUsuario(usuario);

        Contato contatoCriado = contatoService.create(contato);

        return ResponseEntity.created(URI.create("/contatos/" + contatoCriado.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoRespostaDTO> findById(@PathVariable Long id){
        Contato contato = this.contatoService.findById(id);
        return ResponseEntity.ok(new ContatoRespostaDTO(contato));
    }

    @GetMapping
    public ResponseEntity<Iterable<Contato>> findAll(){
        Iterable<Contato> contatos = this.contatoService.findAll();
        return ResponseEntity.ok().body(contatos);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Iterable<Contato>> findByNome(@PathVariable String nome){
        Iterable<Contato> contatos = this.contatoService.findByNome(nome);
        return ResponseEntity.ok().body(contatos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Iterable<Contato>> findByUsuarioId(@PathVariable Long usuarioId){
        Iterable<Contato> contatos = this.contatoService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok().body(contatos);
    }

    @GetMapping("/usuario/{usuarioId}/ativos")
    public ResponseEntity<List<Contato>> findAtivos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(contatoService.findAtivosByUsuario(usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> update(@PathVariable Long id, @Valid @RequestBody ContatoDTO contatoDTO){
        Contato contato = new Contato();
        contato.setNome(contatoDTO.getNome());
        contato.setTelefone(contatoDTO.getTelefone());
        contato.setEmail(contatoDTO.getEmail());
        contato.setIdade(contatoDTO.getIdade());
        contato.setDataNascimento(contatoDTO.getDataNascimento());

        Usuario usuario = new Usuario();
        usuario.setId(contatoDTO.getUsuarioId());
        contato.setUsuario(usuario);

        Contato contatoAtualizado = this.contatoService.update(id, contato);
        return ResponseEntity.ok().body(contatoAtualizado);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        this.contatoService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}/ativos/quantidade")
    public ResponseEntity<Long> countAtivosPorUsuario(@PathVariable Long usuarioId) {
        long quantidade = this.contatoService.countByAtivoTrueAndUsuarioId(usuarioId);
        return ResponseEntity.ok().body(quantidade);
    }

}