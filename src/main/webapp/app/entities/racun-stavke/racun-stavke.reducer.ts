import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRacunStavke, defaultValue } from 'app/shared/model/racun-stavke.model';

export const ACTION_TYPES = {
  FETCH_RACUNSTAVKE_LIST: 'racunStavke/FETCH_RACUNSTAVKE_LIST',
  FETCH_RACUNSTAVKE: 'racunStavke/FETCH_RACUNSTAVKE',
  CREATE_RACUNSTAVKE: 'racunStavke/CREATE_RACUNSTAVKE',
  UPDATE_RACUNSTAVKE: 'racunStavke/UPDATE_RACUNSTAVKE',
  DELETE_RACUNSTAVKE: 'racunStavke/DELETE_RACUNSTAVKE',
  RESET: 'racunStavke/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRacunStavke>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RacunStavkeState = Readonly<typeof initialState>;

// Reducer

export default (state: RacunStavkeState = initialState, action): RacunStavkeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RACUNSTAVKE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RACUNSTAVKE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RACUNSTAVKE):
    case REQUEST(ACTION_TYPES.UPDATE_RACUNSTAVKE):
    case REQUEST(ACTION_TYPES.DELETE_RACUNSTAVKE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RACUNSTAVKE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RACUNSTAVKE):
    case FAILURE(ACTION_TYPES.CREATE_RACUNSTAVKE):
    case FAILURE(ACTION_TYPES.UPDATE_RACUNSTAVKE):
    case FAILURE(ACTION_TYPES.DELETE_RACUNSTAVKE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RACUNSTAVKE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RACUNSTAVKE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RACUNSTAVKE):
    case SUCCESS(ACTION_TYPES.UPDATE_RACUNSTAVKE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RACUNSTAVKE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/racun-stavkes';

// Actions

export const getEntities: ICrudGetAllAction<IRacunStavke> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RACUNSTAVKE_LIST,
  payload: axios.get<IRacunStavke>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRacunStavke> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RACUNSTAVKE,
    payload: axios.get<IRacunStavke>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRacunStavke> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RACUNSTAVKE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRacunStavke> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RACUNSTAVKE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRacunStavke> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RACUNSTAVKE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
