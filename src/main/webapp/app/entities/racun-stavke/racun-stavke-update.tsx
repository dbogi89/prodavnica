import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IArtikal } from 'app/shared/model/artikal.model';
import { getEntities as getArtikals } from 'app/entities/artikal/artikal.reducer';
import { IRacun } from 'app/shared/model/racun.model';
import { getEntities as getRacuns } from 'app/entities/racun/racun.reducer';
import { getEntity, updateEntity, createEntity, reset } from './racun-stavke.reducer';
import { IRacunStavke } from 'app/shared/model/racun-stavke.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRacunStavkeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRacunStavkeUpdateState {
  isNew: boolean;
  artikalId: string;
  racunId: string;
}

export class RacunStavkeUpdate extends React.Component<IRacunStavkeUpdateProps, IRacunStavkeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      artikalId: '0',
      racunId: '0',
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

    this.props.getArtikals();
    this.props.getRacuns();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { racunStavkeEntity } = this.props;
      const entity = {
        ...racunStavkeEntity,
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
    this.props.history.push('/entity/racun-stavke');
  };

  render() {
    const { racunStavkeEntity, artikals, racuns, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="prodavnicaApp.racunStavke.home.createOrEditLabel">Create or edit a RacunStavke</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : racunStavkeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="racun-stavke-id">ID</Label>
                    <AvInput id="racun-stavke-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="redniBrojStavkeLabel" for="racun-stavke-redniBrojStavke">
                    Redni Broj Stavke
                  </Label>
                  <AvField
                    id="racun-stavke-redniBrojStavke"
                    type="string"
                    className="form-control"
                    name="redniBrojStavke"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="kolicinaLabel" for="racun-stavke-kolicina">
                    Kolicina
                  </Label>
                  <AvField id="racun-stavke-kolicina" type="string" className="form-control" name="kolicina" />
                </AvGroup>
                <AvGroup>
                  <Label for="racun-stavke-artikal">Artikal</Label>
                  <AvInput id="racun-stavke-artikal" type="select" className="form-control" name="artikalId">
                    <option value="" key="0" />
                    {artikals
                      ? artikals.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="racun-stavke-racun">Racun</Label>
                  <AvInput id="racun-stavke-racun" type="select" className="form-control" name="racunId">
                    <option value="" key="0" />
                    {racuns
                      ? racuns.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/racun-stavke" replace color="info">
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
  artikals: storeState.artikal.entities,
  racuns: storeState.racun.entities,
  racunStavkeEntity: storeState.racunStavke.entity,
  loading: storeState.racunStavke.loading,
  updating: storeState.racunStavke.updating,
  updateSuccess: storeState.racunStavke.updateSuccess
});

const mapDispatchToProps = {
  getArtikals,
  getRacuns,
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
)(RacunStavkeUpdate);
