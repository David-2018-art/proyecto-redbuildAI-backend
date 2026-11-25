package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Resena;
import com.example.redbuild_ai_backend.entities.Transaccion;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.IResenaRepository;
import com.example.redbuild_ai_backend.repositories.ITransaccionRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IResenaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ResenaServiceImplement implements IResenaService {

    private final IResenaRepository rR;
    private final ITransaccionRepository tR;

    public ResenaServiceImplement(
            IResenaRepository rR,
            ITransaccionRepository tR) {

        this.rR = rR;
        this.tR = tR;
    }

    @Override
    @Transactional
    public void insert(Resena resena) {

        if (resena.getIdResena() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No envíes un ID para registrar una reseña"
            );
        }

        validarResena(resena);

        boolean existe = rR
                .existsByTransaccion_IdTransaccionAndUser_IdUser(
                        resena.getTransaccion().getIdTransaccion(),
                        resena.getUser().getIdUser()
                );

        if (existe) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El autor ya registró una reseña para esta transacción"
            );
        }

        rR.saveAndFlush(resena);
    }

    @Override
    public List<Resena> list() {
        return rR.findAll();
    }

    @Override
    public List<Resena> findByUserId(Long userId) {
        return rR.findByUserId(userId);
    }

    @Override
    public Optional<Resena> listId(Long id) {
        return rR.findById(id);
    }

    @Override
    @Transactional
    public void update(Resena resena) {

        if (resena.getIdResena() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID de la reseña es obligatorio"
            );
        }

        Resena original = rR.findById(resena.getIdResena())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la reseña con ID: " + resena.getIdResena()
                ));

        validarResena(resena);

        boolean mismoAutor = Objects.equals(
                original.getUser().getIdUser(),
                resena.getUser().getIdUser()
        );

        boolean mismoCalificado = Objects.equals(
                original.getRatedUser().getIdUser(),
                resena.getRatedUser().getIdUser()
        );

        boolean mismaTransaccion = Objects.equals(
                original.getTransaccion().getIdTransaccion(),
                resena.getTransaccion().getIdTransaccion()
        );

        if (!mismoAutor || !mismoCalificado || !mismaTransaccion) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se permite cambiar el autor, "
                            + "el usuario calificado ni la transacción"
            );
        }

        original.setTitleResena(resena.getTitleResena());
        original.setDescriptionResena(resena.getDescriptionResena());
        original.setScoreResena(resena.getScoreResena());
        original.setStatusResena(resena.isStatusResena());

        rR.saveAndFlush(original);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Resena resena = rR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la reseña con ID: " + id
                ));

        rR.delete(resena);
        rR.flush();
    }

    private void validarResena(Resena resena) {

        Integer nota = resena.getScoreResena();

        if (nota == null || nota < 1 || nota > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La calificación debe estar entre 1 y 5"
            );
        }

        if (resena.getUser() == null
                || resena.getRatedUser() == null
                || resena.getTransaccion() == null
                || resena.getUser().getIdUser() == null
                || resena.getRatedUser().getIdUser() == null
                || resena.getTransaccion().getIdTransaccion() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debes indicar autor, usuario calificado y transacción"
            );
        }

        Long autor = resena.getUser().getIdUser();
        Long calificado = resena.getRatedUser().getIdUser();

        if (Objects.equals(autor, calificado)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No puedes calificarte a ti mismo"
            );
        }

        Long idTransaccion = resena.getTransaccion().getIdTransaccion();

        Transaccion transaccion = tR.findById(idTransaccion)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la transacción con ID: " + idTransaccion
                ));

        Long adquirente = transaccion.getUser().getIdUser();
        Long publicador = transaccion.getPublication()
                .getPublisher().getIdUser();

        boolean adquirenteCalificaPublicador =
                Objects.equals(autor, adquirente)
                        && Objects.equals(calificado, publicador);

        boolean publicadorCalificaAdquirente =
                Objects.equals(autor, publicador)
                        && Objects.equals(calificado, adquirente);

        if (!adquirenteCalificaPublicador
                && !publicadorCalificaAdquirente) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El autor y el usuario calificado deben ser "
                            + "el adquirente y el publicador de esta transacción"
            );
        }

        if (!"Completada".equals(transaccion.getStatusTransaction())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Solo se permiten reseñas de transacciones completadas"
            );
        }

        resena.setTransaccion(transaccion);
    }
}