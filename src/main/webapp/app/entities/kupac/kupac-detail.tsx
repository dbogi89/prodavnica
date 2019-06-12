import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './kupac.reducer';
import { IKupac } from 'app/shared/model/kupac.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IKupacDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class KupacDetail extends React.Component<IKupacDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { kupacEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Kupac [<b>{kupacEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nazivKupca">Naziv Kupca</span>
            </dt>
            <dd>{kupacEntity.nazivKupca}</dd>
            <dt>
              <span id="pib">Pib</span>
            </dt>
            <dd>{kupacEntity.pib}</dd>
            <dt>Adresa</dt>
            <dd>{kupacEntity.adresa ? kupacEntity.adresa.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/kupac" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/kupac/${kupacEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ kupac }: IRootState) => ({
  kupacEntity: kupac.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(KupacDetail);
