package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.IProductRepository;
import com.example.redbuild_ai_backend.repositories.IPublicationRepository;
import com.example.redbuild_ai_backend.repositories.IResenaRepository;
import com.example.redbuild_ai_backend.repositories.ITransaccionRepository;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements IUserService {

    private final IUserRepository uR;
    private final PasswordEncoder passwordEncoder;
    private final IProductRepository productRepository;
    private final IPublicationRepository publicationRepository;
    private final ITransaccionRepository transaccionRepository;
    private final IResenaRepository resenaRepository;

    public UserServiceImplement(
            IUserRepository uR,
            PasswordEncoder passwordEncoder,
            IProductRepository productRepository,
            IPublicationRepository publicationRepository,
            ITransaccionRepository transaccionRepository,
            IResenaRepository resenaRepository) {

        this.uR = uR;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
        this.publicationRepository = publicationRepository;
        this.transaccionRepository = transaccionRepository;
        this.resenaRepository = resenaRepository;
    }

    @Override
    public void insert(User user) {
        user.setPasswordUser(
                passwordEncoder.encode(user.getPasswordUser())
        );
        uR.save(user);
    }

    @Override
    public List<User> list() {
        return uR.findAll();
    }

    @Override
    public Optional<User> listId(Long id) {
        return uR.findById(id);
    }

    @Override
    public void update(User user) {
        uR.save(user);
    }

    @Override
    public List<User> listByStatus(String statusUser) {
        return uR.findByStatusUser(statusUser);
    }

    @Override
    public List<User> listByRoleName(String nameRole) {
        return uR.findByRoleName(nameRole);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        User user = uR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el usuario con ID: " + id
                ));

        if (productRepository.existsByUser_IdUser(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el usuario porque "
                            + "tiene productos asociados"
            );
        }

        if (publicationRepository.existsByPublisher_IdUser(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el usuario porque "
                            + "tiene publicaciones asociadas"
            );
        }

        if (transaccionRepository.existsByUser_IdUser(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el usuario porque "
                            + "tiene transacciones asociadas"
            );
        }

        if (resenaRepository
                .existsByUser_IdUserOrRatedUser_IdUser(id, id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el usuario porque "
                            + "es autor o destinatario de reseñas"
            );
        }

        try {
            uR.delete(user);
            uR.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el usuario porque "
                            + "tiene registros asociados",
                    exception
            );
        }
    }
}