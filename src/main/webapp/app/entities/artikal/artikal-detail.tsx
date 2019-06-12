import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './artikal.reducer';
import { IArtikal } from 'app/shared/model/artikal.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArtikalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ArtikalDetail extends React.Component<IArtikalDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { artikalEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Artikal [<b>{artikalEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="imeArtikla">Ime Artikla</span>
            </dt>
            <dd>{artikalEntity.imeArtikla}</dd>
            <dt>
              <span id="barKod">Bar Kod</span>
            </dt>
            <dd>{artikalEntity.barKod}</dd>
            <dt>
              <span id="cena">Cena</span>
            </dt>
            <dd>{artikalEntity.cena}</dd>
          </dl>
          <Button tag={Link} to="/entity/artikal" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/artikal/${artikalEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ artikal }: IRootState) => ({
  artikalEntity: artikal.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ArtikalDetail);
