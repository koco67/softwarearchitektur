import './App.css';
import React, { useState, useEffect } from 'react';
import { Route, Routes } from "react-router-dom";
import Product from "./components/Product";
import Home from "./components/Home";
import Basket from './components/Basket';

function App() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch('/api/products')
      .then(response => response.json())
      .then(data => {
        setProducts(data);
      })
      .catch(error => console.error('Error fetching data:', error));
  }, []);

  return (
    <Routes>
      <Route path="/Product/:productId" element={<Product/>} />
      <Route path="/Basket" element={<Basket/>} />
      <Route path="/" exact element={<Home />} />
    </Routes>
    //<div>
    //  <h1>Pokemon Card Shop</h1>
    //  <ul>
    //    {products.map((product, index) => (
    //      <li key={index}>
    //        <p>Name: {product.name}</p>
    //        <p>Description: {product.description}</p>
    //        <p>Price: {product.price}</p>
    //        <p>Inventory: {product.inventory}</p>
    //      </li>
    //    ))}
    //  </ul>
    //</div>
  );
}

export default App;
