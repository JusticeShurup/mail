import "./App.css"
import React from 'react';
import Header from "./Modules/Header";
import {
  Switch,
  Route,
  Routes
} from 'react-router-dom';

import MainPage from "./Pages/MainPage"
import MailDepartmentsPage from "./Pages/MailDepartmentsPage"
import PostalItemsPage from "./Pages/PostalItemsPage"
import HistoryMovementsPage from "./Pages/HistoryMovementsPage"
import LoginPage from "./Pages/LoginPage";


function App() {
  return (
      <div className="App">
      <Header/>

        <Routes>
          <Route exact path ="/"  element={<MainPage/>}></Route>
          <Route exact path="/mailDepartments" element={<MailDepartmentsPage/>}></Route>
          <Route exact path="/postalItems" element={<PostalItemsPage/>}></Route>
          <Route exact path="/historyMovements" element={<HistoryMovementsPage/>}></Route>
          <Route exact path='/login' element={<LoginPage/>}></Route>
        </Routes>
      </div>
  );
}

export default App;
