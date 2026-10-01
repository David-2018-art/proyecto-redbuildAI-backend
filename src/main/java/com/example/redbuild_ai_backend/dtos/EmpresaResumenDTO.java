package com.example.redbuild_ai_backend.dtos;

import java.math.BigDecimal;

public class EmpresaResumenDTO {
    private Long ventasCompletadas;
    private BigDecimal ingresosVentas;
    private Long donacionesCompletadas;

    public EmpresaResumenDTO() {
        this.ventasCompletadas = 0L;
        this.ingresosVentas = BigDecimal.ZERO;
        this.donacionesCompletadas = 0L;
    }

    public Long getVentasCompletadas() {
        return ventasCompletadas;
    }

    public void setVentasCompletadas(Long ventasCompletadas) {
        this.ventasCompletadas = ventasCompletadas;
    }

    public BigDecimal getIngresosVentas() {
        return ingresosVentas;
    }

    public void setIngresosVentas(BigDecimal ingresosVentas) {
        this.ingresosVentas = ingresosVentas;
    }

    public Long getDonacionesCompletadas() {
        return donacionesCompletadas;
    }

    public void setDonacionesCompletadas(Long donacionesCompletadas) {
        this.donacionesCompletadas = donacionesCompletadas;
    }
}
