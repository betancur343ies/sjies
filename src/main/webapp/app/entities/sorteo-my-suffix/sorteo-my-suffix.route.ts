import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SorteoMySuffix } from 'app/shared/model/sorteo-my-suffix.model';
import { SorteoMySuffixService } from './sorteo-my-suffix.service';
import { SorteoMySuffixComponent } from './sorteo-my-suffix.component';
import { SorteoMySuffixDetailComponent } from './sorteo-my-suffix-detail.component';
import { SorteoMySuffixUpdateComponent } from './sorteo-my-suffix-update.component';
import { SorteoMySuffixDeletePopupComponent } from './sorteo-my-suffix-delete-dialog.component';
import { ISorteoMySuffix } from 'app/shared/model/sorteo-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class SorteoMySuffixResolve implements Resolve<ISorteoMySuffix> {
    constructor(private service: SorteoMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sorteo: HttpResponse<SorteoMySuffix>) => sorteo.body));
        }
        return of(new SorteoMySuffix());
    }
}

export const sorteoRoute: Routes = [
    {
        path: 'sorteo-my-suffix',
        component: SorteoMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sjiesApp.sorteo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sorteo-my-suffix/:id/view',
        component: SorteoMySuffixDetailComponent,
        resolve: {
            sorteo: SorteoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sjiesApp.sorteo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sorteo-my-suffix/new',
        component: SorteoMySuffixUpdateComponent,
        resolve: {
            sorteo: SorteoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sjiesApp.sorteo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sorteo-my-suffix/:id/edit',
        component: SorteoMySuffixUpdateComponent,
        resolve: {
            sorteo: SorteoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sjiesApp.sorteo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sorteoPopupRoute: Routes = [
    {
        path: 'sorteo-my-suffix/:id/delete',
        component: SorteoMySuffixDeletePopupComponent,
        resolve: {
            sorteo: SorteoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sjiesApp.sorteo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
