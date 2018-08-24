import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOperadorMySuffix } from 'app/shared/model/operador-my-suffix.model';
import { OperadorMySuffixService } from './operador-my-suffix.service';

@Component({
    selector: 'jhi-operador-my-suffix-update',
    templateUrl: './operador-my-suffix-update.component.html'
})
export class OperadorMySuffixUpdateComponent implements OnInit {
    private _operador: IOperadorMySuffix;
    isSaving: boolean;
    fechaCreacion: string;

    constructor(private operadorService: OperadorMySuffixService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operador }) => {
            this.operador = operador;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.operador.fechaCreacion = moment(this.fechaCreacion, DATE_TIME_FORMAT);
        if (this.operador.id !== undefined) {
            this.subscribeToSaveResponse(this.operadorService.update(this.operador));
        } else {
            this.subscribeToSaveResponse(this.operadorService.create(this.operador));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOperadorMySuffix>>) {
        result.subscribe((res: HttpResponse<IOperadorMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get operador() {
        return this._operador;
    }

    set operador(operador: IOperadorMySuffix) {
        this._operador = operador;
        this.fechaCreacion = moment(operador.fechaCreacion).format(DATE_TIME_FORMAT);
    }
}
