import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import cart from '../utils/cart';
import './ProductPage.css';

function ProductPage({ setNotification }) {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [mainImage, setMainImage] = useState('');
  const [cep, setCep] = useState('');
  const [frete, setfrete] = useState(null);

  useEffect(() => {
    fetch('/produtos.json')
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {
        const foundProduct = data.find(p => p.id === parseInt(id));
        if (foundProduct) {
          setProduct(foundProduct);
          setMainImage(foundProduct.image); // Set initial main image
        } else {
          setError(new Error('Produto não encontrado.'));
        }
        setLoading(false);
      })
      .catch(error => {
        setError(error);
        setLoading(false);
      });
  }, [id]);

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

  const handleCepChange = (e) => {
    setCep(e.target.value);
  };

  const handleCalculateFreight = () => {
    if (cep.length === 8 && !isNaN(cep)) {
      const estimatedCost = (parseInt(cep.substring(0, 2)) % 5) * 5 + 10; // Simple simulation
      setfrete(estimatedCost);
    } else {
      setfrete(null);
      alert('Frete inválido');
    }
  };

  if (loading) {
    return <div className="container text-center mt-5">Carregando produto...</div>;
  }

  if (error) {
    return <div className="container text-center mt-5">Erro: {error.message}</div>;
  }

  if (!product) {
    return <div className="container text-center mt-5">Produto não encontrado.</div>;
  }

  const images = [product.image, product.image, product.image]; 

  return (
    <div className="container my-5">
      <div className="row">
        <div className="col-md-6 product-image-gallery">
          <img src={mainImage} alt={product.name} className="img-fluid rounded main-image" />
          <div className="thumbnail-gallery d-flex gap-2 mt-2">
            {images.map((img, index) => (
              <img
                key={index}
                src={img}
                alt={`${product.name} thumbnail ${index + 1}`}
                className={`thumbnail img-thumbnail ${img === mainImage ? 'active' : ''}`}
                onClick={() => setMainImage(img)}
              />
            ))}
          </div>
        </div>
        <div className="col-md-6 product-info">
          <h1>{product.name}</h1>
          <p className="price">R$ {product.price.toFixed(2)}</p>
          <p className="description">{product.description}</p>
          
          <div className="freight-calculator my-4">
            <h3>Calcular Frete</h3>
            <div className="input-group">
              <input
                type="text"
                className="form-control"
                placeholder="Digite seu CEP (8 dígitos)"
                value={cep}
                onChange={handleCepChange}
                maxLength="8"
              />
              <button className="btn btn-outline-secondary" onClick={handleCalculateFreight}>Calcular</button>
            </div>
            {frete !== null && (
              <p className="mt-2">Frete estimado: R$ {frete.toFixed(2)}</p>
            )}
          </div>

          <button className="btn btn-primary btn-lg add-to-cart-button" onClick={handleAddToCart}>Adicionar ao Carrinho</button>
        </div>
      </div>
    </div>
  );
}

export default ProductPage;