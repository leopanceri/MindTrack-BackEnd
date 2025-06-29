package com.mindtrack.controller;

import com.mindtrack.entity.dto.RespostaDTO;
import com.mindtrack.entity.dto.QuestionarioDTO;
import com.mindtrack.services.SurveyReplyService;
import com.mindtrack.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


//@CrossOrigin
@RestController
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyReplyService surveyReplyService;

    @PostMapping("/novoquestionario")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> criarNovoQuestionario(@RequestBody QuestionarioDTO questionarioDTO) {
        try {
            surveyService.criarNovoQuestionario(questionarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Questionário criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao criar questionário: " + e.getMessage());
        }
    }

    @GetMapping("/listaquestionarios")
    public ResponseEntity<?> listarQuestionarios() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(surveyService.listaQuestionarios());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Falha ao listar questionário");
        }
    }

    @GetMapping("/questionario/{id}")
    public ResponseEntity<?> findQuestionarioById(@PathVariable int id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(surveyService.buscarPorId((long) id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/questionario/resposta")
    @PreAuthorize("hasAuthority('FUNC')")
    public ResponseEntity<?> responderQuestionario(@RequestParam Long surveyId,
                                                   @RequestParam Long funcId,
                                                   @RequestBody List<RespostaDTO> replyList){
        try{
            surveyReplyService.salvarResposta(surveyId, funcId, replyList);
            return ResponseEntity.status(HttpStatus.OK).body("Resposta salva com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/questionarios/{id}")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> deletarQuestionario(@PathVariable Long id) {
        try {
            this.surveyService.removerQuestionario(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //endpoint para listar os questionários não respondidos por um funcionário específico
    @GetMapping("/questionarios")
    @PreAuthorize("hasAuthority('FUNC')")
    public ResponseEntity<?> listarQuestionarios(@RequestParam Long funcId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(surveyService.listaNaoRespondidosPorFuncionario(funcId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/editarquestionario/{id}")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> editarQuestionario(@PathVariable Long id, @RequestBody QuestionarioDTO surveyDTO) {
        try {
            surveyService.editarQuestionario(id, surveyDTO);
            return ResponseEntity.ok(Map.of("message", "Questionário atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao atualizar questionário: " + e.getMessage()));
        }
    }

    @DeleteMapping("/removerquestionario/{id}")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> removerQuestionario(@PathVariable Long id) {
        try {
            surveyService.removerQuestionario(id);
            return ResponseEntity.ok(Map.of("message", "Questionário removido com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao remover questionário: " + e.getMessage()));
        }
    }
}
