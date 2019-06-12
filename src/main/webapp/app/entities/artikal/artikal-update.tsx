import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './artikal.reducer';
import { IArtikal } from 'app/shared/model/artikal.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArtikalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IArtikalUpdateState {
  isNew: boolean;
}

export class ArtikalUpdate extends React.Component<IArtikalUpdateProps, IArtikalUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { artikalEntity } = this.props;
      const entity = {
        ...artikalEntity,
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
    this.props.history.push('/entity/artikal');
  };

  render() {
    const { artikalEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="prodavnicaApp.artikal.home.createOrEditLabel">Create or edit a Artikal</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : artikalEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="artikal-id">ID</Label>
                    <AvInput id="artikal-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="imeArtiklaLabel" for="artikal-imeArtikla">
                    Ime Artikla
                  </Label>
                  <AvField id="artikal-imeArtikla" type="text" name="imeArtikla" />
                </AvGroup>
                <AvGroup>
                  <Label id="barKodLabel" for="artikal-barKod">
                    Bar Kod
                  </Label>
                  <AvField id="artikal-barKod" type="text" name="barKod" />
                </AvGroup>
                <AvGroup>
                  <Label id="cenaLabel" for="artikal-cena">
                    Cena
                  </Label>
                  <AvField id="artikal-cena" type="string" className="form-control" name="cena" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/artikal" replace color="info">
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
  artikalEntity: storeState.artikal.entity,
  loading: storeState.artikal.loading,
  updating: storeState.artikal.updating,
  updateSuccess: storeState.artikal.updateSuccess
});

const mapDispatchToProps = {
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
)(ArtikalUpdate);
