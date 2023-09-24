import React from 'react'
import { Link } from 'react-router-dom'
import './dashboard.css'

const Dashboard = () => {

	const menuItems = ['users', 'logs', 'create-log'];

	const capitalizeFirstLetter = (string) => {
    	return string.charAt(0).toUpperCase() + string.slice(1);
	}

	return (
		<div>
			<div>WELCOME</div>
				<div className='links'>
						{menuItems.map((menuItem, id) => <Link key={id} className='link' to={menuItem}>{capitalizeFirstLetter(menuItem)}</Link>)}
				</div>
		</div>

	)
}

export default Dashboard