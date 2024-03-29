import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import "./Home.css";
import Navbar from './Navbar';
import { useCookies } from 'react-cookie';


function Home() {
  const [products, setProducts] = useState([]);
  const [cookies, setCookie] = useCookies(['sessionID']);

  useEffect(() => {

    //Cookie is being set here
    if (!cookies.sessionID) {
      // Set the 'visited' cookie with a value
      setCookie('sessionID', '123', { path: '/' });
    }

    fetch('http://localhost:8080/api/gateway/products')
      .then(response => response.json())
      .then(data => setProducts(data))
      .catch(error => console.error('Error fetching products:', error));
  }, [cookies.sessionID, setCookie]);

  return (
    <div>
      <Navbar />
      <ul className="product-list">
        {products.map(product => (
          <li key={product.id} className="product-card">
            <Link to={`/Product/${product.id}`} className='headline-of-product'>{product.name}</Link>
            <p className='price-of-product'>{product.price}$</p>
            <Link to={`/Product/${product.id}`}>
              <img src={`${process.env.PUBLIC_URL}/publicImages/${product.name}_Card.jpg`} alt={product.name} className='product-image'/>
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Home;