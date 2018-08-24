import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SjiesSorteoMySuffixModule } from './sorteo-my-suffix/sorteo-my-suffix.module';
import { SjiesOperadorMySuffixModule } from './operador-my-suffix/operador-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SjiesSorteoMySuffixModule,
        SjiesOperadorMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SjiesEntityModule {}
