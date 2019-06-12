import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdresa, defaultValue } from 'app/shared/model/adresa.model';

export const ACTION_TYPES = {
  FETCH_ADRESA_LIST: 'adresa/FETCH_ADRESA_LIST',
  FETCH_ADRESA: 'adresa/FETCH_ADRESA',
  CREATE_ADRESA: 'adresa/CREATE_ADRESA',
  UPDATE_ADRESA: 'adresa/UPDATE_ADRESA',
  DELETE_ADRESA: 'adresa/DELETE_ADRESA',
  RESET: 'adresa/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdresa>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type AdresaState = Readonly<typeof initialState>;

// Reducer

export default (state: AdresaState = initialState, action): AdresaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADRESA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADRESA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ADRESA):
    case REQUEST(ACTION_TYPES.UPDATE_ADRESA):
    case REQUEST(ACTION_TYPES.DELETE_ADRESA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ADRESA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADRESA):
    case FAILURE(ACTION_TYPES.CREATE_ADRESA):
    case FAILURE(ACTION_TYPES.UPDATE_ADRESA):
    case FAILURE(ACTION_TYPES.DELETE_ADRESA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADRESA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADRESA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADRESA):
    case SUCCESS(ACTION_TYPES.UPDATE_ADRESA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADRESA):
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

const apiUrl = 'api/adresas';

// Actions

export const getEntities: ICrudGetAllAction<IAdresa> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADRESA_LIST,
  payload: axios.get<IAdresa>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IAdresa> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADRESA,
    payload: axios.get<IAdresa>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAdresa> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADRESA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdresa> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADRESA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdresa> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADRESA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
