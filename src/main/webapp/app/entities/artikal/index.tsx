import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Artikal from './artikal';
import ArtikalDetail from './artikal-detail';
import ArtikalUpdate from './artikal-update';
import ArtikalDeleteDialog from './artikal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArtikalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArtikalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArtikalDetail} />
      <ErrorBoundaryRoute path={match.url} component={Artikal} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ArtikalDeleteDialog} />
  </>
);

export default Routes;
