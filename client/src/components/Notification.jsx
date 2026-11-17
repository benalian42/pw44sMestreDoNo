import React from 'react';
import './Notification.css';

function Notification({ notification }) {
  const { show, message, x, y } = notification;

  const style = {
    left: `${x + 20}px`, // Position slightly to the right of the cursor
    top: `${y}px`,      // Position at the same vertical level as the cursor
  };

  return (
    <div className={`notification ${show ? 'show' : ''}`} style={style}>
      {message}
    </div>
  );
}

export default Notification;