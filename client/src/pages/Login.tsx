import React, { useState } from 'react';
import './Form.css';

function Login() {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState('');

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const validate = () => {
    let newErrors = {};
    if (!formData.email) {
      newErrors.email = 'E-mail é obrigatório';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'E-mail inválido';
    }
    if (!formData.password) {
      newErrors.password = 'Senha é obrigatória';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const validaEnvio = (e) => {
    e.preventDefault();
    setMessage('');
    if (validate()) {
      const users = JSON.parse(localStorage.getItem('users')) || [];
      const user = users.find(
        (u) => u.email === formData.email && u.password === formData.password
      );

      if (user) {
        localStorage.setItem('currentUser', JSON.stringify(user));
        setMessage('Login bem-sucedido!');
      } else {
        setMessage('E-mail ou senha inválidos.');
      }
    }
  };

  return (
    <div className="form-container">
      <h2>Login</h2>
      <form onSubmit={validaEnvio}>
        <div className="form-group">
          <label htmlFor="email">E-mails:</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleOnChange}
            className={errors.email ? 'input-error' : ''}
          />
          {errors.email && <p className="error-message">{errors.email}</p>}
        </div>
        <div className="form-group">
          <label htmlFor="password">Senha:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleOnChange}
            className={errors.password ? 'input-error' : ''}
          />
          {errors.password && <p className="error-message">{errors.password}</p>}
        </div>
        <button type="submit">Entrar</button>
      </form>
      {message && <p className="form-message">{message}</p>}
    </div>
  );
}

export default Login;
