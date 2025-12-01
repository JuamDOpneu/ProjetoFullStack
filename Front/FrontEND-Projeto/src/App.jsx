import React from 'react';
import Layout from './components/Layout';
import { AuthProvider } from './contexts/AuthContext';
import { ThemeProvider } from './contexts/ThemeContext';

function App() {
  return (
    <AuthProvider>
      <ThemeProvider>
        <Layout />
      </ThemeProvider>
    </AuthProvider>
  );
}

export default App;