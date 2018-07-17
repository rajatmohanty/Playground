import React, { Component } from 'react';
import { render } from 'react-dom';
import PropTypes from "prop-types";

const Course = (props) => {
  return (
    <div>
      <p>{props.name}: {props.gpa}</p>
    </div>
  );
};

Course.propTypes = {
  name: PropTypes.string,
  gpa: PropTypes.number
};

Course.defaultProps = {
  name: "",
  gpa: 0.0
};

export default Course;