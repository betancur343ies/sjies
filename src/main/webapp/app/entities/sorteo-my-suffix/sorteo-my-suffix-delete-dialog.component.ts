import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISorteoMySuffix } from 'app/shared/model/sorteo-my-suffix.model';
import { SorteoMySuffixService } from './sorteo-my-suffix.service';

@Component({
    selector: 'jhi-sorteo-my-suffix-delete-dialog',
    templateUrl: './sorteo-my-suffix-delete-dialog.component.html'
})
export class SorteoMySuffixDeleteDialogComponent {
    sorteo: ISorteoMySuffix;

    constructor(private sorteoService: SorteoMySuffixService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sorteoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sorteoListModification',
                content: 'Deleted an sorteo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sorteo-my-suffix-delete-popup',
    template: ''
})
export class SorteoMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sorteo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SorteoMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.sorteo = sorteo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
