package org.example.trello2.service;

import org.example.trello2.model.Card;
import org.example.trello2.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Card updateCard(Long id, Card cardDetails) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        
        if (optionalCard.isPresent()) {
            Card cardToUpdate = optionalCard.get();
            cardToUpdate.setTitulo(cardDetails.getTitulo());
            cardToUpdate.setDescricao(cardDetails.getDescricao());
            // Atualize outros campos conforme necessário
            return cardRepository.save(cardToUpdate);
        }

        return null;
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
