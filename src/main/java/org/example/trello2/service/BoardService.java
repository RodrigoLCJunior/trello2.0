package org.example.trello2.service;

import org.example.trello2.model.Board;
import org.example.trello2.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Board updateBoard(Long id, Board boardDetails) {
        // Busca o board existente
        Optional<Board> optionalBoard = boardRepository.findById(id);
        
        if (optionalBoard.isPresent()) {
            Board boardToUpdate = optionalBoard.get();
            
            // Atualiza os detalhes do board
            boardToUpdate.setName(boardDetails.getName()); // Atualizando nome
            boardToUpdate.setDescricao(boardDetails.getDescricao()); // Atualizando descrição
            // Adicione mais campos conforme necessário

            // Salva o board atualizado
            return boardRepository.save(boardToUpdate);
        }

        // Retorna null se o board não for encontrado
        return null;
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
