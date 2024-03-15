import React from 'react';
//import { Link } from 'react-router-dom';
import "./BasketCounterSection.css";
import { BiSolidPlusCircle } from "react-icons/bi";
import { BiSolidMinusCircle } from "react-icons/bi";

function BasketCounterSection(props) {

    console.log(props.counter);

  return (
    <div className='basketCounterSection-main'>
        <button className='plus-minus-icon'><BiSolidMinusCircle /></button>
        <p className='item-counter-basket'>{props.counter}</p>
        <button className='plus-minus-icon'><BiSolidPlusCircle /></button>
    </div>
  );
}

export default BasketCounterSection;