import React, { useState, useEffect } from 'react';
import Navbar from "./Navbar";

const Basket = () => {

  //function generateSessionId() {
  //  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  //  const length = 32;
  //  let sessionId = '';
  //  for (let i = 0; i < length; i++) {
  //    sessionId += characters.charAt(Math.floor(Math.random() * characters.length));
  //  }
  //  return sessionId;
  //}
  
  //const sessionId = generateSessionId();

  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Lade die Daten vom Server
    fetch('http://localhost:8084/basket/view', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        const { products } = data;
        setProducts(products);
        console.log(data);
        setIsLoading(false);
      })
      .catch(error => {
        setError(error);
        setIsLoading(false);
      });
  }, []); // Leere Abhängigkeitsliste bedeutet, dass dieser Effekt nur einmal nach dem Rendern ausgeführt wird

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }
  return (
    <div>
      <Navbar/>
      <h2>Basket</h2>
      <ul>
        {products.map(product => (
          <li key={product.id}>
            ID: {product.id}, Price: {product.price}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Basket;