import React, { useState, useEffect } from "react";
import { useParams } from 'react-router-dom';
import "./Product.css"
import Navbar from "./Navbar";

const Product = () => {

    const { productId } = useParams();
    const [product, setProduct] = useState(null);
    const [imageSrc, setImageSrc] = useState(null);

    useEffect(() => {
        fetch(`/api/products/${productId}`)
          .then(response => response.json())
          .then(data => setProduct(data))
          .catch(error => console.error('Error fetching product:', error));
      }, [productId]);
    
      if (!product) {
        return <div>Loading...</div>;
      }

      const selectedImage = product.name+"_Card.jpg";

      import(`../images/${selectedImage}`).then(imageModule => {
        setImageSrc(imageModule.default);
      });

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
            <button onClick={() => console.log(`${product.name} added to Basket`)}>
              Add to Basket
            </button>
          </div>
          <img src={imageSrc} alt="Beschreibung des Bildes" />
        </div>
      </div>
    );
};

export default Product;