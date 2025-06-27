package com.mindtrack.entity.dto.relatorio;

public record RespostaAgrupadaDTO(
        Long idOpcaoResposta,
        String textoOpcao,
        long quantidade,
        String percentual
) {
}
