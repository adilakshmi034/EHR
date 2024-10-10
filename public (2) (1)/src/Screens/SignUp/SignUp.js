import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from "sweetalert2";

const SignUp = () => {
  const location = useLocation();
  const { plan } = location.state || {};

  const navigate = useNavigate();

  const [mobileNumber, setMobileNumber] = useState(0);
  const [email, setEmail] = useState("");
  const [fullName, setFullName] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const saveUser = (event) => {
    event.preventDefault();
    setIsLoading(true);

    const formData = new FormData();
    formData.append("email", email);
    formData.append("fullName", fullName);
    formData.append("mobileNumber", mobileNumber);
    formData.append("subscription_id", Number(plan.subscription_Id));
    setIsLoading(true);

    try {
      axios
        .post(`http://localhost:8080/api/users/register`, formData)
        .then((response) => {
          setIsLoading(false);
          Swal.fire({
            icon: "success",
            title: "Login Added Successfullyl",
            text: "The Login has been successfully added!",
          });

          navigate("/login", { state: { plan } });
        })
        .catch((error) => {
          setIsLoading(false);
          Swal.fire({
            icon: "error",
            title: "Failed to add job. Please try again",
            text: error || "Failed to add job. Please try again.",
          });
        });
    } catch (error) {
      setIsLoading(false);
      Swal.fire({
        icon: "error",
        title: "Failed to add job. Please try again",
        text: error || "Failed to add job. Please try again.",
      });
    }
    setIsLoading(false);
  };

  return (
    <div className="vh-100  d-flex justify-content-center align-items-center">
      <form className="card p-3" onSubmit={saveUser}>
        <h5 className="text-center">SIGN IN</h5>

        <div class="form-group">
          <label for="exampleInputPassword2">Selected Plan Type</label> :{" "}
          {plan?.planType}
        </div>

        <div class="form-group">
          <label for="subscriptionPlan">
            Selected Subscription Plan (&#8377;) :
          </label>
          {plan?.amount}
        </div>

        <hr></hr>

        <div class="form-group">
          <label for="exampleInputEmail1">Email address</label>
          <input
            type="email"
            class="form-control"
            id="exampleInputEmail1"
            aria-describedby="emailHelp"
            placeholder="Enter email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
          />
          <small id="emailHelp" class="form-text text-muted">
            We'll never share your email with anyone else.
          </small>
        </div>

        <div class="form-group">
          <label for="exampleInputPassword1">Full Name</label>
          <input
            type="text"
            class="form-control"
            id="exampleInputPassword1"
            placeholder="full name"
            onChange={(event) => setFullName(event.target.value)}
          />
        </div>

        <div class="form-group mb-2">
          <label for="mobileNumber">Mobile Number</label>
          <input
            type="number"
            class="form-control"
            id="mobileNumber"
            placeholder="mobile number"
            value={mobileNumber}
            onChange={(event) => setMobileNumber(event.target.value)}
          />
        </div>

        <button type="submit" class="btn btn-primary">
          {isLoading ? (
            <>
              <div class="spinner-border text-success" role="status">
                <span class="sr-only"></span>
              </div>
            </>
          ) : (
            <>Submit</>
          )}
        </button>
      </form>
    </div>
  );
};

export default SignUp;
