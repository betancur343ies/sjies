import { Moment } from 'moment';
import { ISorteoMySuffix } from 'app/shared/model//sorteo-my-suffix.model';

export interface IOperadorMySuffix {
    id?: number;
    nombre?: string;
    fechaCreacion?: Moment;
    totalSorteos?: number;
    sorteosActivos?: number;
    sorteos?: ISorteoMySuffix[];
}

export class OperadorMySuffix implements IOperadorMySuffix {
    constructor(
        public id?: number,
        public nombre?: string,
        public fechaCreacion?: Moment,
        public totalSorteos?: number,
        public sorteosActivos?: number,
        public sorteos?: ISorteoMySuffix[]
    ) {}
}
