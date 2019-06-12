import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Adresa from './adresa';
import AdresaDetail from './adresa-detail';
import AdresaUpdate from './adresa-update';
import AdresaDeleteDialog from './adresa-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdresaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdresaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdresaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Adresa} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AdresaDeleteDialog} />
  </>
);

export default Routes;
