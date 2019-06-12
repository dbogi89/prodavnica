import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAdresa } from 'app/shared/model/adresa.model';
import { getEntities as getAdresas } from 'app/entities/adresa/adresa.reducer';
import { getEntity, updateEntity, createEntity, reset } from './kupac.reducer';
import { IKupac } from 'app/shared/model/kupac.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IKupacUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IKupacUpdateState {
  isNew: boolean;
  adresaId: string;
}

export class KupacUpdate extends React.Component<IKupacUpdateProps, IKupacUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      adresaId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getAdresas();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { kupacEntity } = this.props;
      const entity = {
        ...kupacEntity,
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
    this.props.history.push('/entity/kupac');
  };

  render() {
    const { kupacEntity, adresas, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="prodavnicaApp.kupac.home.createOrEditLabel">Create or edit a Kupac</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : kupacEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="kupac-id">ID</Label>
                    <AvInput id="kupac-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nazivKupcaLabel" for="kupac-nazivKupca">
                    Naziv Kupca
                  </Label>
                  <AvField id="kupac-nazivKupca" type="text" name="nazivKupca" />
                </AvGroup>
                <AvGroup>
                  <Label id="pibLabel" for="kupac-pib">
                    Pib
                  </Label>
                  <AvField id="kupac-pib" type="text" name="pib" />
                </AvGroup>
                <AvGroup>
                  <Label for="kupac-adresa">Adresa</Label>
                  <AvInput id="kupac-adresa" type="select" className="form-control" name="adresa.id">
                    <option value="" key="0" />
                    {adresas
                      ? adresas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/kupac" replace color="info">
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
  adresas: storeState.adresa.entities,
  kupacEntity: storeState.kupac.entity,
  loading: storeState.kupac.loading,
  updating: storeState.kupac.updating,
  updateSuccess: storeState.kupac.updateSuccess
});

const mapDispatchToProps = {
  getAdresas,
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
)(KupacUpdate);
