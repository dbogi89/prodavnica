import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IKupac } from 'app/shared/model/kupac.model';
import { getEntities as getKupacs } from 'app/entities/kupac/kupac.reducer';
import { getEntity, updateEntity, createEntity, reset } from './racun.reducer';
import { IRacun } from 'app/shared/model/racun.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRacunUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRacunUpdateState {
  isNew: boolean;
  kupacId: string;
}

export class RacunUpdate extends React.Component<IRacunUpdateProps, IRacunUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      kupacId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getKupacs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { racunEntity } = this.props;
      const entity = {
        ...racunEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/racun');
  };

  render() {
    const { racunEntity, kupacs, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="prodavnicaApp.racun.home.createOrEditLabel">Create or edit a Racun</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : racunEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="racun-id">ID</Label>
                    <AvInput id="racun-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="brojRacunaLabel" for="racun-brojRacuna">
                    Broj Racuna
                  </Label>
                  <AvField id="racun-brojRacuna" type="text" name="brojRacuna" />
                </AvGroup>
                <AvGroup>
                  <Label id="datumLabel" for="racun-datum">
                    Datum
                  </Label>
                  <AvField id="racun-datum" type="date" className="form-control" name="datum" />
                </AvGroup>
                <AvGroup>
                  <Label for="racun-kupac">Kupac</Label>
                  <AvInput id="racun-kupac" type="select" className="form-control" name="kupacId">
                    <option value="" key="0" />
                    {kupacs
                      ? kupacs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/racun" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  kupacs: storeState.kupac.entities,
  racunEntity: storeState.racun.entity,
  loading: storeState.racun.loading,
  updating: storeState.racun.updating,
  updateSuccess: storeState.racun.updateSuccess
});

const mapDispatchToProps = {
  getKupacs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RacunUpdate);
