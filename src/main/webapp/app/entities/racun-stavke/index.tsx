import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RacunStavke from './racun-stavke';
import RacunStavkeDetail from './racun-stavke-detail';
import RacunStavkeUpdate from './racun-stavke-update';
import RacunStavkeDeleteDialog from './racun-stavke-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RacunStavkeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RacunStavkeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RacunStavkeDetail} />
      <ErrorBoundaryRoute path={match.url} component={RacunStavke} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RacunStavkeDeleteDialog} />
  </>
);

export default Routes;
