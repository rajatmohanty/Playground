import React, { Component } from 'react';
import { render } from 'react-dom';
import CourseRow from './CourseRow'
import PropTypes from "prop-types";

const CourseList = (props) => {
  return (
    <div>
      {props.courses.map(course => (
        <CourseRow name={course.name} gpa={course.gpa}/>
      ))}
    </div>
  );
};

CourseList.propTypes = {
  courses: PropTypes.object
};

CourseList.defaultProps = {
  courses: null
};

export default CourseList;