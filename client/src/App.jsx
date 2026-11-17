import React, { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import Header from './components/header.jsx';
import Footer from './components/footer.jsx';
import Home from './pages/Home.jsx';
import ProductPage from './pages/ProductPage.jsx';
import CartPage from './pages/CartPage.jsx';
import Notification from './components/Notification.jsx';
import Login from './pages/Login.jsx'; // Import actual Login component
import Register from './pages/Register.jsx'; // Import actual Register component
import cart from './utils/cart.js'; // Import the cart object


function App() {
  const [notification, setNotification] = useState({
    show: false,
    message: '',
    x: 0,
    y: 0,
  });

  const [cartItemCount, setCartItemCount] = useState(cart.getTotalItems());

  useEffect(() => {
    const unsubscribe = cart.subscribe(() => {
      setCartItemCount(cart.getTotalItems());
    });
    return () => unsubscribe();
  }, []);

  return (
      <React.Fragment>
        <Header cartItemCount={cartItemCount} />
        <Notification notification={notification} />
        <main>
          <Routes>
            <Route path="/" element={<Home setNotification={setNotification} />} />
            <Route path="/produtos/:id" element={<ProductPage setNotification={setNotification} />} />
            <Route path="/carrinho" element={<CartPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/cadastro" element={<Register />} />
          </Routes>
        </main>
        <Footer />
      </React.Fragment>
  );
}

export default App;
