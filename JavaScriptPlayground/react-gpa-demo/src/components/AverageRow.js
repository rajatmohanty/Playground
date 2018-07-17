import React, { Component } from 'react';
import { render } from 'react-dom';
import PropTypes from "prop-types";

const Average = (props) => {
  var gpa = props.courses.map((course) => (course.gpa));
  var sum = gpa.reduce((p, c) => parseFloat(p) + parseFloat(c));
  var len = gpa.length;
  return (
    <div>
      <h4>You have {len} courses, with average GPA: {(sum / len).toFixed(3)}.</h4>
    </div>
  );
};

Average.propTypes = {
  courses: PropTypes.array
};

Average.defaultProps = {
  courses: []
};

export default Average;