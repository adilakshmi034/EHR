// src/components/Login.js
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import './InterviewLogin.css'

const InterviewLogin = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();

   navigate('/exam')
  };

  return (

    <div className="vh-100 d-flex justify-content-center align-items-center ">
   
    <div className="card p-5">
      <h2>Interview Login</h2>
      <form onSubmit={handleLogin}>
      <div class="form-group">
      <label>Username:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            placeholder="user name"
            class="form-control" 
          />
        </div>
        <div class="form-group">
        <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            placeholder="password"
            class="form-control" 
          />
        </div>
        <button className="btn btn-primary" type="submit">Login</button>
      </form>
    </div>
   
    </div>

  );
};

export default InterviewLogin;