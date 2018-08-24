import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SjiesSharedModule } from 'app/shared';
import {
    SorteoMySuffixComponent,
    SorteoMySuffixDetailComponent,
    SorteoMySuffixUpdateComponent,
    SorteoMySuffixDeletePopupComponent,
    SorteoMySuffixDeleteDialogComponent,
    sorteoRoute,
    sorteoPopupRoute
} from './';

const ENTITY_STATES = [...sorteoRoute, ...sorteoPopupRoute];

@NgModule({
    imports: [SjiesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SorteoMySuffixComponent,
        SorteoMySuffixDetailComponent,
        SorteoMySuffixUpdateComponent,
        SorteoMySuffixDeleteDialogComponent,
        SorteoMySuffixDeletePopupComponent
    ],
    entryComponents: [
        SorteoMySuffixComponent,
        SorteoMySuffixUpdateComponent,
        SorteoMySuffixDeleteDialogComponent,
        SorteoMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SjiesSorteoMySuffixModule {}
