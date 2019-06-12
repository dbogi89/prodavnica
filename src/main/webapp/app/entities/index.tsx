import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Artikal from './artikal';
import Kupac from './kupac';
import Racun from './racun';
import Adresa from './adresa';
import RacunStavke from './racun-stavke';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/artikal`} component={Artikal} />
      <ErrorBoundaryRoute path={`${match.url}/kupac`} component={Kupac} />
      <ErrorBoundaryRoute path={`${match.url}/racun`} component={Racun} />
      <ErrorBoundaryRoute path={`${match.url}/adresa`} component={Adresa} />
      <ErrorBoundaryRoute path={`${match.url}/racun-stavke`} component={RacunStavke} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
