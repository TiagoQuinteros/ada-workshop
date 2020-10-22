package ar.com.manflack.ada.domain.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferRequest
{
    @NotNull
    private Long idOrigen;

    @NotNull
    private Long idDestino;

    @NotNull
    private BigDecimal monto;

    public Long getIdOrigen()
    {
        return idOrigen;
    }

    public void setIdOrigen(Long idOrigen)
    {
        this.idOrigen = idOrigen;
    }

    public Long getIdDestino()
    {
        return idDestino;
    }

    public void setIdDestino(Long idDestino)
    {
        this.idDestino = idDestino;
    }

    public BigDecimal getMonto()
    {
        return monto;
    }

    public void setMonto(BigDecimal monto)
    {
        this.monto = monto;
    }
}
