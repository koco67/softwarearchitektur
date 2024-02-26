import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import "./Home.css";


function Home() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch('/api/products')
      .then(response => response.json())
      .then(data => setProducts(data))
      .catch(error => console.error('Error fetching products:', error));
  }, []);

  return (
    <div>
      <h1>Pokemon Card Shop</h1>
      <ul className="product-list">
        {products.map(product => (
          <li key={product.id} className="product-card">
            <Link to={`/Product/${product.id}`}>{product.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Home;