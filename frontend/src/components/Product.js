import React, { useState, useEffect } from "react";
import { useParams } from 'react-router-dom';
import "./Product.css"
import Navbar from "./Navbar";
import addToBasket from "./basketService";

const Product = () => {

    const { productId } = useParams();
    const [product, setProduct] = useState(null);
    const [imageSrc, setImageSrc] = useState(null);

    const [formData, setFormData] = useState({
      id: '',
      price: ''
    });

    const [tempData, setTempData] = useState({
      id: 'testId',
      price: '12345678'
    });

    useEffect(() => {
        fetch(`/api/products/${productId}`)
          .then(response => response.json())
          .then(data => {
            setProduct(data);
            setFormData({
              id: data.id,
              price: data.price
            });
          })
          .catch(error => console.error('Error fetching product:', error));
      }, [productId]);
    
      if (!product) {
        return <div>Loading...</div>;
      };

      const selectedImage = product.name+"_Card.jpg";

      import(`../images/${selectedImage}`).then(imageModule => {
        setImageSrc(imageModule.default);
      });

      const handleAddToBasket = async () => {
        try {
            await addToBasket(product);
            console.log(`${product.name} added to basket.`);
        } catch (error) {
            console.error('Error adding product to basket:', error);
        }
      };

    // POST-Request
    const postData = () => {

      fetch('http://localhost:8084/basket/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Origin': 'http://localhost:3000/',
          'Access-Control-Request-Method': 'POST'
        },
        body: JSON.stringify(tempData)
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        // Verarbeite die Antwort des Servers hier
        console.log('Response from server:', data);
      })
      .catch(error => {
        console.error('There was a problem with your fetch operation:', error);
      });
    };

    return (
      <div>
        <Navbar />
        <div className="product-box">
          <div>
            <h1>{product.name}</h1>
            <p>{product.description}</p>
            <p>Price: ${product.price}</p>
            <p>Inventory: {product.inventory}</p>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <button onClick={postData}>
              Add to Basket
            </button>
          </div>
          <img src={imageSrc} alt="Beschreibung des Bildes" />
        </div>
      </div>
    );
};

export default Product;