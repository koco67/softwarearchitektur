import './App.css';
import React from './';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import Home from '../src/components/Home';
import Product from '../src/components/Product';
import Basket from '../src/components/Basket';

function App() {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/product">Product</Link>
            </li>
            <li>
              <Link to="/about">About Us</Link>
            </li>
          </ul>
        </nav>

        <Route path="/" exact component={Home} />
        <Route path="/product" component={Product} />
        <Route path="/basket" component={Basket} />
      </div>
    </Router>
  );
}

export default App;
