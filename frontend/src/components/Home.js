import React from 'react';
import { FaShoppingBasket } from 'react-icons/fa';
import './Home.css'

function Home() {
  return (
    <nav className="home">
      <div className="nav-text">
        <p>Home</p>
      </div>
      <div className="search-bar">
        <input type="text" placeholder="Search..." />
        <button>Search</button>
      </div>
      <div className="basket-icon">
        <FaShoppingBasket />
      </div>
    </nav>
  );
}

export default Home;