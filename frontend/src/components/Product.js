import React, { useState, useEffect } from "react";
import { useParams } from 'react-router-dom';
import "./Product.css"

const Product = () => {

    const { productId } = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        fetch(`/api/products/${productId}`)
          .then(response => response.json())
          .then(data => setProduct(data))
          .catch(error => console.error('Error fetching product:', error));
      }, [productId]);
    
      if (!product) {
        return <div>Loading...</div>;
      }

    return (
      <div className="product-box">
        <h1>{product.name}</h1>
        <p>{product.description}</p>
        <p>Price: ${product.price}</p>
        <p>Inventory: {product.inventory}</p>
      </div>
    );
};

export default Product;