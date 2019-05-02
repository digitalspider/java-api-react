import React from 'react';
import {Switch, Route} from 'react-router-dom';
import LandingPage from '../containers/LandingPage';
import UserPage from '../containers/UserPage';
import ArticlePage from '../containers/ArticlePage';

const Error404 = () => {
  return <h3>Route not found</h3>;
};

const MainRouter = () => (
  <Switch>
    <Route path="/user" component={UserPage}/>
    <Route path="/article" component={ArticlePage}/>
    <Route path="/" component={LandingPage}/>
    <Route component={Error404} />
  </Switch>
);

export default MainRouter;
