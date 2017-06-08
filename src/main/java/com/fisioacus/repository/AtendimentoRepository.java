package com.fisioacus.repository;

import com.fisioacus.domain.Atendimento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Atendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento,Long> {

}
