import React, { useState }  from 'react';
import "./BasketCounterSection.css";
import { BiSolidPlusCircle } from "react-icons/bi";
import { BiSolidMinusCircle } from "react-icons/bi";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';

function BasketCounterSection(props) {

  const [counter, setCounter] = useState(props.currentProduct.counter);

  const itemToOrOffBasket = ({
    name: props.currentProduct.name,
    id: props.currentProduct.id,
    price: props.currentProduct.price
  });

      // POST Product to Basket
      const postData = () => {
        fetch('http://localhost:8080/api/gateway/basket/add', {
          method: 'POST',
          mode: 'cors',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(itemToOrOffBasket)
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
        setCounter(counter + 1);
        toast.info('added to Basket!');
      };

      // DELETE Product from Basket
      const deleteData = () => {
        fetch('http://localhost:8080/api/gateway/basket/remove', {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
            // Weitere Header können hier hinzugefügt werden, z.B. Auth-Token
          },
          body: JSON.stringify(itemToOrOffBasket),
        })
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
        })
        .catch(error => {
          // Fehlerbehandlung für Fehler beim Senden der Anfrage
          console.error('Error deleting resource:', error);
        });
        setCounter(counter - 1);
        toast.info('removed from basket Basket!');
      };

  return (
    <div className='basketCounterSection-main'>
        <button className='plus-minus-icon' onClick={deleteData}><BiSolidMinusCircle /></button>
        <p className='item-counter-basket'>{counter}</p>
        <button className='plus-minus-icon' onClick={postData}><BiSolidPlusCircle /></button>
        <ToastContainer position="bottom-right" />
    </div>
  );
}

export default BasketCounterSection;