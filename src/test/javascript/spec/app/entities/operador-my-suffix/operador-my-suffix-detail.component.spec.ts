/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SjiesTestModule } from '../../../test.module';
import { OperadorMySuffixDetailComponent } from 'app/entities/operador-my-suffix/operador-my-suffix-detail.component';
import { OperadorMySuffix } from 'app/shared/model/operador-my-suffix.model';

describe('Component Tests', () => {
    describe('OperadorMySuffix Management Detail Component', () => {
        let comp: OperadorMySuffixDetailComponent;
        let fixture: ComponentFixture<OperadorMySuffixDetailComponent>;
        const route = ({ data: of({ operador: new OperadorMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SjiesTestModule],
                declarations: [OperadorMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OperadorMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OperadorMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.operador).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
