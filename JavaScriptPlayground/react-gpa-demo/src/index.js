import React, { Component } from 'react';
import { render } from 'react-dom';
import Header from './components/Header';
import CourseRow from './components/CourseRow'
import CourseList from './components/CourseList'
import AverageRow from './components/AverageRow'
import courseList from "./courses.json";

class App extends Component {
  constructor() {
    super();
    this.state = {
      name: 'GPA Calculator',
      courses: courseList
    };
    this.refreshCourses = this.refreshCourses.bind(this);
  }

  refreshCourses() {
    var tempList = courseList.slice();
    var i;
    for (i = 0; i < tempList.length; i++) {
      var c = tempList[i];
      c.gpa = (Math.random() * 5).toFixed(3);
    }
    this.setState({
      courses: tempList
    });
  }

  render() {
    return (
      <div>
        <Header name={this.state.name} />
        <CourseList courses={this.state.courses} />
        <AverageRow courses={this.state.courses} />
        <button onClick={this.refreshCourses}>Refresh</button>
      </div>
    );
  }
}

render(<App />, document.getElementById('root'));