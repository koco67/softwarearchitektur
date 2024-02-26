import React, { useState } from "react";

const Product = () => {

    const [products, setProducts] = useState([]);

    return (
        <div>
            This will be the Page for a single Product
            <ul>
          {products.map((product, index) => (
            <li key={index}>
              <p>Name: {product.name}</p>
              <p>Description: {product.description}</p>
              <p>Price: {product.price}</p>
              <p>Inventory: {product.inventory}</p>
            </li>
            ))};
            </ul>
        </div>
    );
};

export default Product;