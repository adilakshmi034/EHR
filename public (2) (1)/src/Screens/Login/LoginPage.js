import React from "react";
import "./LoginPage.css";
import { useState } from "react";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom"; // Import useNavigate

function LoginPage() {
  const [emailOrMobileNumber, setEmailOrMobileNumber] = useState("");
  const [password, setPassword] = useState("");
 // const [errorMessage, setErrorMessage] = useState("");
 const navigate = useNavigate(); 
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/admin/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded", // or 'application/json'
        },
        body: new URLSearchParams({
          emailOrMobileNumber: emailOrMobileNumber,
          password: password,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
      //  setErrorMessage(errorData.error || "Login failed. Please try again.");
        Swal.fire({
          icon: 'error',
          title: 'Login Failed',
          text: errorData.error || "Login failed. Please try again.",
        });
      } else {
        const data = await response.json();
        // Handle successful login, e.g., store JWT token, redirect to another page, etc.
        console.log("Login successful:", data);
        Swal.fire({
          icon: 'success',
          title: 'Login Successful',
          text: 'You have successfully logged in!',
        }).then(() => {
          // Redirect to HR Dashboard
          navigate('/hrdashboard');
        });
      
       // setErrorMessage(data.error || "Login successful.");

      }
    } catch (error) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'An error occurred. Please try again.',
      });
      // setErrorMessage("An error occurred. Please try again.");
      console.error("Login error:", error);
    }
  };
  return (
    <div>
      <div className="row justify-content-center ">
        <div className="col-md-6 formdesign mt-5 text-center">
          <form onSubmit={handleLogin}>
            <div className="input-group form-floating mb-3 mt-3 ">
              <span className="input-group-text  " id="floatingInput">
                <i className="bi bi-envelope"></i>
              </span>
              <div className="form-floating ">
                <input
                  type="email"
                  className="form-control"
                  id="floatingInput"
                  placeholder="Email or mobilenumber"
                  value={emailOrMobileNumber}
                  onChange={(e) => setEmailOrMobileNumber(e.target.value)}
                  required
                ></input>
                <label htmlFor="floatingInput">Email OR MobileNumber</label>
                <div>
                  <small className="error">
                    Please Enter Email or MobileNumber
                  </small>

                  <small className="error">
                    Enter a Valid .gmail/.org Email Or Enter Valid MobileNumber
                  </small>
                </div>
              </div>
            </div>

            <div className="input-group mb-3">
              <span className="input-group-text">
                <i className="bi bi-lock"></i>
              </span>
              <div className="form-floating">
                <input
                  type="password"
                  className="form-control"
                  id="floatingPassword"
                  placeholder="password"
                  required
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                ></input>
                <label htmlFor="floatingPassword">password</label>
                <span className="eye-icon">
                  <i className="bi"></i>
                </span>

                <div>
                  <small className="error">Please Enter Password</small>
                </div>
              </div>
            </div>

            <div className="row">
              <div className="col">
                <button type="submit" className="btn sign btn-primary me-2">
                
                  SignIn
                </button>
                {/* <span
                    className="spinner-border spinner-border-sm"
                    role="status"
                    aria-hidden="true"
                  ></span> */}
              </div>
              <div className="col">
                <button
                  type="button"
                  className="btn btn-primary"
                >
                  Sign Up
                </button>
              </div>
            </div>
          </form>
       
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
