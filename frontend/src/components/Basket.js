import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from "./Navbar";

const Basket = () => {
  const [basket, setBasket] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchBasket();
  }, []);

  const fetchBasket = () => {
    axios.get('/basket/view')
      .then(response => {
        setBasket(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching basket:', error);
        setLoading(false);
      });
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Navbar/>
      <h2>Basket</h2>
      {basket ? (
        <ul>
          {basket.products.map(product => (
            <li key={product.id}>
              {product.name} - Quantity: {product.quantity}
            </li>
          ))}
        </ul>
      ) : (
        <div>No items in basket</div>
      )}
    </div>
  );
};

export default Basket;