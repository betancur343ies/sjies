import { Moment } from 'moment';

export const enum Juegos {
    RULETA = 'RULETA',
    DADOS = 'DADOS',
    LA24 = 'LA24'
}

export const enum Estado {
    ACTIVO = 'ACTIVO',
    INACTIVO = 'INACTIVO',
    FINALIZADO = 'FINALIZADO'
}

export interface ISorteoMySuffix {
    id?: number;
    nombre?: string;
    tipo?: Juegos;
    fechaCreacion?: Moment;
    estado?: Estado;
    fechaRealizacion?: Moment;
    ganador?: string;
    operadorNombre?: string;
    operadorId?: number;
}

export class SorteoMySuffix implements ISorteoMySuffix {
    constructor(
        public id?: number,
        public nombre?: string,
        public tipo?: Juegos,
        public fechaCreacion?: Moment,
        public estado?: Estado,
        public fechaRealizacion?: Moment,
        public ganador?: string,
        public operadorNombre?: string,
        public operadorId?: number
    ) {}
}
