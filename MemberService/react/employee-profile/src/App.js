import React from 'react';
import './App.css';
import Header from './component/header/';
import MemberHome from './component/member-home/';

function App() {
  return (
    <div className="App">
     <Header />
      <div className="container">
        <MemberHome />
      </div>
    </div>
  );
}

export default App;
