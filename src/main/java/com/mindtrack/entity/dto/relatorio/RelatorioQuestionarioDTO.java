package com.mindtrack.entity.dto.relatorio;

import java.time.LocalDate;
import java.util.List;

public record RelatorioQuestionarioDTO(
        Long idQuestionario,
        String tituloQuestionario,
        String descricaoQuestionario,
        LocalDate dataPublicacao,
        LocalDate dataValidade,
        Long totalParticipantes,
        Double mediaGeral,
        List<MediaPorSetorDTO> mediaSetores,
        List<PerguntaRelatorioDTO> perguntasRelatorio
) {}
