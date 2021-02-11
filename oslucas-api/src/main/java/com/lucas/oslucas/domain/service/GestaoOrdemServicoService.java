package com.lucas.oslucas.domain.service;


import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.oslucas.api.model.Comentario;
import com.lucas.oslucas.domain.exception.EntidadeNaoEncontradaException;
import com.lucas.oslucas.domain.exception.NegocioException;
import com.lucas.oslucas.domain.model.Cliente;
import com.lucas.oslucas.domain.model.OrdemServico;
import com.lucas.oslucas.domain.model.StatusOrdemServico;
import com.lucas.oslucas.domain.repository.ClienteRepository;
import com.lucas.oslucas.domain.repository.ComentarioRepository;
import com.lucas.oslucas.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId()).orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}	
	
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId).orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
}
