import React from 'react';
import {Switch, Route} from 'react-router-dom';
import NonCampaignList from '../containers/NonCampaignList';
import NonCampaignView from './../containers/NonCampaignView';
import NonCampaignCreate from '../containers/NonCampaignCreate';
import UserView from '../containers/UserView';
import BookingTabsView from '../containers/BookingTabsView';
import CopyView from '../containers/CopyView';
import BookingCreate from '../containers/BookingCreate';
import AdTemplate from '../containers/AdTemplate';
import AdminView from './../containers/AdminView';

const Error404 = () => {
  return <h3>Route not found</h3>;
};

const MainRouter = () => (
  <Switch>
    <Route exact path="/" component={NonCampaignList} />
    <Route exact path="/advertisement" component={NonCampaignList} />
    <Route path="/advertisement/new" component={NonCampaignCreate} />
    <Route exact path="/advertisement/:id/booking/new"
      component={BookingCreate} />
    <Route path="/advertisement/:id" component={NonCampaignView} />
    <Route exact path="/booking/:id/template" component={AdTemplate} />
    <Route path="/booking/:id" component={BookingTabsView} />
    <Route exact path="/copy/:id" component={CopyView} />
    <Route exact path="/copy/:id/edit" component={CopyView} />
    <Route path="/user/:id" component={UserView} />
    <Route path="/admin" component={AdminView}/>
    <Route component={Error404} />
  </Switch>
);

export default MainRouter;
