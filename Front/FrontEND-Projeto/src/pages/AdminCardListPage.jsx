import React, { useState, useEffect } from 'react';
import { getCards, deleteCard } from '../services/cardService.js';
import { useNavigate } from 'react-router-dom';
import Button from '../components/Button';
import LoadingSpinner from '../components/LoadingSpinner';

function AdminCardListPage() {
  const [cards, setCards] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  // Função para buscar as cartas
  const fetchCards = async () => {
    try {
      setLoading(true);
      const response = await getCards(); // Busca todas as cartas
      setCards(response.data);
    } catch (err) {
      setError('Erro ao carregar cartas.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCards();
  }, []);

  // Função para deletar
  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir esta carta?')) {
      try {
        await deleteCard(id);
        // Remove da lista visualmente sem precisar recarregar tudo
        setCards(cards.filter(card => card.id !== id));
      } catch (err) {
        alert('Erro ao excluir carta.');
      }
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div className="admin-page">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <h2>Gerenciar Cartas</h2>
        <Button variant="success" onClick={() => navigate('/admin/new')}>
          + Nova Carta
        </Button>
      </div>

      {error && <p className="error-message">{error}</p>}

      {cards.length === 0 ? (
        <div style={{ textAlign: 'center', padding: '3rem', color: '#888' }}>
          <h3>Nenhuma carta encontrada.</h3>
          <p>Clique em "+ Nova Carta" para começar.</p>
        </div>
      ) : (
        <div className="admin-card-grid">
          {cards.map((card) => (
            <div key={card.id} className="admin-card-item">
              {/* Imagem */}
              <img 
                src={card.imageUrl} 
                alt={card.name} 
                className="admin-card-image"
                onError={(e) => { e.target.src = 'https://via.placeholder.com/150?text=Sem+Imagem'; }} // Fallback se a imagem quebrar
              />
              
              {/* Conteúdo */}
              <div className="admin-card-content">
                <h3 className="admin-card-title">{card.name}</h3>
                <span className="admin-card-theme">{card.theme}</span>
              </div>

              {/* Botões de Ação */
              <div className="admin-card-actions">
                <Button 
                  variant="primary" 
                  onClick={() => navigate(`/admin/edit/${card.id}`)}
                >
                  Editar
                </Button>
                <Button 
                  variant="danger" 
                  onClick={() => handleDelete(card.id)}
                >
                  Excluir
                </Button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default AdminCardListPage;
