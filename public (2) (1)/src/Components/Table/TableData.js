import React from 'react'
import axios from "axios";
import { useState } from "react";
import { useEffect } from "react";
function TableData() {
    const [jobDetails, setJobDetails] = useState([]);
    const storedId = localStorage.getItem("user_id");
  
    useEffect(() => {
      // Fetch data from the API endpoint
      axios
        .get(`http://localhost:8080/api/users/${storedId}`)
        .then((response) => {
          // Extract the addJobDetails array and store it in state
          setJobDetails(response.data.addJobDetails);
        })
        .catch((error) => {
          console.error("There was an error fetching the job details!", error);
        });
    });
  return (
<div>
      <h2>Job Details</h2>
      <table border="1">
        <thead>
          <tr>
            <th>Job ID</th>
            <th>Job Title</th>
            <th>Key Skills</th>
            <th>Created At</th>
            <th>Years of Experience</th>
          </tr>
        </thead>
        <tbody>
          {jobDetails.map((job, index) => (
            <tr key={index}>
              <td>{job.jobId}</td>
              <td>{job.jobTitle}</td>
              <td>{job.jobkeyskills.join(", ")}</td>
              <td>{job.createdAt}</td>
              <td>{job.yearsOfExperience}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
      )
}

export default TableData