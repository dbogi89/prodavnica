import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRacun, defaultValue } from 'app/shared/model/racun.model';

export const ACTION_TYPES = {
  FETCH_RACUN_LIST: 'racun/FETCH_RACUN_LIST',
  FETCH_RACUN: 'racun/FETCH_RACUN',
  CREATE_RACUN: 'racun/CREATE_RACUN',
  UPDATE_RACUN: 'racun/UPDATE_RACUN',
  DELETE_RACUN: 'racun/DELETE_RACUN',
  RESET: 'racun/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRacun>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RacunState = Readonly<typeof initialState>;

// Reducer

export default (state: RacunState = initialState, action): RacunState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RACUN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RACUN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RACUN):
    case REQUEST(ACTION_TYPES.UPDATE_RACUN):
    case REQUEST(ACTION_TYPES.DELETE_RACUN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RACUN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RACUN):
    case FAILURE(ACTION_TYPES.CREATE_RACUN):
    case FAILURE(ACTION_TYPES.UPDATE_RACUN):
    case FAILURE(ACTION_TYPES.DELETE_RACUN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RACUN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_RACUN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RACUN):
    case SUCCESS(ACTION_TYPES.UPDATE_RACUN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RACUN):
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

const apiUrl = 'api/racuns';

// Actions

export const getEntities: ICrudGetAllAction<IRacun> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RACUN_LIST,
    payload: axios.get<IRacun>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRacun> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RACUN,
    payload: axios.get<IRacun>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRacun> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RACUN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRacun> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RACUN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRacun> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RACUN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
