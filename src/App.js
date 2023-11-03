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
import Login from "./Modules/LoginPage/Login";
import Registration from "./Modules/LoginPage/Registration";
import Missing from "./Modules/Missing";
import RequireAuth from "./Modules/RequireAuth";


const ROLES = {
  'USER': "USER",
  'OPERATOR': "OPERATOR",
  'ADMIN': "ADMIN"
}





function App() {
  return (
      <div className="App">
      <Header/>



        <Routes>

          <Route element={<RequireAuth allowedRoles={[ROLES.USER]}/>}>
            <Route path="/user/registryPostalItem" element={<MainPage/>}></Route>
            <Route path="/user/postalItems" element={<PostalItemsPage/>}></Route>
          </Route>

          <Route element={<RequireAuth allowedRoles={[ROLES.OPERATOR]}/>}>
            <Route path="/operator/postalItems" element={<PostalItemsPage></PostalItemsPage>}></Route>
          </Route>



          <Route exact path ="/"  element={<MainPage/>}></Route>
          <Route exact path="/mailDepartments" element={<MailDepartmentsPage/>}></Route>
          <Route exact path="/postalItems" element={<PostalItemsPage/>}></Route>
          <Route exact path="/historyMovements" element={<HistoryMovementsPage/>}></Route>
          <Route exact path='/login' element={<Login/>}></Route>
          <Route exact path ='/registration' element={<Registration/>}></Route>
          <Route exact path ='*' element={<Missing/>}></Route>
        </Routes>
      </div>
  );
}

export default App;
