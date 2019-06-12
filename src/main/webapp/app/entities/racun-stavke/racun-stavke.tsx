import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './racun-stavke.reducer';
import { IRacunStavke } from 'app/shared/model/racun-stavke.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRacunStavkeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class RacunStavke extends React.Component<IRacunStavkeProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { racunStavkeList, match } = this.props;
    return (
      <div>
        <h2 id="racun-stavke-heading">
          Racun Stavkes
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Racun Stavke
          </Link>
        </h2>
        <div className="table-responsive">
          {racunStavkeList && racunStavkeList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Redni Broj Stavke</th>
                  <th>Kolicina</th>
                  <th>Artikal</th>
                  <th>Racun</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {racunStavkeList.map((racunStavke, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${racunStavke.id}`} color="link" size="sm">
                        {racunStavke.id}
                      </Button>
                    </td>
                    <td>{racunStavke.redniBrojStavke}</td>
                    <td>{racunStavke.kolicina}</td>
                    <td>{racunStavke.artikalId ? <Link to={`artikal/${racunStavke.artikalId}`}>{racunStavke.artikalId}</Link> : ''}</td>
                    <td>{racunStavke.racunId ? <Link to={`racun/${racunStavke.racunId}`}>{racunStavke.racunId}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${racunStavke.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${racunStavke.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${racunStavke.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Racun Stavkes found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ racunStavke }: IRootState) => ({
  racunStavkeList: racunStavke.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RacunStavke);
