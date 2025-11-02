package br.com.desafioVagaCRM.cliente;

import br.com.desafioVagaCRM.contato.Contato;
import br.com.desafioVagaCRM.contato.ContatoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ContatoRepository contatoRepository;

    public ClienteController(ClienteRepository clienteRepository, ContatoRepository contatoRepository) {
        this.clienteRepository = clienteRepository;
        this.contatoRepository = contatoRepository;
    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @PostMapping("/{id}/contatos")
    public ResponseEntity<Contato> salvar(@RequestBody Contato contato, @PathVariable Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            contato.setCliente(clienteOpt.get());
            Contato contatoSalvo = contatoRepository.save(contato);
            return ResponseEntity.status(HttpStatus.CREATED).body(contatoSalvo);
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> buscarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok().body(clientes);
    }

    @GetMapping("/{id}/contatos")
    public ResponseEntity<List<Contato>> buscarContatos(@PathVariable Long id) {
        List<Contato> contatosCliente = contatoRepository.findAllByCliente_Id(id);
        return ResponseEntity.ok().body(contatosCliente);
    }

}
