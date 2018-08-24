import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISorteoMySuffix } from 'app/shared/model/sorteo-my-suffix.model';

@Component({
    selector: 'jhi-sorteo-my-suffix-detail',
    templateUrl: './sorteo-my-suffix-detail.component.html'
})
export class SorteoMySuffixDetailComponent implements OnInit {
    sorteo: ISorteoMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sorteo }) => {
            this.sorteo = sorteo;
        });
    }

    previousState() {
        window.history.back();
    }
}
