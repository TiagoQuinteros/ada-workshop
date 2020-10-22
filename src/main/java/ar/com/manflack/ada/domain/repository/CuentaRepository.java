package ar.com.manflack.ada.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.manflack.ada.domain.model.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long>
{
}
