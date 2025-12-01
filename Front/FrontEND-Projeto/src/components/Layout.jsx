import React from 'react';
import { Outlet } from 'react-router-dom';
import Navigation from './Navigation';

function Layout() {
  return (
    <div className="app-container">
      <Navigation />
      
      <main className="content">
        <Outlet />
      </main>

      {/* AQUI: O rodapé precisa ter a classe 'footer' */}
      <footer className="footer">
        <p>© 2025 Jogo da Memória Full-Stack</p>
      </footer>
    </div>
  );
}

export default Layout;