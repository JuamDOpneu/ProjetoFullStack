import api from './api'; 

export const getCards = (params) => api.get('/cards', { params });
export const getCardById = (id) => api.get(`/cards/${id}`);

// NÃO coloque { headers: { 'Content-Type': 'application/json' } } aqui!
// O navegador detecta automaticamente que é FormData e configura sozinho.
export const createCard = (formData) => {
    return api.post('/cards', formData);
};

export const updateCard = (id, formData) => {
    return api.put(`/cards/${id}`, formData);
};

export const deleteCard = (id) => api.delete(`/cards/${id}`);


export const getDistinctThemes = () => api.get('/cards/themes');
