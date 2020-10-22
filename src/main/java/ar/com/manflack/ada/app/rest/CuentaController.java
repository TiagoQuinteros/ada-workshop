package ar.com.manflack.ada.app.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.manflack.ada.domain.model.Cuenta;
import ar.com.manflack.ada.domain.repository.CuentaRepository;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "cuentas")
public class CuentaController
{
	@Autowired
	private CuentaRepository cuentaRepository;

	@Transactional
	@PostMapping("")
	public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta)
	{
		Cuenta result = cuentaRepository.save(cuenta);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cuenta> getCuenta(@PathVariable Long id)
	{
		Optional<Cuenta> result = cuentaRepository.findById(id);
		return new ResponseEntity<>(result.get(), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<Cuenta>> getAllCuentas()
	{
		return new ResponseEntity<>(cuentaRepository.findAll(), HttpStatus.OK);
	}
}
