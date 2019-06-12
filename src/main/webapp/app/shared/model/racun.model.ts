import { Moment } from 'moment';
import { IRacunStavke } from 'app/shared/model/racun-stavke.model';

export interface IRacun {
  id?: number;
  brojRacuna?: string;
  datum?: Moment;
  kupacId?: number;
  racunStavkes?: IRacunStavke[];
}

export const defaultValue: Readonly<IRacun> = {};
