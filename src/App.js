import "./App.css"
import React from 'react';
import Header from "./Modules/Header";
import MainPage from "./Pages/MainPage"
import {
  Switch,
  Route,
  Routes
} from 'react-router-dom';
import MailDepartmentsPage from "./Pages/MailDepartmentsPage";


function App() {
  return (
      <div className="App">
      <Header/>

        <Routes>
          <Route exact path ="/"  element={<MainPage/>}></Route>
          <Route exact path="/departments" element={<MailDepartmentsPage/>}></Route>
        </Routes>
      </div>
  );
}

export default App;
