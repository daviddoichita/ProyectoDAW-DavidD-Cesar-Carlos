export interface Socio {
    id: number;
    rol: 'U' | 'A';
    nombre: string;
    apellidos: string;
    email: string;
    foto: string;
    password: string;
  }