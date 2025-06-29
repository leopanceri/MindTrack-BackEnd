package com.mindtrack.entity.dto.relatorio;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PerguntaRelatorioDTO(
        Long idPergunta,
        String textoPergunta,
        String tipoPergunta,
        List<RespostaAgrupadaDTO> respostasAgrupadas,
        List<RespostaAbertaDTO> respostasAbertas
) {}
