import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; 
import Users from './users/Users';
import SupersetDashboard from './superset/SupersetDashboard';
import Dashboard from './dashboard/Dashboard';
import Logs from './logs/Logs';
import CreateLog from './logs/CreateLog';
import LogSupersetDashboard from './logs/logs-superset-dashboard/LogSupersetDashboard';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Dashboard/>}/>
          <Route path='logs' element={<Logs/>}/>
          <Route path="users" element={<Users/>}/>
          <Route path="users/dashboard" element={<SupersetDashboard/>}/>
          <Route path="create-log" element={<CreateLog/>}/>
          <Route path="logs/log-superset-dashboard" element={<LogSupersetDashboard/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
