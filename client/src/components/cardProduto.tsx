import React from 'react';
import { Link } from 'react-router-dom';
import cart from '../utils/cart'; 
import './cardProduto.css'; 

function ProductCard({ product, setNotification }) {

  const handleAddToCart = (event) => {
    cart.add(product);
    setNotification({
      show: true,
      message: `${product.name} adicionado ao carrinho!`,
      x: event.clientX,
      y: event.clientY,
    });
    setTimeout(() => {
      setNotification((prev) => ({ ...prev, show: false }));
    }, 2000);
  };

  return (
    <div className="card h-100 product-card">
      <Link to={`/produtos/${product.id}`}>
        <img src={product.image} className="card-img-top" alt={product.name} />
      </Link>
      <div className="card-body d-flex flex-column">
        <h5 className="card-title">
          <Link to={`/produtos/${product.id}`}>{product.name}</Link>
        </h5>
        <p className="card-text">{product.description}</p>
        <p className="price mt-auto">R$ {product.price.toFixed(2)}</p>
        <button onClick={handleAddToCart} className="btn btn-primary">
          <i className="bi bi-cart-plus me-2"></i>Adicionar ao Carrinho
        </button>
      </div>
    </div>
  );
}

export default ProductCard;