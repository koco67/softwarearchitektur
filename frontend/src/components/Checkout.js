import Navbar from "./Navbar";
import "./Checkout.css";
import React, { useState, useEffect } from 'react';

const Checkout = () => {

  
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [totalSum, setTotalSum] = useState([]);

  useEffect(() => {
    // GET-REQUEST
    fetch('http://localhost:8080/api/gateway/basket', {
      method: 'GET',
      mode: 'cors',
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
        setIsLoading(false);
  
        // POST Basket to Checkout-Service and get Total in response
        fetch('http://localhost:8080/api/gateway/checkout/total', {
          method: 'POST',
          mode: 'cors',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(data)
        })
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          console.log('Response from server:', data);
          setTotalSum(data);
        })
        .catch(error => {
          console.error('There was a problem with your fetch operation:', error);
        });
      })
      .catch(error => {
        setError(error);
        setIsLoading(false);
      });
  }, []);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div>
      <Navbar/>
      <div className="checkout-box">
        <h1>Checkout</h1>
        The total of your sum: {totalSum}
        <p>Choose payment method:</p>
        <button>PayPal</button>
        <button>Credit Card</button>
        <button>Klarna</button>
      </div>
    </div>
  );
};

export default Checkout;