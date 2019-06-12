import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IKupac, defaultValue } from 'app/shared/model/kupac.model';

export const ACTION_TYPES = {
  FETCH_KUPAC_LIST: 'kupac/FETCH_KUPAC_LIST',
  FETCH_KUPAC: 'kupac/FETCH_KUPAC',
  CREATE_KUPAC: 'kupac/CREATE_KUPAC',
  UPDATE_KUPAC: 'kupac/UPDATE_KUPAC',
  DELETE_KUPAC: 'kupac/DELETE_KUPAC',
  RESET: 'kupac/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IKupac>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type KupacState = Readonly<typeof initialState>;

// Reducer

export default (state: KupacState = initialState, action): KupacState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_KUPAC_LIST):
    case REQUEST(ACTION_TYPES.FETCH_KUPAC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_KUPAC):
    case REQUEST(ACTION_TYPES.UPDATE_KUPAC):
    case REQUEST(ACTION_TYPES.DELETE_KUPAC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_KUPAC_LIST):
    case FAILURE(ACTION_TYPES.FETCH_KUPAC):
    case FAILURE(ACTION_TYPES.CREATE_KUPAC):
    case FAILURE(ACTION_TYPES.UPDATE_KUPAC):
    case FAILURE(ACTION_TYPES.DELETE_KUPAC):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_KUPAC_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_KUPAC):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_KUPAC):
    case SUCCESS(ACTION_TYPES.UPDATE_KUPAC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_KUPAC):
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

const apiUrl = 'api/kupacs';

// Actions

export const getEntities: ICrudGetAllAction<IKupac> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_KUPAC_LIST,
    payload: axios.get<IKupac>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IKupac> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_KUPAC,
    payload: axios.get<IKupac>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IKupac> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_KUPAC,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IKupac> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_KUPAC,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IKupac> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_KUPAC,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
