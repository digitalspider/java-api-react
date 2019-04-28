import React from 'react';
import {Switch, Route} from 'react-router-dom';
import Main from './../containers/Main';
import UserLoad from './../containers/UserLoad';
import Login from './../containers/Login';

class MainRouter extends React.Component {
  render() {
    return (
      <Switch>
        <Route path="/login" component={Login} />
        <Route path="/user/load" component={UserLoad} />
        <Route path="/" component={Main} />
      </Switch>
    );
  }
}

export default MainRouter;
