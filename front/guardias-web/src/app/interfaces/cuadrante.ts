import { Cargo } from "./cargo";
import { Falta } from "./falta";
import { Sesion } from "./sesion";

export interface Cuadrante {
  id: number,
  fecha: Date,
  cargo: Cargo,
  guardia: Sesion,
  faltas: Falta[],
}
