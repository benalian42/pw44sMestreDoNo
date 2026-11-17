import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'

import App from './App.jsx'

//importacoes basicas do prime
import 'primereact/resources/primereact.min.css';                // Core CSS
import 'primeicons/primeicons.css';                              // Ícones

import './index.css'
// 4. (Opcional) PrimeFlex para ajudar com classes utilitárias de layout (flexbox, margens, etc.)
import 'primeflex/primeflex.css';


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>,
)
