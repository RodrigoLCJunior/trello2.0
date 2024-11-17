import React, { useState } from 'react';
import axios from 'axios';
import '../styles/Card.css'; // Adicione o arquivo CSS para o Card

const Card = ({ boardId }) => {
    const [cards, setCards] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [cardTitle, setCardTitle] = useState('');
    const [cardDescription, setCardDescription] = useState('');

    const fetchCards = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/cards?boardId=${boardId}`);
            setCards(response.data);
        } catch (error) {
            console.error("Erro ao buscar cards:", error);
        }
    };

    const createCard = async (e) => {
        e.preventDefault();
        try {
            await axios.post(`http://localhost:8080/api/cards`, {
                title: cardTitle,
                description: cardDescription,
                boardId: boardId
            });
            setCardTitle('');
            setCardDescription('');
            setShowModal(false);
            fetchCards(); // Atualiza a lista de cards
        } catch (error) {
            console.error("Erro ao criar card:", error);
        }
    };

    const openModal = () => {
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setCardTitle('');
        setCardDescription('');
    };

    return (
        <div>
            <button onClick={openModal}>Criar Card</button>
            {showModal && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h3>Criar Card</h3>
                        <form onSubmit={createCard}>
                            <input
                                type="text"
                                value={cardTitle}
                                onChange={(e) => setCardTitle(e.target.value)}
                                placeholder="Título do Card"
                                required
                            />
                            <textarea
                                value={cardDescription}
                                onChange={(e) => setCardDescription(e.target.value)}
                                placeholder="Descrição do Card"
                                required
                            />
                            <button type="submit">Criar Card</button>
                            <button type="button" onClick={closeModal}>Cancelar</button>
                        </form>
                    </div>
                </div>
            )}
            <ul>
                {cards.map(card => (
                    <li key={card.id}>
                        <strong>{card.title}</strong>: {card.description}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Card;
