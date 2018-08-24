/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SjiesTestModule } from '../../../test.module';
import { SorteoMySuffixDetailComponent } from 'app/entities/sorteo-my-suffix/sorteo-my-suffix-detail.component';
import { SorteoMySuffix } from 'app/shared/model/sorteo-my-suffix.model';

describe('Component Tests', () => {
    describe('SorteoMySuffix Management Detail Component', () => {
        let comp: SorteoMySuffixDetailComponent;
        let fixture: ComponentFixture<SorteoMySuffixDetailComponent>;
        const route = ({ data: of({ sorteo: new SorteoMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SjiesTestModule],
                declarations: [SorteoMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SorteoMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SorteoMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sorteo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
