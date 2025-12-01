import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../services/api';
import Button from '../components/Button';
import Input from '../components/Input';

function RegisterPage() {
  const [formData, setFormData] = useState({ name: '', email: '', password: '' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/auth/register', formData);
      alert('Cadastro realizado com sucesso! Faça login.');
      navigate('/login');
    } catch (err) {
      setError('Erro ao cadastrar. Verifique os dados ou tente outro email.');
    }
  };

  return (
    <div className="form-page">
      <h2 style={{textAlign: 'center'}}>Criar Conta</h2>
      
      {error && <p className="error-message">{error}</p>}
      
      <form onSubmit={handleSubmit}>
        <div className="form-group">
            <Input label="Nome" name="name" value={formData.name} onChange={handleChange} />
        </div>
        <div className="form-group">
            <Input label="Email" name="email" type="email" value={formData.email} onChange={handleChange} />
        </div>
        <div className="form-group">
            <Input label="Senha" name="password" type="password" value={formData.password} onChange={handleChange} />
        </div>
        
        <div style={{marginTop: '1.5rem'}}>
          <Button type="submit" variant="success" style={{width: '100%'}}>Cadastrar</Button>
        </div>
      </form>

      {/* --- Link de volta para Login --- */}
      <div style={{marginTop: '2rem', textAlign: 'center', fontSize: '0.9rem'}}>
        <p>Já tem uma conta?</p>
        <Link 
          to="/login" 
          style={{
            color: '#007bff', 
            fontWeight: 'bold', 
            textDecoration: 'none',
            fontSize: '1rem'
          }}
        >
          FAZER LOGIN
        </Link>
      </div>
    </div>
  );
}
export default RegisterPage;