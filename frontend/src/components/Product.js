import React, { useState, useEffect } from "react";
import { useParams } from 'react-router-dom';
import "./Product.css"
import Navbar from "./Navbar";

const Product = () => {

    const { productId } = useParams(); //ID of the Pokemon-Card
    const [product, setProduct] = useState(null); //This Variable will be set to the Pokemon-Cards Data
    const [imageSrc, setImageSrc] = useState(null); //Setting the Image for the Pokemon-Card

    // GET-Product
    useEffect(() => {
        fetch(`http://localhost:8080/api/gateway/products/${productId}`)
          .then(response => response.json())
          .then(data => {
            setProduct(data);
          })
          .catch(error => console.error('Error fetching product:', error));
      }, [productId]);
    
      if (!product) {
        return <div>Loading...</div>;
      };

      const itemToBasket = ({
        name: product.name,
        id: product.id,
        price: product.price
      });

      const selectedImage = product.name+"_Card.jpg";

      import(`../images/${selectedImage}`).then(imageModule => {
        setImageSrc(imageModule.default);
      });


    // POST Product to Basket
    const postData = () => {

      fetch('http://localhost:8080/api/gateway/basket/add', {
        method: 'POST',
        mode: 'cors',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(itemToBasket)
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
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