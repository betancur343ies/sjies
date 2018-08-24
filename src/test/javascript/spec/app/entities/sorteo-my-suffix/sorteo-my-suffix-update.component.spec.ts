/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SjiesTestModule } from '../../../test.module';
import { SorteoMySuffixUpdateComponent } from 'app/entities/sorteo-my-suffix/sorteo-my-suffix-update.component';
import { SorteoMySuffixService } from 'app/entities/sorteo-my-suffix/sorteo-my-suffix.service';
import { SorteoMySuffix } from 'app/shared/model/sorteo-my-suffix.model';

describe('Component Tests', () => {
    describe('SorteoMySuffix Management Update Component', () => {
        let comp: SorteoMySuffixUpdateComponent;
        let fixture: ComponentFixture<SorteoMySuffixUpdateComponent>;
        let service: SorteoMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SjiesTestModule],
                declarations: [SorteoMySuffixUpdateComponent]
            })
                .overrideTemplate(SorteoMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SorteoMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SorteoMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SorteoMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sorteo = entity;
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
                    const entity = new SorteoMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sorteo = entity;
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
