package com.lucas.oslucas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucas.oslucas.domain.model.OrdemServico;


@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

}
