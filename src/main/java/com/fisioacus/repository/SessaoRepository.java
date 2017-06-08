package com.fisioacus.repository;

import com.fisioacus.domain.Sessao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sessao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SessaoRepository extends JpaRepository<Sessao,Long> {

}
