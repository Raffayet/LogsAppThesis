import React from 'react';
import { useState, useEffect } from 'react';
import { SERVER_PATH } from '../utils/environment';
import './users.css'
import { Link } from 'react-router-dom';
import axios from 'axios';

const CONTEXT_PATH = 'user';

const Users = () => {

	const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    try {
        const response = await axios.get(SERVER_PATH + CONTEXT_PATH + '/get-users');
        setData(response.data);
        setLoading(false);
    } catch (error) {
        setError(error);
    }
  };

	useEffect(() => {
    fetchData();
  }, []);

	return (
		<div>
      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p>Error: {error.message}</p>
      ) : (
				<Link to='dashboard'>Dashboard</Link>
      )}
    </div>
	)
}

export default Users