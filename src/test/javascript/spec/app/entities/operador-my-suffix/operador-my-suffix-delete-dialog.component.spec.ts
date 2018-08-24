/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SjiesTestModule } from '../../../test.module';
import { OperadorMySuffixDeleteDialogComponent } from 'app/entities/operador-my-suffix/operador-my-suffix-delete-dialog.component';
import { OperadorMySuffixService } from 'app/entities/operador-my-suffix/operador-my-suffix.service';

describe('Component Tests', () => {
    describe('OperadorMySuffix Management Delete Component', () => {
        let comp: OperadorMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<OperadorMySuffixDeleteDialogComponent>;
        let service: OperadorMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SjiesTestModule],
                declarations: [OperadorMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(OperadorMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OperadorMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperadorMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
