import React from 'react';

/**
 * Componente de Botão Reutilizável
 * * Props:
 * - children: O texto ou ícone dentro do botão.
 * - variant: 'primary' (azul), 'secondary' (cinza), 'success' (verde), 'danger' (vermelho).
 * - onClick: Função chamada ao clicar.
 * - disabled: Se true, bloqueia o clique.
 * - type: 'button', 'submit' ou 'reset'.
 * - style: Estilos inline extras (se precisar).
 */
function Button({ 
  children, 
  onClick, 
  variant = 'primary', // Se não informar, será azul
  type = 'button', 
  disabled = false,
  style = {}
}) {
  
  // Monta a classe CSS baseada na variante escolhida
  const className = `btn btn-${variant}`;

  return (
    <button 
      className={className} 
      onClick={onClick} 
      type={type}
      disabled={disabled}
      style={style}
    >
      {children}
    </button>
  );
}

export default Button;