import axios from "axios";
import React from "react";

const Dummy = () => {
 

  const Input = {
    TotalExamMarks: 20,
    PassPercentage: "50%",
    QuestionsAndAnswers: [
      {
        Question:
          "Can you describe your role and key responsibilities at IDREAM MEDIA related to managing YouTube and Facebook from July 2022 to January 2024?",
        MinimumTime: 90,
        MaximumMarks: 10,
        Answer:
          "During my time at IDREAM MEDIA, I managed YouTube and Facebook channels, overseeing content scheduling, analytics tracking, and audience engagement. My responsibilities included optimizing video content for better reach and coordinating with the content creation team.",
      },
      {
        Question:
          "What were your primary responsibilities and achievements as an HR at Q CONNEQT BUSINESS SOLUTIONS LIMITED from June 2021 to May 2022?",
        MinimumTime: 90,
        MaximumMarks: 10,
        Answer:
          "At Q CONNEQT BUSINESS SOLUTIONS LIMITED, my primary responsibilities as an HR included talent acquisition, employee onboarding, and managing employee relations. I successfully implemented an employee wellness program and streamlined the recruitment process, which reduced hiring time by 20%.",
      },
    ],
  };

  const getter = () => {
    axios
      .post(`https://hook.eu2.make.com/5a0umo82ii6z8j0gmcl84v8hs1xyq00p`, Input)
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error))
  };

  return (
    <div>
      <button onClick={getter}> Click me</button>
    </div>
  );
};

export default Dummy;