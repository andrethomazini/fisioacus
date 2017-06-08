package com.fisioacus.repository;

import com.fisioacus.domain.Convenio;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Convenio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConvenioRepository extends JpaRepository<Convenio,Long> {

}
