import React, { useState } from 'react';
import axios from 'axios';

const CardModal = ({ boardId, closeModal }) => {
    const [cardTitle, setCardTitle] = useState('');
    const [cardDescription, setCardDescription] = useState('');

    const createCard = async (e) => {
        e.preventDefault();
        try {
            await axios.post(`http://localhost:8080/api/cards`, {
                title: cardTitle,
                description: cardDescription,
                boardId: boardId // Enviar o ID do Board ao criar o Card
            });
            closeModal(); // Fechar o modal após a criação do Card
        } catch (error) {
            console.error("Erro ao criar card:", error);
        }
    };

    return (
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
    );
};

export default CardModal;
