import './App.css';
import React, { useState, useEffect } from 'react';
import { Route, Routes } from "react-router-dom";
import Product from "./Components/Product";
import Home from "./Components/Home";

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
      <Route path="Components" element={<Product />}/>
      <Route path="Home" element={<Home />}/>
      <Route path="/"/>
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
