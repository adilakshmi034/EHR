import React from "react";
import "./Intructions.css";
import { useNavigate } from "react-router-dom";

const Instructions = () => {
  const navigate = useNavigate();

  return (
    <div className="">
      <header className="text-center py-4 bg-primary text-white">
        <h1>INSTRUCTIONS</h1>
      </header>

      <div className="row my-5">
        <div className="col">
          <div className="inside p-4 bg-light rounded shadow-sm">
            <h2 className="text-center mb-4">Please Read Carefully</h2>
            <div className="entry-content">
              <p>&#9642; Do not refresh the page</p>
              <p>&#9642; Do not exit exam directly with out submitting </p>
              <p>&#9642; Cannot go to previous or next question </p>
            </div>
          </div>
        </div>
      </div>

      <footer className="text-center footer py-4 bg-dark text-white">
        <p><span className="text-danger">*</span> Click to start the exam </p>
        <button className="btn btn-outline-info" onClick={() => navigate("/exam")}>
          Start
        </button>
      </footer>
    </div>
  );
};

export default Instructions;