import React from 'react';
import { Link } from 'react-router-dom';
import './header.css'; // Import the CSS file

function Header({ cartItemCount }) {
  return (
    <header className="header">
      <nav className="navbar navbar-expand-lg navbar-light" style={{ backgroundColor: 'var(--cor-primaria)' }}>
        <div className="container-fluid">
          <Link to="/" className="navbar-brand logo">
            <span>Mestre do</span>
            <img className="logo-image" src="src/assets/img/logo.png">
            </img>
          </Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link to="/" className="nav-link">Início</Link>
              </li>
              <li className="nav-item">
                <Link to="/produtos" className="nav-link">Produtos</Link>
              </li>
              <li className="nav-item">
                <Link to="/login" className="nav-link">Login</Link>
              </li>
              <li className="nav-item">
                <Link to="/cadastro" className="nav-link">Cadastro</Link>
              </li>
            </ul>
            <form className="d-flex search-bar">
              <input className="form-control me-2" type="search" placeholder="Buscar produtos..." aria-label="Buscar" />
              <button className="btn btn-outline-light" type="submit"><i className="bi bi-search"></i></button>
            </form>
            <Link to="/carrinho" className="nav-link cart-icon mx-1">
              <i className="bi bi-cart"></i>
              {cartItemCount > 0 && <span className="badge bg-danger ms-1">{cartItemCount}</span>}
            </Link>
          </div>
        </div>
        <div className="mobilePanel disabled">
          teste
        </div>
      </nav>
    </header>
  );
}

export default Header;