import { Sesion } from "./sesion";

export interface Falta {
  id: number,
  incidencias: string,
  firma: string,
  deberes: string,
  sesion: Sesion,
}
