import React, { Component } from 'react'
import ReactDOM from 'react-dom';

class App extends Component {
  constructor() {
    super();
    this.state = {
      count: 0,
    };
    this.increment = this.increment.bind(this);
    this.decrement = this.decrement.bind(this);
    this.reset = this.reset.bind(this);
  }

  increment() {
    this.setState((prev) => ({count: prev.count + 1}));
  }

  decrement() {
    this.setState((prev) => ({count: prev.count - 1}));
  }

  reset() {
    this.setState(() => ({count: 0}));
  }

  render() {
    return (
      <div>
        <Header/>
        <h2 align="center">{this.state.count}</h2>
        <div align="center">
          <button onClick={this.increment}>+1</button>
          <button onClick={this.decrement}>-1</button><br/>
          <button onClick={this.reset}>Reset</button>
        </div>
      </div>
    );
  }
}

const Header = () => {
  return (
    <h1 align="center">Just a Counter</h1>
  );
};

ReactDOM.render(
  <App/>, 
  document.getElementById('root')
);
