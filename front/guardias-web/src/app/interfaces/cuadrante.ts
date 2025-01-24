import { Cargo } from "./cargo";
import { Sesion } from "./sesion";

export interface Cuadrante {
  id: number,
  fecha: Date,
  incidencias: string,
  firma: string,
  deberes: string,
  cargo: Cargo,
  guardia: Sesion,
  faltas: Sesion[],
}
