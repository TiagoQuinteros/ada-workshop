package ar.com.manflack.ada.repository;

import ar.com.manflack.ada.domain.Cuenta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cuenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
