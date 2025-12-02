import React from 'react';

function MemoryCardComponent({ card, isFlipped, onClick }) {
  return (
    <div className={`memory-card ${isFlipped ? 'flipped' : ''}`} onClick={onClick}>
      <div className="card-inner">
        <div className="card-front">
          ?
        </div>
        <div className="card-back">
          <img src={card.imageUrl} alt={card.name} />
          <p>{card.name}</p>
        </div>
      </div>
    </div>
  );
}

export default MemoryCardComponent;