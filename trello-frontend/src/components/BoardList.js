import React, { useState } from 'react';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import '../styles/BoardList.css';

// Componente para exibir cards dentro de um board
const Card = ({ card, index }) => {
  return (
    <Draggable key={card.title} draggableId={card.title} index={index}>
      {(provided) => (
        <div
          className="card-item"
          ref={provided.innerRef}
          {...provided.draggableProps}
          {...provided.dragHandleProps}
        >
          <h3 className="card-title">{card.title}</h3>
          <p className="card-description">{card.description}</p>
          <p className="card-status">Status: {card.status}</p>
          <p className="card-priority">Prioridade: {card.priority}</p>
          <p className="card-dueDate">Data Limite: {card.dueDate}</p>
        </div>
      )}
    </Draggable>
  );
};

// Componente para criar e listar os boards
const BoardList = () => {
  const [boards, setBoards] = useState([]); // Inicializando com uma lista vazia
  const [showModal, setShowModal] = useState(false);
  const [newCardData, setNewCardData] = useState({
    title: '',
    description: '',
    status: 'Nulo',
    priority: '',
    dueDate: ''
  });
  const [boardName, setBoardName] = useState(''); // Nome do board a ser criado
  const [currentBoard, setCurrentBoard] = useState(null); // Board selecionado para adicionar card

  // Função para criar um board
  const createBoard = () => {
    const newBoardId = `board-${boards.length + 1}`;
    const newBoard = { id: newBoardId, name: boardName || `Board ${boards.length + 1}`, cards: [] };
    setBoards(prevBoards => [...prevBoards, newBoard]);
    setBoardName(''); // Limpar o nome do board após a criação
  };

  // Função para mover os cards entre os boards
  const onDragEnd = (result) => {
    const { destination, source } = result;

    if (!destination) return; // Se o card foi solto fora de qualquer board, nada deve ser feito

    const sourceBoard = boards.find(board => board.id === source.droppableId);
    const destinationBoard = boards.find(board => board.id === destination.droppableId);

    if (sourceBoard === destinationBoard && source.index === destination.index) return; // Nada mudou se o card foi solto no mesmo lugar

    // Retirando o card do board de origem
    const sourceCards = Array.from(sourceBoard.cards);
    const [movedCard] = sourceCards.splice(source.index, 1);

    // Adicionando o card ao board de destino
    const destinationCards = Array.from(destinationBoard.cards);
    destinationCards.splice(destination.index, 0, movedCard);

    // Atualizando o estado dos boards
    setBoards(boards.map(board => {
      if (board.id === sourceBoard.id) return { ...board, cards: sourceCards };
      if (board.id === destinationBoard.id) return { ...board, cards: destinationCards };
      return board;
    }));
  };

  // Função para abrir o modal
  const openModal = (boardId) => {
    setCurrentBoard(boardId); // Define o board selecionado
    setShowModal(true);
  };

  // Função para fechar o modal
  const closeModal = () => {
    setShowModal(false);
    setCurrentBoard(null); // Limpa o board selecionado quando o modal for fechado
  };

  // Função para lidar com os dados do card
  const handleCardDataChange = (e) => {
    setNewCardData({
      ...newCardData,
      [e.target.name]: e.target.value
    });
  };

  // Função para criar um novo card no board selecionado
  const handleCardSubmit = (e) => {
    e.preventDefault();
    const newCard = { ...newCardData };

    // Encontrar o board onde o card será adicionado
    const boardIndex = boards.findIndex(board => board.id === currentBoard);
    const updatedBoards = [...boards];
    updatedBoards[boardIndex].cards.push(newCard); // Adicionando o card ao board selecionado

    setBoards(updatedBoards); // Atualizando o estado dos boards
    setShowModal(false); // Fechar o modal
    setNewCardData({ title: '', description: '', status: 'Nulo', priority: '', dueDate: '' }); // Limpar os dados do card
  };

  return (
    <DragDropContext onDragEnd={onDragEnd}>
      <div className="board-container">
        <h2>Boards</h2>

        {/* Input para o nome do board */}
        <div className="create-board-container">
          <input
            type="text"
            placeholder="Digite o nome do board"
            value={boardName}
            onChange={(e) => setBoardName(e.target.value)}
          />
          <button onClick={createBoard}>Criar Board</button>
        </div>

        <div className="board-list">
          {boards.length > 0 ? boards.map(board => (
            <Droppable key={board.id} droppableId={board.id}>
              {(provided) => (
                <div
                  className="board-item"
                  ref={provided.innerRef}
                  {...provided.droppableProps}
                >
                  <h3 className="board-title">{board.name}</h3>
                  <div className="card-container">
                    {board.cards.map((card, index) => (
                      <Card key={card.title} card={card} index={index} />
                    ))}
                  </div>
                  <button onClick={() => openModal(board.id)}>Adicionar Card</button>
                  {provided.placeholder}
                </div>
              )}
            </Droppable>
          )) : (
            <p>No boards available. Create a new board first.</p>
          )}
        </div>
      </div>

      {/* Modal para adicionar um novo card */}
      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <h2>Adicionar Novo Card</h2>
            <form onSubmit={handleCardSubmit}>
              <label>
                Título:
                <input
                  type="text"
                  name="title"
                  value={newCardData.title}
                  onChange={handleCardDataChange}
                  required
                />
              </label>
              <label>
                Descrição:
                <textarea
                  name="description"
                  value={newCardData.description}
                  onChange={handleCardDataChange}
                  required
                />
              </label>
              <label>
                Status:
                <input
                  type="text"
                  name="status"
                  value={newCardData.status}
                  onChange={handleCardDataChange}
                />
              </label>
              <label>
                Prioridade:
                <input
                  type="text"
                  name="priority"
                  value={newCardData.priority}
                  onChange={handleCardDataChange}
                />
              </label>
              <label>
                Data Limite:
                <input
                  type="date"
                  name="dueDate"
                  value={newCardData.dueDate}
                  onChange={handleCardDataChange}
                />
              </label>
              <button type="submit">Salvar</button>
              <button type="button" onClick={closeModal}>
                Fechar
              </button>
            </form>
          </div>
        </div>
      )}
    </DragDropContext>
  );
};

export default BoardList;
