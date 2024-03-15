import React, { useState, useEffect } from 'react';
import Navbar from "./Navbar";
import BasketCounterSection from "./BasketCounterSection";
import { Link } from 'react-router-dom';
import "./Basket.css";

const Basket = () => {

  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Lade die Daten vom Server
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

  const idCounts = products.reduce((acc, product) => {
    acc[product.id] = (acc[product.id] || 0) + 1; // Inkrementiere den Counter für die ID oder initialisiere ihn auf 1, wenn die ID zum ersten Mal gefunden wird
    return acc;
  }, {});

  const updatedProducts = products.map(product => {
      return { ...product, counter: idCounts[product.id] }; // Füge den Counter zum Produktobjekt hinzu
  });

  const uniqueUpdatedProducts = updatedProducts.filter((product, index, self) =>
    index === self.findIndex((p) => (
        p.id === product.id
    ))
  );

  console.log(uniqueUpdatedProducts);

  return (
    <div>
      <Navbar/>
      <h2>Items within your Basket</h2>
      <ul className="basket-product-list">
        {uniqueUpdatedProducts.map(product => (
        <li key={product.id} className="product-card">
        <img src={`${process.env.PUBLIC_URL}/publicImages/${product.name}_Card.jpg`} alt={product.name} />
        <BasketCounterSection counter={product.counter}/>
        </li>
      ))}
      </ul>
      <Link to="/Checkout">
        <button>Proceed to Checkout</button>
      </Link>
    </div>
  );
};

export default Basket;

