import React from 'react'
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import './logsTable.css';
import { Link } from 'react-router-dom';


export default function LogsTable({logMessages}) {
    console.log(logMessages)
  return (
    <div>
      <TableContainer component={Paper} className='table-container'>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead className='table-head'>
                <TableRow>
                  <TableCell align="center">Id</TableCell>
                  <TableCell align="center">Log Type</TableCell>
                  <TableCell align="center">Timestamp</TableCell>
                  <TableCell align="center">Log Value</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {logMessages.map((logMessage, index) => (
                  <TableRow
                    key={index}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                    <TableCell align="center">{logMessage.id}</TableCell>
                    <TableCell align="center">{logMessage.logType}</TableCell>
                    <TableCell align="center">{logMessage.timestamp}</TableCell>
                    <TableCell align="center">${logMessage.logValue}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <Link to='log-superset-dashboard'>Superset Dashboard</Link>
    </div>
    
  );
}