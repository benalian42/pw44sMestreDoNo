import React from 'react';
import './footer.css'; // Import the CSS file

function Footer() {
  return (
    <footer className="footer">
      <div className="footer-container">
        <p>&copy; 2025 Mestre do Nó. Todos os direitos reservados.</p>
        <p>Contato: contato@mestredono.com</p>
        <div className="social-links">
          <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">Instagram</a>
          <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">Facebook</a>
        </div>
        <div className='cards'>
          <div>
            <i className='bi bi-credit-card'>ELO</i>
          </div>
          <div>
            <i className='bi bi-credit-card'>Master</i>
          </div>
          <div>
            <i className='bi bi-credit-card'>Visa</i>
          </div>
        </div>
        
      </div>
    </footer>
  );
}

export default Footer;