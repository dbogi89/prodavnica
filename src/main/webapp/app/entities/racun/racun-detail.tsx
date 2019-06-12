import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './racun.reducer';
import { IRacun } from 'app/shared/model/racun.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRacunDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RacunDetail extends React.Component<IRacunDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { racunEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Racun [<b>{racunEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="brojRacuna">Broj Racuna</span>
            </dt>
            <dd>{racunEntity.brojRacuna}</dd>
            <dt>
              <span id="datum">Datum</span>
            </dt>
            <dd>
              <TextFormat value={racunEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Kupac</dt>
            <dd>{racunEntity.kupacId ? racunEntity.kupacId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/racun" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/racun/${racunEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ racun }: IRootState) => ({
  racunEntity: racun.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RacunDetail);
