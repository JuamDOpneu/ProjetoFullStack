import React, { useContext } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import { ThemeContext } from '../contexts/ThemeContext';

function Navigation() {
  const { user, logout } = useContext(AuthContext);
  const { theme, toggleTheme } = useContext(ThemeContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navigation">
      <NavLink to="/" end>Home</NavLink>
      <NavLink to="/game">Jogar</NavLink>
      
      {user ? (
        <>
            <NavLink to="/profile">Perfil</NavLink>
            <NavLink to="/admin">Admin</NavLink>
            <button 
              onClick={handleLogout} 
              style={{background:'none', border:'none', color:'var(--nav-text)', cursor:'pointer', fontSize: '1.1rem', fontWeight: '500'}}
            >
              Sair
            </button>
        </>
      ) : (
        <>
            {/* Apenas o botão de Login aparece agora */}
            <NavLink to="/login">Login</NavLink>
        </>
      )}

      <button onClick={toggleTheme} className="theme-toggle-btn" title="Alternar Tema">
        {theme === 'light' ? '🌙' : '☀️'}
      </button>
    </nav>
  );
}

export default Navigation;