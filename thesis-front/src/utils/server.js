const express = require('express');
const cors = require('cors');

const app = express();

// Enable CORS for all routes
app.use(cors());

// Your routes and server configuration go here...

app.listen(8082, () => {
  console.log('Server is running on port 8082');
});