import "./App.css"
import React from 'react';
import Header from "./Modules/Header";
import {
  Route,
  Routes
} from 'react-router-dom';

import MainPage from "./Pages/MainPage"
import Login from "./Modules/LoginPage/Login";
import Registration from "./Modules/LoginPage/Registration";
import Missing from "./Modules/Missing";
import RequireAuth from "./Modules/RequireAuth";
import UserPostalItems from "./Modules/UserViewPart/UserPostalItems";
import UserMovementHistory from "./Modules/UserViewPart/UserMovementHistory";
import UserRegistryPostalItem from "./Modules/UserViewPart/UserRegistryPostalItem";
import Queries from "./Modules/OperatorViewPart/Queries";
import OperatorPostalItems from "./Modules/OperatorViewPart/OperatorPostalItems";
import OperatorMovementHistory from "./Modules/OperatorViewPart/OperatorMovementHistory";
import AdminMailDepartments from "./Modules/AdminViewPart/AdminMailDepartments";
import AdminOperators from "./Modules/AdminViewPart/AdminOperators";
import RegistryDepartment from "./Modules/AdminViewPart/RegistryDepartment";
import RegistryOperator from "./Modules/AdminViewPart/RegistryOperator";


const ROLES = {
  "USER": "USER",
  "OPERATOR": "OPERATOR",
  "ADMIN": "ADMIN"
}





function App() {
  return (
      <div className="App">
      <Header/>
        <Routes >

          <Route element={<RequireAuth allowedRoles={[ROLES.USER]}/>}>
            <Route path="/user/registryPostalItem" element={<UserRegistryPostalItem/>}></Route>
            <Route path="/user/postalItems" element={<UserPostalItems/>}></Route>
            <Route path="/user/postalItemsMovementHistories" element={<UserMovementHistory/>}></Route>
          </Route>

          <Route element={<RequireAuth allowedRoles={[ROLES.OPERATOR]}/>}>
            <Route path="/operator/queries" element={<Queries></Queries>}></Route>
            <Route path="/operator/postalItems" element={<OperatorPostalItems></OperatorPostalItems>}></Route>
            <Route path="/operator/movementHistory" element={<OperatorMovementHistory/>}></Route>
          </Route>

          <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN]}/>}>
            <Route path="/admin/mailDepartments" element={<AdminMailDepartments></AdminMailDepartments>}></Route>
            <Route path="/admin/operators" element={<AdminOperators></AdminOperators>}></Route>
            <Route path="/admin/registryMailDepartment" element={<RegistryDepartment/>}></Route>
            <Route path="/admin/registryOperator" element={<RegistryOperator/>}></Route>
          </Route>

          <Route exact path ="/"  element={<MainPage/>}></Route>
          <Route exact path='/login' element={<Login/>}></Route>
          <Route exact path ='/registration' element={<Registration/>}></Route>
          <Route exact path ='*' element={<Missing/>}></Route>

        </Routes>
      </div>
  );
}

export default App;
