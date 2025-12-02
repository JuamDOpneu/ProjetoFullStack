import React, { useState, useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import { useNavigate, Link } from 'react-router-dom';
import Button from '../components/Button';
import Input from '../components/Input';

function LoginPage() {
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await login(formData.email, formData.password);
      navigate('/profile'); 
    } catch (err) {
      console.error(err);
      setError('Email ou senha inválidos.');
    }
  };

  return (
    <div className="form-page">
      <h2 style={{textAlign: 'center'}}>Login</h2>
      
      {error && <p className="error-message">{error}</p>}
      
      <form onSubmit={handleSubmit}>
        <div className="form-group">
            <Input label="Email" name="email" type="email" value={formData.email} onChange={handleChange} />
        </div>
        <div className="form-group">
            <Input label="Senha" name="password" type="password" value={formData.password} onChange={handleChange} />
        </div>
        
        <div style={{marginTop: '1.5rem', display: 'flex', flexDirection: 'column', gap: '1rem'}}>
          <Button type="submit" style={{width: '100%'}}>Entrar</Button>
        </div>
      </form>

      <div style={{marginTop: '2rem', textAlign: 'center', fontSize: '0.9rem'}}>
        <p>Não é cadastrado?</p>
        <Link 
          to="/register" 
          style={{
            color: '#007bff', 
            fontWeight: 'bold', 
            textDecoration: 'none',
            fontSize: '1rem'
          }}
        >
          CLIQUE AQUI
        </Link>
      </div>
    </div>
  );
}
export default LoginPage;
