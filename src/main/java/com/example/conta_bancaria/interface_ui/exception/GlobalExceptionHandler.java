package com.example.conta_bancaria.interface_ui.exception;


import com.example.conta_bancaria.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValoresNegativosException.class)
    public ResponseEntity<String> handleValoresNegativos (ValoresNegativosException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntidadeNaoEncontradoException.class)
    public ResponseEntity<String> handleEntidadeNaoEncontrado (EntidadeNaoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficiente (SaldoInsuficienteException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContaMesmoTipoException.class)
    public ResponseEntity<String> handleContaMesmoTipo (ContaMesmoTipoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RendimentoInvalidoException.class)
    public ResponseEntity<String> handleRendimentoInvalido (RendimentoInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferirParaMesmaContaException.class)
    public ResponseEntity<String> handleTransferirParaMesmaConta (TransferirParaMesmaContaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TipoDeContaInvalidoException.class)
    public ResponseEntity<String> handleTipoDeContaInvalido (TipoDeContaInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
