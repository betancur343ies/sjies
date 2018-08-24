import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOperadorMySuffix } from 'app/shared/model/operador-my-suffix.model';

type EntityResponseType = HttpResponse<IOperadorMySuffix>;
type EntityArrayResponseType = HttpResponse<IOperadorMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class OperadorMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/operadors';

    constructor(private http: HttpClient) {}

    create(operador: IOperadorMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(operador);
        return this.http
            .post<IOperadorMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(operador: IOperadorMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(operador);
        return this.http
            .put<IOperadorMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOperadorMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOperadorMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(operador: IOperadorMySuffix): IOperadorMySuffix {
        const copy: IOperadorMySuffix = Object.assign({}, operador, {
            fechaCreacion: operador.fechaCreacion != null && operador.fechaCreacion.isValid() ? operador.fechaCreacion.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaCreacion = res.body.fechaCreacion != null ? moment(res.body.fechaCreacion) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((operador: IOperadorMySuffix) => {
            operador.fechaCreacion = operador.fechaCreacion != null ? moment(operador.fechaCreacion) : null;
        });
        return res;
    }
}
