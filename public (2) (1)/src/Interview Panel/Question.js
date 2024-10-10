import React, { useState, useEffect } from "react";
import "./Question.css";
import { Button } from "react-bootstrap";

function Question({
  question,
  index,
  totalQuestions,
  onAnswerSubmit,
  onTimeUp,
  saveAllAnswers,
  lastQuestionSubmitted,
  saveTotalResult,
}) {
  const [answer, setAnswer] = useState("");
  const minimumTime = question?.minimumTime || 0;
  const [timeLeft, setTimeLeft] = useState(minimumTime);

  useEffect(() => {
    setAnswer("");
  }, [question, index]);

  useEffect(() => {
    if (answer.trim()) {
      saveAllAnswers((prevAnswers) => {
        const updatedAnswers = [...prevAnswers];
        updatedAnswers[index] = answer;
        return updatedAnswers;
      });
    }
  }, [answer, index, saveAllAnswers]);

  useEffect(() => {
    setTimeLeft(minimumTime);
  }, [minimumTime, index]);

  useEffect(() => {
    const timer = setInterval(() => {
      setTimeLeft((prevTime) => {
        if (prevTime <= 1) {
          clearInterval(timer);
          onTimeUp(index);
          return 0;
        }
        return prevTime - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, [index, onTimeUp]);

  const handleAnswerChange = (e) => {
    const newValue = e.target.value;
    if (newValue !== answer) {
      setAnswer(newValue);
    }
  };

  const handleSubmit = () => {
    onAnswerSubmit(index, answer);
  };

  return (
    <>
      {lastQuestionSubmitted ? (
        <>
          <h1 className="text-light">
            completed all questions please click on submit
            <Button
              variant="success outline-suceess ms-2"
              onClick={saveTotalResult}
            >
              Submit Exam
            </Button>
          </h1>
        </>
      ) : (
        <div style={{ padding: "20px" }}>
          <div className="inside p-3">
            <h2 className="text-light">
              Question <span className="text-info"> {index + 1} </span> of
              {totalQuestions}
            </h2>
            <h5 className="question text-light">
              <span> Q)</span> {question?.question}
            </h5>
            <textarea
              className="form-control"
              value={answer}
              onInput={handleAnswerChange}
              style={{ width: "100%", height: "200px" }}
              disabled={timeLeft <= 0}
              onCopy={(e) => e.preventDefault()}
              onPaste={(e) => e.preventDefault()}
              onCut={(e) => e.preventDefault()}
            />

            <div>
              <p className="text-light">
                Time Remaining for this Question: {timeLeft} seconds
              </p>
              <button
                className="btn btn-primary"
                onClick={handleSubmit}
                disabled={timeLeft <= 0}
              >
                Submit Answer
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default Question;
