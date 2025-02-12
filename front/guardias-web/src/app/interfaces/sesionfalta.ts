import { Cuadrante } from "./cuadrante";
import { Sesion } from "./sesion";

export interface Sesionfalta {
    id: number;
    sesion: Sesion;
    cuadrante: Cuadrante;
    incidencias: string;
    firma: string;
    deberes: boolean;
}
