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
import PostalItems from "./Pages/PostalItemsPage"
import HistoryMovementsPage from "./Pages/HistoryMovementsPage"


function App() {
  return (
      <div className="App">
      <Header/>

        <Routes>
          <Route exact path ="/"  element={<MainPage/>}></Route>
          <Route exact path="/mailDepartments" element={<MailDepartmentsPage/>}></Route>
          <Route exact path="/postalItems" element={<PostalItems/>}></Route>
          <Route exact path="/historyMovements" element={<HistoryMovementsPage/>}></Route>
        </Routes>
      </div>
  );
}

export default App;