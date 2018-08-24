import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ISorteoMySuffix } from 'app/shared/model/sorteo-my-suffix.model';
import { SorteoMySuffixService } from './sorteo-my-suffix.service';
import { IOperadorMySuffix } from 'app/shared/model/operador-my-suffix.model';
import { OperadorMySuffixService } from 'app/entities/operador-my-suffix';

@Component({
    selector: 'jhi-sorteo-my-suffix-update',
    templateUrl: './sorteo-my-suffix-update.component.html'
})
export class SorteoMySuffixUpdateComponent implements OnInit {
    private _sorteo: ISorteoMySuffix;
    isSaving: boolean;

    operadors: IOperadorMySuffix[];
    fechaCreacion: string;
    fechaRealizacion: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private sorteoService: SorteoMySuffixService,
        private operadorService: OperadorMySuffixService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sorteo }) => {
            this.sorteo = sorteo;
        });
        this.operadorService.query().subscribe(
            (res: HttpResponse<IOperadorMySuffix[]>) => {
                this.operadors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.sorteo.fechaCreacion = moment(this.fechaCreacion, DATE_TIME_FORMAT);
        this.sorteo.fechaRealizacion = moment(this.fechaRealizacion, DATE_TIME_FORMAT);
        if (this.sorteo.id !== undefined) {
            this.subscribeToSaveResponse(this.sorteoService.update(this.sorteo));
        } else {
            this.subscribeToSaveResponse(this.sorteoService.create(this.sorteo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISorteoMySuffix>>) {
        result.subscribe((res: HttpResponse<ISorteoMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackOperadorById(index: number, item: IOperadorMySuffix) {
        return item.id;
    }
    get sorteo() {
        return this._sorteo;
    }

    set sorteo(sorteo: ISorteoMySuffix) {
        this._sorteo = sorteo;
        this.fechaCreacion = moment(sorteo.fechaCreacion).format(DATE_TIME_FORMAT);
        this.fechaRealizacion = moment(sorteo.fechaRealizacion).format(DATE_TIME_FORMAT);
    }
}
