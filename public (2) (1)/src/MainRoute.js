import React from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./Screens/Home/Home";
import SignUp from "./Screens/SignUp/SignUp";
import LoginPage from "./Screens/Login/LoginPage";
import AddJobDetails from "./Components/AddJobDetails/AddJobDetails";
import TableData from "./Components/Table/TableData";
import InterviewLogin from "./Interview Panel/InterviewLogin";
import Exam from "./Interview Panel/Exam";
import Instructions from "./Interview Panel/Instructions";
import Dummy from "./Interview Panel/Dummy";

const MainRoute = () => {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<LoginPage />} />
        {/* <Route path="/hrdashboard" element={<HrDashboard />} /> */}
        <Route path="/addJobDetails" element={<AddJobDetails />} />
        <Route path="/listJobDetails" element={<TableData />} />
        <Route path="/interviewlogin" element={<InterviewLogin />}></Route>
        <Route path="/instructions" element={<Instructions />} ></Route>
        <Route path="/exam/:emailid" element={<Exam />} />
        <Route path="/dummy" element={<Dummy />} />
      </Routes>
    </div>
  );
};

export default MainRoute;