import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OperadorMySuffix } from 'app/shared/model/operador-my-suffix.model';
import { OperadorMySuffixService } from './operador-my-suffix.service';
import { OperadorMySuffixComponent } from './operador-my-suffix.component';
import { OperadorMySuffixDetailComponent } from './operador-my-suffix-detail.component';
import { OperadorMySuffixUpdateComponent } from './operador-my-suffix-update.component';
import { OperadorMySuffixDeletePopupComponent } from './operador-my-suffix-delete-dialog.component';
import { IOperadorMySuffix } from 'app/shared/model/operador-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class OperadorMySuffixResolve implements Resolve<IOperadorMySuffix> {
    constructor(private service: OperadorMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((operador: HttpResponse<OperadorMySuffix>) => operador.body));
        }
        return of(new OperadorMySuffix());
    }
}

export const operadorRoute: Routes = [
    {
        path: 'operador-my-suffix',
        component: OperadorMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sjiesApp.operador.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'operador-my-suffix/:id/view',
        component: OperadorMySuffixDetailComponent,
        resolve: {
            operador: OperadorMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sjiesApp.operador.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'operador-my-suffix/new',
        component: OperadorMySuffixUpdateComponent,
        resolve: {
            operador: OperadorMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sjiesApp.operador.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'operador-my-suffix/:id/edit',
        component: OperadorMySuffixUpdateComponent,
        resolve: {
            operador: OperadorMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sjiesApp.operador.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operadorPopupRoute: Routes = [
    {
        path: 'operador-my-suffix/:id/delete',
        component: OperadorMySuffixDeletePopupComponent,
        resolve: {
            operador: OperadorMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sjiesApp.operador.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
