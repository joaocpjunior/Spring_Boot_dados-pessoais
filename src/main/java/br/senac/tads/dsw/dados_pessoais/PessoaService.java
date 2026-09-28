package br.senac.tads.dsw.dados_pessoais;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class PessoaService {

    private final AtomicInteger contador = new AtomicInteger(0);
    private final Map<String, PessoaDto> mapPessoas = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        adicionarPessoa("fulano", "Fulano da Silva", "fulano@email.com", "(11) 99999-1234", "2000-10-20");
        adicionarPessoa("ciclano", "Ciclano de Souza", "ciclano@email.com", "(11) 98888-5678", "1999-05-10");
        adicionarPessoa("beltrana", "Beltrana dos Santos", "beltrana@email.com", "(11) 97777-9012", "2001-02-23");
    }

    private void adicionarPessoa(String username, String nome, String email, String telefone, String dataNascimento) {
        PessoaDto pessoa = new PessoaDto(
            contador.incrementAndGet(),
            username,
            nome,
            email,
            telefone,
            LocalDate.parse(dataNascimento)
        );
        mapPessoas.put(username, pessoa);
    }

    //novo método adicionar, o método de cração...
    public PessoaDto incluirNovaPessoa(PessoaDto pessoa) {
    pessoa.setId(contador.incrementAndGet());
    mapPessoas.put(pessoa.getUsername(), pessoa);
    return pessoa;
}


    public List<PessoaDto> obterPessoas() {
        return new ArrayList<>(mapPessoas.values());
    }

    public Optional<PessoaDto> obterPessoa(String username) {
        return Optional.ofNullable(mapPessoas.get(username));
    }

    /* 
    public void removerPessoa(String username) {
        mapPessoas.remove(username);
    } 
    */

    public PessoaDto alterarPessoa(String username, PessoaAlteracaoDto pessoaAlteracao) {
    if (!mapPessoas.containsKey(username)) {
        throw new NaoEncontradoException("Pessoa " + username + " não encontrada");
    }
    PessoaDto pessoaOriginal = mapPessoas.get(username);
    pessoaOriginal.setNome(pessoaAlteracao.getNome());
    pessoaOriginal.setEmail(pessoaAlteracao.getEmail());
    pessoaOriginal.setTelefone(pessoaAlteracao.getTelefone());
    pessoaOriginal.setDataNascimento(pessoaAlteracao.getDataNascimento());
    pessoaOriginal.setConhecimentos(pessoaAlteracao.getConhecimentos());
    return pessoaOriginal;
}

public void removerPessoa(String username) {
    if (!mapPessoas.containsKey(username)) {
        throw new NaoEncontradoException("Pessoa " + username + " não encontrada");
    }
    mapPessoas.remove(username);
}

    public void atualizarPessoa(String username, PessoaDto novaPessoa) {
        mapPessoas.put(username, novaPessoa);
    }
}

