import React from 'react';
import {Switch, Route} from 'react-router-dom';
// import AdminView from './../containers/AdminView';

const Error404 = () => {
  return <h3>Route not found</h3>;
};

const MainRouter = () => (
  <Switch>
    {/* <Route path="/admin" component={AdminView}/> */}
    <Route component={Error404} />
  </Switch>
);

export default MainRouter;
