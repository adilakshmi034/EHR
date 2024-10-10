import React from "react";
import { useState } from "react";
import Swal from "sweetalert2";
function AddJobDetails() {

  const [jobTitle, setjobTitle] = useState("");
  const [jobkeyskills, setjobkeyskills] = useState([]);
  const [yearsOfExperience, setyearsOfExperience] = useState("");
const storedId=localStorage.getItem('user_id');

  const handleaddJob = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/api/JobDetails/addjob/${storedId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json", // or 'application/json'
        },
        body: JSON.stringify({
            jobTitle: jobTitle,
          jobkeyskills: jobkeyskills,
          yearsOfExperience:yearsOfExperience
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
      //  setErrorMessage(errorData.error || "Login failed. Please try again.");
        Swal.fire({
          icon: 'error',
          title: 'Failed to add job. Please try again',
          text: errorData.error || "Failed to add job. Please try again.",
        });
      } else {
        const data = await response.json();
        // Handle successful login, e.g., store JWT token, redirect to another page, etc.
        console.log("Login successful:", data);
        Swal.fire({
          icon: 'success',
          title: 'Job Added Successfullyl',
          text: 'The job has been successfully added!',
        })
      

      }
    } catch (error) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'An error occurred. Please try again.',
      });
      // setErrorMessage("An error occurred. Please try again.");
      console.error("Job addition error:", error);
    }
  };

  const handleAddSkill = (skill) => {
    setjobkeyskills([...jobkeyskills, skill]);
  };

  return (
    <form onSubmit={handleaddJob}>
      <div className="form-group row mt-2">
        <label htmlFor="inputEmail3" className="col-sm-2 col-form-label">
          jobTitle
        </label>
        <div className="col-sm-10">
          <input
            type="text"
            className="form-control"
            id="inputEmail3"
            placeholder="Email"
            value={jobTitle}
            onChange={(e) => setjobTitle(e.target.value)}
          ></input>
        </div>
      </div>
      <div className="form-group row mt-2">
        <label htmlFor="inputPassword3" className="col-sm-2 col-form-label">
          jobkeyskills
        </label>
        <div className="col-sm-10">
         <input
          type="text"
          
          onKeyDown={(e) => {
            if (e.key === 'Enter' && e.target.value.trim() !== "") {
              e.preventDefault();
              handleAddSkill(e.target.value.trim());
              e.target.value = '';
            }
          }}
          placeholder="Enter a skill and press Enter"
        />
     
        </div>
      </div>
      <div className="form-group row mt-2">
        <label htmlFor="inputPassword3" className="col-sm-2 col-form-label">
          YearsOfExperience
        </label>
        <div className="col-sm-10">
          <input
            type="number"
            className="form-control"
            value={yearsOfExperience}
            onChange={(e) => setyearsOfExperience(e.target.value)}
            placeholder="YearsOfExperience"
          />
        </div>
      </div>

      <div className="form-group row">
        <div className="col-sm-10">
          <button type="submit" className="btn btn-primary">
            Add Job Details
          </button>
        </div>
      </div>
    </form>
  );
}

export default AddJobDetails;
