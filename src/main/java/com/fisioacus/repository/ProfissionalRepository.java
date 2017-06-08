package com.fisioacus.repository;

import com.fisioacus.domain.Profissional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Profissional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional,Long> {

}
