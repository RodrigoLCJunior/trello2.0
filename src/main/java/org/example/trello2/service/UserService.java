package org.example.trello2.service;

import org.example.trello2.model.User;
import org.example.trello2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método para criar um usuário
    public User createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RuntimeException("O email não pode ser vazio.");
        }
        if (user.getSenha() == null || user.getSenha().isEmpty()) {
            throw new RuntimeException("A senha não pode ser vazia.");
        }
        return userRepository.save(user);
    }

    // Método para listar todos os usuários
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Método para buscar um usuário por ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id)
                .or(() -> {
                    throw new RuntimeException("Usuário não encontrado.");
                });
    }

    // Método para atualizar um usuário existente
    public User updateUser(Integer id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    if (userDetails.getNome() != null) user.setNome(userDetails.getNome());
                    if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
                    if (userDetails.getSenha() != null) user.setSenha(userDetails.getSenha());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("Usuário não encontrado para atualização."));
    }

    // Método para deletar um usuário
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado para exclusão.");
        }
        userRepository.deleteById(id);
    }
}
