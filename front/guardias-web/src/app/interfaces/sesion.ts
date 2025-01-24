import { Aula } from "./aula";
import { Curso } from "./curso";
import { Dia } from "./dia";
import { Grupo } from "./grupo";
import { Intervalo } from "./intervalo";
import { Materia } from "./materia";
import { Profesor } from "./profesor";

export interface Sesion {
  id: number,
  profesor: Profesor,
  materia: Materia
  grupo: Grupo,
  aula: Aula,
  intervalo: Intervalo,
  curso: Curso,
  dia: Dia,
}
