package ar.com.manflack.ada.app.rest;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.manflack.ada.domain.model.Cuenta;
import ar.com.manflack.ada.domain.model.TransferRequest;
import ar.com.manflack.ada.domain.repository.CuentaRepository;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/transaction")
public class TransaccionesController
{
	@Autowired
	private CuentaRepository cuentaRepository;

	@PostMapping("/plata/cargar/{id}")
	public ResponseEntity<Cuenta> cargarPlata(
			@Valid @RequestParam(name = "plata") @Min(value = 1, message = "La plata a cargar debe ser positiva") BigDecimal plata,
			@PathVariable(name = "id") @NotNull Long id)
	{
		Cuenta cuenta = cuentaRepository.findById(id).get();

		BigDecimal nuevoSaldo = cuenta.getBalance().add(plata);

		cuenta.setBalance(nuevoSaldo);
		cuenta = cuentaRepository.saveAndFlush(cuenta);

		return new ResponseEntity<>(cuenta, HttpStatus.OK);
	}

	@PostMapping("/transferir")
	public ResponseEntity<String> transferirPlata(@Valid @RequestBody TransferRequest transfer)
	{
		sacarPlata(transfer.getMonto(), transfer.getIdOrigen());
		cargarPlata(transfer.getMonto(), transfer.getIdDestino());

		return new ResponseEntity<>("Transferencia exitosa", HttpStatus.OK);
	}

	@PostMapping("/plata/sacar/{id}")
	public ResponseEntity<Cuenta> sacarPlata(
			@Valid @Positive(message = "La plata a sacar debe ser positiva") @RequestParam(name = "plata") BigDecimal plata,
			@PathVariable(name = "id") @NotNull Long id)
	{
		Cuenta cuenta = cuentaRepository.findById(id).get();

		BigDecimal nuevoSaldo = cuenta.getBalance().add(plata.negate());

		cuenta.setBalance(nuevoSaldo);
		cuenta = cuentaRepository.saveAndFlush(cuenta);

		return new ResponseEntity<>(cuenta, HttpStatus.OK);
	}
}
