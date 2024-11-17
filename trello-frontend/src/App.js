
import './App.css';
import BoardList from './components/BoardList'; // Certifique-se de que o caminho está correto

function App() {
  return (
    <div className="App">
      <h1>Meu Trello</h1>
      {/* Renderizando o BoardList */}
      <BoardList />
    </div>
  );
}

export default App;
