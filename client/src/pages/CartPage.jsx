import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import cart from '../utils/cart';
import './CartPage.css';

function CartPage() {
  const [cartItems, setCartItems] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const updateCart = () => {
      setCartItems([...cart.get()]);
    };
    updateCart();
    const unsubscribe = cart.subscribe(updateCart);
    return () => unsubscribe();
  }, []);

  const removeItem = (id) => cart.remove(id);
  const increaseQuantity = (item) => cart.add(item);
  const decreaseQuantity = (id) => cart.decreaseQuantity(id);
  const clearCart = () => cart.clear();
  const handleCheckout = () => navigate('/login');

  const subtotal = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
  const shipping = 20.00; // Example fixed shipping
  const total = subtotal + shipping;

  return (
    <div className="container my-5 cart-container">
      <h1 className="text-center mb-4 cart-title">Seu Carrinho de Compras</h1>

      {cartItems.length === 0 ? (
        <div className="text-center">
          <p>Seu carrinho está vazio. <Link to="/">Continue comprando</Link>.</p>
        </div>
      ) : (
        <div className="row">
          <div className="col-lg-8">
            <div className="table-responsive">
              <table className="table cart-items">
                <thead>
                  <tr>
                    <th scope="col">Produto</th>
                    <th scope="col">Preço</th>
                    <th scope="col" className="text-center">Quantidade</th>
                    <th scope="col" className="text-end">Total</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                  {cartItems.map(item => (
                    <tr key={item.id} className="cart-item-row">
                      <td>
                        <div className="d-flex align-items-center">
                          <img src={item.image} alt={item.name} className="cart-item-image me-3" />
                          <div>
                            <h5>{item.name}</h5>
                          </div>
                        </div>
                      </td>
                      <td>R$ {item.price.toFixed(2)}</td>
                      <td>
                        <div className="d-flex justify-content-center align-items-center">
                          <button className="btn btn-sm" onClick={() => decreaseQuantity(item.id)}><i className="bi bi-dash-circle"></i></button>
                          <span className="mx-2">{item.quantity}</span>
                          <button className="btn btn-sm" onClick={() => increaseQuantity(item)}><i className="bi bi-plus-circle"></i></button>
                        </div>
                      </td>
                      <td className="text-end">R$ {(item.price * item.quantity).toFixed(2)}</td>
                      <td className="text-end">
                        <button onClick={() => removeItem(item.id)} className="btn btn-sm btn-danger"><i className="bi bi-trash"></i></button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
          <div className="col-lg-4">
            <div className="card cart-summary">
              <div className="card-body">
                <h5 className="card-title">Resumo da Compra</h5>
                <ul className="list-group list-group-flush">
                  <li className="list-group-item d-flex justify-content-between">
                    <span>Subtotal:</span>
                    <span>R$ {subtotal.toFixed(2)}</span>
                  </li>
                  <li className="list-group-item d-flex justify-content-between">
                    <span>Frete:</span>
                    <span>R$ {shipping.toFixed(2)}</span>
                  </li>
                  <li className="list-group-item d-flex justify-content-between total">
                    <strong>Total:</strong>
                    <strong>R$ {total.toFixed(2)}</strong>
                  </li>
                </ul>
                <div className="d-grid gap-2 mt-4">
                  <button onClick={handleCheckout} className="btn btn-primary checkout-button">Finalizar Compra</button>
                  <Link to="/" className="btn btn-outline-secondary continue-shopping-button">Continuar Comprando</Link>
                  <button onClick={clearCart} className="btn btn-outline-danger clear-cart-button">
                    <i className="bi bi-trash me-2"></i>Limpar Carrinho
                   </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default CartPage;