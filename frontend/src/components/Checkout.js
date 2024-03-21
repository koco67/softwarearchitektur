import Navbar from "./Navbar";
import "./Checkout.css";
import React, { useState, useEffect } from 'react';

const Checkout = () => {

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
        <p className="total-text">The total of your sum:</p>
        <p className="sum-text">{totalSum}</p>
        <div className="payment-method-box">
          <p className="choice-text">Choose payment method:</p>
          <button className="payment-button">PayPal</button>
          <button className="payment-button">Credit Card</button>
          <button className="payment-button">Klarna</button>
        </div>
      </div>
    </div>
  );
};

export default Checkout;