import { IAdresa } from 'app/shared/model/adresa.model';
import { IRacun } from 'app/shared/model/racun.model';

export interface IKupac {
  id?: number;
  nazivKupca?: string;
  pib?: string;
  adresa?: IAdresa;
  racuns?: IRacun[];
}

export const defaultValue: Readonly<IKupac> = {};
