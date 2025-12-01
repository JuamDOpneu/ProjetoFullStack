import React, { createContext, useState, useEffect } from 'react';
import api from '../services/api';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Ao recarregar a página, tenta recuperar o usuário
    const token = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');

    if (token && storedUser) {
      try {
        setUser(JSON.parse(storedUser));
        api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      } catch (error) {
        console.error("Erro ao ler usuário do localStorage", error);
        localStorage.clear(); // Limpa se estiver corrompido
      }
    }
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      console.log("Tentando logar com:", email);
      const response = await api.post('/auth/login', { email, password });
      
      console.log("Resposta do Backend:", response.data); // <--- OLHE O CONSOLE DO NAVEGADOR AQUI

      const { token, user } = response.data;

      if (!user) {
        console.error("ERRO: O Backend retornou o token, mas NÃO retornou o objeto 'user'!");
        alert("Erro no sistema: Usuário não identificado na resposta.");
        return;
      }

      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));
      
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      setUser(user); // Isso faz o botão de Perfil aparecer
      console.log("Login realizado com sucesso! Usuário setado:", user);

    } catch (error) {
      console.error("Erro no Login:", error);
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    delete api.defaults.headers.common['Authorization'];
    setUser(null);
  };

  const updateUser = (updatedData) => {
    setUser(updatedData);
    localStorage.setItem('user', JSON.stringify(updatedData));
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, updateUser, loading }}>
      {children}
    </AuthContext.Provider>
  );
};