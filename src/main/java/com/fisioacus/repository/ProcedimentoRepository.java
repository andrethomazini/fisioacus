package com.fisioacus.repository;

import com.fisioacus.domain.Procedimento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Procedimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedimentoRepository extends JpaRepository<Procedimento,Long> {

}
