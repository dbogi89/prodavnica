export interface IRacunStavke {
  id?: number;
  redniBrojStavke?: number;
  kolicina?: number;
  artikalId?: number;
  racunId?: number;
}

export const defaultValue: Readonly<IRacunStavke> = {};
