export interface InformesFaltas {
    fecha: string;
    intervalo: {
        horainicio: string;
        horafin: string;
    };
    profesor: {
        nombre: string;
        apellidos: string;
    };
    grupo: {
        nombre: string;
    };
    aula: {
        nombre: string;
    };
}