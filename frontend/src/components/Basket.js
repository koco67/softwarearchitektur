import React from "react";
import Navbar from './Navbar';
import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';

const Basket = () => {
    const [basket, setBasket] = useState([]);

    useEffect(() => {
      const userId = Cookies.get('userId');
      fetchBasket(userId);
    }, []);
  
    const fetchBasket = (userId) => {
      // API-Anfrage an den Basket Microservice, um den Warenkorb abzurufen
      // Verwenden Sie die Benutzer-ID aus dem Cookie, um den Warenkorb zuzuordnen
      fetch(`/api/basket/${userId}`)
        .then(response => response.json())
        .then(data => setBasket(data));
    };
  
    const addToBasket = (productId, quantity) => {
      // Logik zum Hinzufügen eines Produkts zum Warenkorb
      // Aktualisieren Sie den lokalen Zustand und senden Sie eine Anfrage an den Server, um den Warenkorb zu aktualisieren
    };
  
    const removeFromBasket = (productId, quantity) => {
      // Logik zum Entfernen eines Produkts aus dem Warenkorb
      // Aktualisieren Sie den lokalen Zustand und senden Sie eine Anfrage an den Server, um den Warenkorb zu aktualisieren
    };
    return (
        <div>
            <Navbar />
            This will be the Page for the Basket
        </div>
    );
};

export default Basket;