import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './adresa.reducer';
import { IAdresa } from 'app/shared/model/adresa.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdresaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Adresa extends React.Component<IAdresaProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { adresaList, match } = this.props;
    return (
      <div>
        <h2 id="adresa-heading">
          Adresas
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Adresa
          </Link>
        </h2>
        <div className="table-responsive">
          {adresaList && adresaList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Ulica</th>
                  <th>Ptt</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {adresaList.map((adresa, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${adresa.id}`} color="link" size="sm">
                        {adresa.id}
                      </Button>
                    </td>
                    <td>{adresa.ulica}</td>
                    <td>{adresa.ptt}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${adresa.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${adresa.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${adresa.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Adresas found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ adresa }: IRootState) => ({
  adresaList: adresa.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Adresa);
