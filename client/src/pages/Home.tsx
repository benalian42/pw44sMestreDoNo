import React, { useState, useEffect } from 'react';
import './Home.css'; 
import ProductCard from '../components/cardProduto.jsx';

function Home({ setNotification }) {
  const [allProducts, setAllProducts] = useState([]);
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('Todos');

  useEffect(() => {
    fetch('/produtos.json')
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {
        setAllProducts(data);
        setProducts(data);
        setLoading(false);

        const uniqueCategories = ['Todos', ...new Set(data.map(product => product.category))];
        setCategories(uniqueCategories);
      })
      .catch(error => {
        setError(error);
        setLoading(false);
      });
  }, []);

  useEffect(() => {
    if (selectedCategory === 'Todos') {
      setProducts(allProducts);
    } else {
      setProducts(allProducts.filter(product => product.category === selectedCategory));
    }
  }, [selectedCategory, allProducts]);

  if (loading) {
    return <div className="home-container">Carregando produtos...</div>;
  }

  if (error) {
    return <div className="home-container">Erro ao carregar produtos: {error.message}</div>;
  }

  return (
    <div className="container home-container">
      <h1 className="home-title">Nossos Produtos de Macramê</h1>

      <div className="category-menu">
        {categories.map(category => (
          <button
            key={category}
            className={`category-button ${selectedCategory === category ? 'active' : ''}`}
            onClick={() => setSelectedCategory(category)}
          >
            {category}
          </button>
        ))}
      </div>

      <div className="row">
        {products.map(product => (
          <div key={product.id} className="col-lg-3 col-md-4 col-sm-6 mb-4">
            <ProductCard product={product} setNotification={setNotification} />
          </div>
        ))}
      </div>
    </div>
  );
}

export default Home;