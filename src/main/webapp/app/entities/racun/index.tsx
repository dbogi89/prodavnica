import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Racun from './racun';
import RacunDetail from './racun-detail';
import RacunUpdate from './racun-update';
import RacunDeleteDialog from './racun-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RacunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RacunUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RacunDetail} />
      <ErrorBoundaryRoute path={match.url} component={Racun} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RacunDeleteDialog} />
  </>
);

export default Routes;
