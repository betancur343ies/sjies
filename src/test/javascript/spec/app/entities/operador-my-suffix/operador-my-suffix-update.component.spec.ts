/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SjiesTestModule } from '../../../test.module';
import { OperadorMySuffixUpdateComponent } from 'app/entities/operador-my-suffix/operador-my-suffix-update.component';
import { OperadorMySuffixService } from 'app/entities/operador-my-suffix/operador-my-suffix.service';
import { OperadorMySuffix } from 'app/shared/model/operador-my-suffix.model';

describe('Component Tests', () => {
    describe('OperadorMySuffix Management Update Component', () => {
        let comp: OperadorMySuffixUpdateComponent;
        let fixture: ComponentFixture<OperadorMySuffixUpdateComponent>;
        let service: OperadorMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SjiesTestModule],
                declarations: [OperadorMySuffixUpdateComponent]
            })
                .overrideTemplate(OperadorMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OperadorMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperadorMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OperadorMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.operador = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OperadorMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.operador = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
