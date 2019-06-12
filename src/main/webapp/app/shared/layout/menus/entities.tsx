import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/artikal">
      Artikal
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/kupac">
      Kupac
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/racun">
      Racun
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/adresa">
      Adresa
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/racun-stavke">
      Racun Stavke
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
