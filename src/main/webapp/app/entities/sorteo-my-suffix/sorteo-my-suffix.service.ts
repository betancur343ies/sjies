import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISorteoMySuffix } from 'app/shared/model/sorteo-my-suffix.model';

type EntityResponseType = HttpResponse<ISorteoMySuffix>;
type EntityArrayResponseType = HttpResponse<ISorteoMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class SorteoMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/sorteos';

    constructor(private http: HttpClient) {}

    create(sorteo: ISorteoMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sorteo);
        return this.http
            .post<ISorteoMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(sorteo: ISorteoMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sorteo);
        return this.http
            .put<ISorteoMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISorteoMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISorteoMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(sorteo: ISorteoMySuffix): ISorteoMySuffix {
        const copy: ISorteoMySuffix = Object.assign({}, sorteo, {
            fechaCreacion: sorteo.fechaCreacion != null && sorteo.fechaCreacion.isValid() ? sorteo.fechaCreacion.toJSON() : null,
            fechaRealizacion: sorteo.fechaRealizacion != null && sorteo.fechaRealizacion.isValid() ? sorteo.fechaRealizacion.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaCreacion = res.body.fechaCreacion != null ? moment(res.body.fechaCreacion) : null;
        res.body.fechaRealizacion = res.body.fechaRealizacion != null ? moment(res.body.fechaRealizacion) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((sorteo: ISorteoMySuffix) => {
            sorteo.fechaCreacion = sorteo.fechaCreacion != null ? moment(sorteo.fechaCreacion) : null;
            sorteo.fechaRealizacion = sorteo.fechaRealizacion != null ? moment(sorteo.fechaRealizacion) : null;
        });
        return res;
    }
}
