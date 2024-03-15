import './App.css';
//import React, { useState, useEffect } from 'react';
import { Route, Routes } from "react-router-dom";
import Product from "./components/Product";
import Home from "./components/Home";
import Basket from './components/Basket';
import Checkout from './components/Checkout';

function App() {
  //const [products, setProducts] = useState([]);

  //useEffect(() => {
  //  fetch('/api/products')
  //    .then(response => response.json())
  //    .then(data => {
  //      setProducts(data);
  //    })
  //    .catch(error => console.error('Error fetching data:', error));
  //}, []);

  return (
    <Routes>
      <Route path="/Product/:productId" element={<Product/>} />
      <Route path="/Basket" element={<Basket/>} />
      <Route path="/Checkout" element={<Checkout/>} />
      <Route path="/" exact element={<Home />} />
    </Routes>
  );
}

export default App;
