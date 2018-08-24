import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOperadorMySuffix } from 'app/shared/model/operador-my-suffix.model';
import { OperadorMySuffixService } from './operador-my-suffix.service';

@Component({
    selector: 'jhi-operador-my-suffix-delete-dialog',
    templateUrl: './operador-my-suffix-delete-dialog.component.html'
})
export class OperadorMySuffixDeleteDialogComponent {
    operador: IOperadorMySuffix;

    constructor(
        private operadorService: OperadorMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.operadorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'operadorListModification',
                content: 'Deleted an operador'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-operador-my-suffix-delete-popup',
    template: ''
})
export class OperadorMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ operador }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OperadorMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.operador = operador;
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
