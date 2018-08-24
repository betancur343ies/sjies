/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SjiesTestModule } from '../../../test.module';
import { SorteoMySuffixDeleteDialogComponent } from 'app/entities/sorteo-my-suffix/sorteo-my-suffix-delete-dialog.component';
import { SorteoMySuffixService } from 'app/entities/sorteo-my-suffix/sorteo-my-suffix.service';

describe('Component Tests', () => {
    describe('SorteoMySuffix Management Delete Component', () => {
        let comp: SorteoMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<SorteoMySuffixDeleteDialogComponent>;
        let service: SorteoMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SjiesTestModule],
                declarations: [SorteoMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(SorteoMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SorteoMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SorteoMySuffixService);
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
