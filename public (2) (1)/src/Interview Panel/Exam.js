import React, { useState, useRef, useEffect } from "react";
import "./Exam.css";
import Question from "./Question";
import { useParams } from "react-router-dom";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import { Modal, Button } from "react-bootstrap";

const Exam = () => {
  const Ref = useRef(null);

  const { emailid } = useParams();
  const [isVerified, setIsVerified] = useState(false);
  const [lastQuestionSubmitted, setLastQuestionSubmitted] = useState(false);
  const [userDetails, setUserDetails] = useState({});
  const [timer, setTimer] = useState("00:00:00");
  const [currentIndex, setCurrentIndex] = useState(0);
  const [examStarted, setExamStarted] = useState(false);
  const [questions, setQuestions] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [answers, setAnswers] = useState([]);
  const [exitExam, setExitExam] = useState(false);
  const [output, setOutput] = useState({
    Decision: "Pass",
    Justification:
      "The candidate provided detailed, relevant, and complete answers highlighting their roles and responsibilities. Both responses demonstrated a clear understanding of their past positions and included specific achievements, which indicates competence in their field. The responses met the quality criteria necessary for passing.",
    OverallPercentage: "100%",
    QuestionScores: {
      Q1: 10,
      Q2: 10,
    },
  });


    
    const handleBeforeUnload = (event) => {
      event.preventDefault();
      event.returnValue = '';
    };
  
    useEffect(() => {
      window.addEventListener('beforeunload', handleBeforeUnload);
  
      //.
      return () => {
        window.removeEventListener('beforeunload', handleBeforeUnload);
      };
    }, []);







  const getCandidateDetails = async () => {
    axios
      .get(`http://localhost:8080/api/candidates/${emailid}`, {})
      .then((response) => {
        if (response) {
          setUserDetails(response.data);
          console.log(userDetails);
        } else {
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const getter = async () => {
    axios
      .get(
        `http://localhost:8080/api/candidates/${userDetails.candidate_Id}/questions`
      )
      .then((response) => {
        setShowModal(true);
        setQuestions(response.data);
        console.log(response.data);
      })
      .catch((error) => console.log(error));
  };

  useEffect(() => {
    const fetchDetailsAndQuestions = async () => {
      await getCandidateDetails();
    };

    fetchDetailsAndQuestions();
  }, [emailid]);

  useEffect(() => {
    if (userDetails.candidate_Id) {
      getter();
    }
  }, [userDetails]);

  const verifiyEmail = () => {
    axios
      .get(`http://localhost:8080/api/candidates/verify`, {
        params: {
          email: emailid,
        },
      })
      .then((response) => {
        if (response.data == true) {
          setIsVerified(true);
          console.log(response.data);
        } else {
          setIsVerified(false);
          console.log(response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    console.log("Component mounted or updated");
    return () => {
      console.log("Component unmounted");
    };
  }, []);

  const Input = {
    TotalExamMarks: 20,
    PassPercentage: "50%",
    QuestionsAndAnswers: [
      {
        Question:
          "Can you describe your role and key responsibilities at IDREAM MEDIA related to managing YouTube and Facebook from July 2022 to January 2024?",
        MinimumTime: 90,
        MaximumMarks: 10,
        Answer: "",
      },
      {
        Question:
          "What were your primary responsibilities and achievements as an HR at Q CONNEQT BUSINESS SOLUTIONS LIMITED from June 2021 to May 2022?",
        MinimumTime: 90,
        MaximumMarks: 10,
        Answer: "",
      },
    ],
  };

  const fetchResults =  () => {
    let updatedInput = {
      ...Input,
      QuestionsAndAnswers: questions.map((q) => ({
        Question: q.question,
        MinimumTime: q.minimumTime,
        MaximumMarks: q.maximumMarks,
        Answer: "",
      })),
    };

    updatedInput = {
      ...Input,
      QuestionsAndAnswers: Input.QuestionsAndAnswers.map((qa, index) => ({
        ...qa,
        Answer: answers[index] || "",
      })),
    };

    console.log(updatedInput);

    axios
      .post(
        `https://hook.eu2.make.com/5a0umo82ii6z8j0gmcl84v8hs1xyq00p`,
        updatedInput
      )
      .then((response) => {
        setOutput({
          Decision: "Pass",
          Justification:
            "The candidate provided detailed, relevant, and complete answers highlighting their roles and responsibilities. Both responses demonstrated a clear understanding of their past positions and included specific achievements, which indicates competence in their field. The responses met the quality criteria necessary for passing.",
          OverallPercentage: "100%",
          QuestionScores: {
            Q1: 10,
            Q2: 10,
          },
        });
        console.log(output);
      })
      .catch((error) => console.log(error));
  };

  const saveTotalResult = async () => {
    fetchResults(); // Ensure fetchResults has completed

    console.log(output);

    let finalOutput = {
      ...output,
      answers: answers,
    };

    console.log("Final Output before sending:", finalOutput);

    axios
      .post(
        `http://localhost:8080/api/interviews/save-answers/${userDetails.candidate_Id}`, finalOutput)
      .then((response) => {
        console.log(response);
        setExitExam(true);
      })
      .catch((response) => {
        console.log(response);
      });
  };

  useEffect(() => {
    verifiyEmail();
  }, [emailid]);

  const [statuses, setStatuses] = useState(
    Array(questions.length).fill("unattempted")
  );

  const totalTime = questions.reduce(
    (accumulator, currentElement) => accumulator + currentElement.minimumTime,
    0
  );

  const getTimeRemaining = (time) => {
    const hours = Math.floor(time / 3600);
    const minutes = Math.floor((time % 3600) / 60);
    const seconds = time % 60;

    return {
      hours,
      minutes,
      seconds,
    };
  };

  const startTimer = (time) => {
    let { hours, minutes, seconds } = getTimeRemaining(time);
    if (time >= 0) {
      setTimer(
        `${hours > 9 ? hours : "0" + hours}:${
          minutes > 9 ? minutes : "0" + minutes
        }:${seconds > 9 ? seconds : "0" + seconds}`
      );
    }
  };

  const clearTimer = (time) => {
    if (Ref.current) clearInterval(Ref.current);

    const id = setInterval(() => {
      if (time > 0) {
        time -= 1;
        startTimer(time);
      } else {
        clearInterval(Ref.current);
      }
    }, 1000);

    Ref.current = id;
  };

  useEffect(() => {
    const modalElement = document.getElementById("exampleModal");
    if (modalElement) {
      const modalInstance = new window.bootstrap.Modal(modalElement, {
        backdrop: "static",
        keyboard: false,
      });
      modalInstance.show();
    }

    return () => {
      if (Ref.current) clearInterval(Ref.current);
    };
  }, []);

  const handleStartExam = () => {
    setExamStarted(true);
    setShowModal(false);

    clearTimer(totalTime);

    const modalElement = document.getElementById("exampleModal");
    if (modalElement) {
      const modalInstance = window.bootstrap.Modal.getInstance(modalElement);
      if (modalInstance) {
        modalInstance.hide();
      }
    }

    // const modalBackdrop = document.querySelector(".modal-backdrop");
    // if (modalBackdrop) {
    //   modalBackdrop.remove();
    // }
  };

  const onClickReset = () => {
    clearTimer(totalTime);
  };

  const handleAnswerSubmit = (index, answer) => {
    if (index < questions.length - 1) {
      const newStatuses = [...statuses];
      newStatuses[index] = "attempted";
      setStatuses(newStatuses);
      setCurrentIndex(index + 1);
    } else {
      console.log("Exam finished");
      setLastQuestionSubmitted(true);
    }
  };

  const handleTimeUp = (index) => {
    if (index < questions.length - 1) {
      const newStatuses = [...statuses];
      newStatuses[index] = "timed-out";
      setStatuses(newStatuses);
      setCurrentIndex(index + 1);
    } else {
      console.log("Exam finished");

      setLastQuestionSubmitted(true);
    }
  };

  return (
    <div className="exam-background">
      {exitExam ? (
        <>
          <p className="text-light text-center fw-bold fst-italic">
            your answers are saved with us. please, exit the exam
          </p>
        </>
      ) : (
        <>
          {isVerified ? (
            <>
              <div className="">
                <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                  <a className="navbar-brand" >
                    <img src="/e-HRlogo.png" alt="logo" class="responsive" />
                  </a>
                  <h6 className="text-light text-center">{emailid}</h6>
                  <button
                    className="navbar-toggler"
                    type="button"
                    data-toggle="collapse"
                    data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                  >
                    <span className="navbar-toggler-icon"></span>
                  </button>

                  <div
                    className="collapse navbar-collapse "
                    id="navbarSupportedContent"
                  >
                    <ul className="navbar-nav mr-auto"></ul>
                    <div className="form-inline my-2 pe-1 my-lg-0 text-light">
                      <span className="text-bold p-0 m-0 g-0">Time Left: </span>
                      <span className="p-0 ms-1 g-0">
                        <h3> {timer}</h3>
                      </span>
                    </div>
                  </div>
                </nav>
              </div>

              <div className="row">
                <div className="col-2 border-right">
                  <div className="container fw-bolder pt-1 pt-md-0 pt-sm-0">
                    {questions.map((element, index) => (
                      <span
                        className={`dot m-1 text-center ${
                          currentIndex === index
                            ? "bg-info text-white"
                            : statuses[index] === "attempted"
                            ? "bg-success text-white"
                            : statuses[index] === "timed-out"
                            ? "bg-danger text-white"
                            : statuses[index] === "unattempted"
                            ? "bg-warning text-white"
                            : ""
                        }`}
                        key={`question-dot-${index}`}
                      >
                        <div>{index + 1}</div>
                      </span>
                    ))}
                  </div>
                </div>

                <div className="col-10">
                  <div className="row">
                    <Question
                      question={questions[currentIndex]}
                      index={currentIndex}
                      totalQuestions={questions.length}
                      onAnswerSubmit={handleAnswerSubmit}
                      onTimeUp={handleTimeUp}
                      saveAllAnswers={setAnswers}
                      lastQuestionSubmitted={lastQuestionSubmitted}
                      saveTotalResult={saveTotalResult}
                    />
                  </div>
                  <div className="row"></div>
                </div>
              </div>

              <Modal
                show={showModal}
                onHide={handleStartExam}
                backdrop="static"
                keyboard={false}
                className="modal-background"
              >
                <div className="modal-dialog ">
                  <div className="modal-content modal-background ">
                    <div className="modal-header ">
                      <h1
                        className="modal-title fs-5 text-white"
                        id="exampleModal"
                      >
                        INSTRUCTIONS
                      </h1>
                    </div>
                    <div className="modal-body ">
                      <div className="text-white ">
                        <header className="text-center py-4 provider text-white">
                          <h1>INSTRUCTIONS</h1>
                        </header>

                        <div className="row my-5 mx-1 provider">
                          <div className="col">
                            <div className=" p-4 rounded shadow-sm">
                              <h2 className="text-center mb-4">
                                Please Read Carefully
                              </h2>
                              <div className="entry-content">
                                <p>&#9642; Do not refresh the page</p>
                                <p>
                                  &#9642; Do not exit exam directly with out
                                  submitting
                                </p>
                                <p>
                                  &#9642; Cannot go to previous or next question
                                </p>
                              </div>
                            </div>
                          </div>
                        </div>

                        <footer className="text-center footer py-4 provider text-white">
                          <p>
                            <span className="text-danger">*</span> Click to
                            start the exam
                          </p>
                          <Button
                            variant="outline-info"
                            onClick={handleStartExam}
                          >
                            START
                          </Button>
                        </footer>
                      </div>
                    </div>
                  </div>
                </div>
              </Modal>
            </>
          ) : (
            <>
              <h1>YOU CANNOT ATTEMT EXAM THIS TIME </h1>
            </>
          )}
        </>
      )}
    </div>
  );
};

export default Exam;
