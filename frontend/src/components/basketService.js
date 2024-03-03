import axios from 'axios';

const addToBasket = async (product) => {
    try {
        const response = await axios.post('/basket/add', product);
        return response.data;
    } catch (error) {
        console.error('Error adding product to basket:', error);
        throw error;
    }
};

export default addToBasket;