import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; 
import Users from './users/Users';
import SupersetDashboard from './superset/SupersetDashboard';
import Dashboard from './dashboard/Dashboard';
import Logs from './logs/Logs';
import CreateLog from './logs/CreateLog';
import LogSupersetDashboard from './logs/logs-superset-dashboard/LogSupersetDashboard';
import Login from './auth/Login';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Login/>}/>
          <Route path="/dashboard" element={<Dashboard/>}/>
          <Route path='/dashboard/logs' element={<Logs/>}/>
          <Route path="/dashboard/users" element={<Users/>}/>
          <Route path="users/dashboard" element={<SupersetDashboard/>}/>
          <Route path="/dashboard/create-log" element={<CreateLog/>}/>
          <Route path="logs/log-superset-dashboard" element={<LogSupersetDashboard/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
