import React from 'react';
import {Switch, Route} from 'react-router-dom';
import Main from './../containers/Main';

class MainRouter extends React.Component {
  render() {
    return (
      <Switch>
        <Route path="/" component={Main} />
      </Switch>
    );
  }
}

export default MainRouter;
