import React from 'react';
import { Link } from 'react-router-dom';
import "./Navbar.css";
import { BsBasket3 } from "react-icons/bs";

function Navbar() {
  return (
    <nav className='navbar-main'>
      <Link className='mainLogo-Design' to="/">Pokemon Card Shop</Link>
      <Link to="/Basket">
        <BsBasket3 className="basket-icon" />
      </Link>
    </nav>
  );
}

export default Navbar;