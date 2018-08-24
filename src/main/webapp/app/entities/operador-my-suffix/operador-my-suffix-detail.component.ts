import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperadorMySuffix } from 'app/shared/model/operador-my-suffix.model';

@Component({
    selector: 'jhi-operador-my-suffix-detail',
    templateUrl: './operador-my-suffix-detail.component.html'
})
export class OperadorMySuffixDetailComponent implements OnInit {
    operador: IOperadorMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ operador }) => {
            this.operador = operador;
        });
    }

    previousState() {
        window.history.back();
    }
}
