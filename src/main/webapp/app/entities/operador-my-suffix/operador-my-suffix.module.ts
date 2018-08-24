import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SjiesSharedModule } from 'app/shared';
import {
    OperadorMySuffixComponent,
    OperadorMySuffixDetailComponent,
    OperadorMySuffixUpdateComponent,
    OperadorMySuffixDeletePopupComponent,
    OperadorMySuffixDeleteDialogComponent,
    operadorRoute,
    operadorPopupRoute
} from './';

const ENTITY_STATES = [...operadorRoute, ...operadorPopupRoute];

@NgModule({
    imports: [SjiesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OperadorMySuffixComponent,
        OperadorMySuffixDetailComponent,
        OperadorMySuffixUpdateComponent,
        OperadorMySuffixDeleteDialogComponent,
        OperadorMySuffixDeletePopupComponent
    ],
    entryComponents: [
        OperadorMySuffixComponent,
        OperadorMySuffixUpdateComponent,
        OperadorMySuffixDeleteDialogComponent,
        OperadorMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SjiesOperadorMySuffixModule {}
