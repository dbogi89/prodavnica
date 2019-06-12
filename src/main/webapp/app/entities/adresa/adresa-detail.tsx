import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './adresa.reducer';
import { IAdresa } from 'app/shared/model/adresa.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdresaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AdresaDetail extends React.Component<IAdresaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { adresaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Adresa [<b>{adresaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="ulica">Ulica</span>
            </dt>
            <dd>{adresaEntity.ulica}</dd>
            <dt>
              <span id="ptt">Ptt</span>
            </dt>
            <dd>{adresaEntity.ptt}</dd>
          </dl>
          <Button tag={Link} to="/entity/adresa" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/adresa/${adresaEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ adresa }: IRootState) => ({
  adresaEntity: adresa.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AdresaDetail);
