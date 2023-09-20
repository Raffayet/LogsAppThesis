import React from 'react';
import { useState, useEffect } from 'react';
import fetchData from '../utils/fetchData';
import { SERVER_PATH } from '../utils/environment';
import './users.css'
import { Link } from 'react-router-dom';

const CONTEXT_PATH = 'user';

const Users = () => {

	const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

	useEffect(() => {
    async function fetchDataFromApi() {
      try {
        const responseData = await fetchData(SERVER_PATH + CONTEXT_PATH + '/get-users'); // Use the fetchData function
        setData(responseData);
        setLoading(false);
      } catch (error) {
        setError(error);
        setLoading(false);
      }
    }

    fetchDataFromApi(); // Call the fetchDataFromApi function
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