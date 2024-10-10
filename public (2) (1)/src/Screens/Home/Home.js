import React, { useEffect, useState } from "react";
import "./Home.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Home = () => {
  const navigate = useNavigate();
  const [plans, setPlans] = useState([]);

  const getSubscriptionPlans = () => {
    axios
      .get(`http://localhost:8080/api/subscriptions/getAll`)
      .then((response) => {
        console.log(response.data);

        setPlans(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    getSubscriptionPlans();
  }, []);

  const upgradePlans = (plan) => {
    console.log(plan);
    
    navigate("/signup", { state: { plan } });
  };

  return (
    <div class="plansparent">
      <div id="pricing-table">
        <div class="smaller"></div>

        {plans?.map((plan, index) => {
          return (
            <div key={plan?.subscription_Id}>
              <div class="plan plan3">
                <div class="header">{plan?.planType}</div>
                <div class="price">&#8377; {plan?.amount}</div>
                <div class="monthly">per month</div>
                <ul>
                  <li>
                    <pre>Description</pre>
                    <p>{plan?.discription}</p>
                  </li>
                  <li>
                    <pre>Additional Features</pre>
                    <p>{plan?.additionalFeatures}</p>
                  </li>
                </ul>
                <a class="signup" onClick={() => upgradePlans(plan)}>
                  Sign up
                </a>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Home;